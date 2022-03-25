package com.neuedu.nuist18_weathersystem;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RegionLocator;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.tool.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 数据加载与清洗
 *
 */
public class BulkLoad {
	static class DataBulkMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, ImmutableBytesWritable, Put>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			// 跳过空内容、第一行标题栏
			if (StringUtils.isBlank(line) || line.startsWith("Estacao")) {
				return;
			}
			// 只导入83377巴西利亚的数据
			if (line.indexOf("83377") < 0) {
				return;
			}
			// Estacao;Data;Hora;Precipitacao;TempBulboSeco;TempBulboUmido;TempMaxima;TempMinima;UmidadeRelativa;PressaoAtmEstacao;PressaoAtmMar;DirecaoVento;VelocidadeVento;Insolacao;Nebulosidade;Evaporacao
			// Piche;Temp Comp Media;Umidade Relativa Media;Velocidade do Vento Media;
			// 82024;01/01/1961;0000;;;;32.3;;;;;;;4.4;;;26.56;82.5;3;
			// 82024;01/01/1961;1200;;26;23.9;;22.9;83;994.2;;5;5;;8;;;;;
			// 82024;01/01/1961;1800;;32.3;27;;;65;991.6;;5;3;;9;;;;;
			// 固定强制拆分成19列
			String[] items = line.split(";", 19);
			String code = items[0];
			String date = items[1];
			String hora = items[2];
			float precipitacao = Float.valueOf(StringUtils.isBlank(items[3])? "0" : items[3]);
			float tempMax = Float.valueOf(StringUtils.isBlank(items[6])? "0" : items[6]);
			float tempMin = Float.valueOf(StringUtils.isBlank(items[7])? "0" : items[7]);
			float tempAvg = Float.valueOf(StringUtils.isBlank(items[16])? "0" : items[16]);
			;
			// 构建Put
			byte[] row = Bytes.toBytes(code + "_" + date + "_" + hora);
			byte[] family = Bytes.toBytes("info");
			byte[] c1 = Bytes.toBytes("precipitacao");
			byte[] c2 = Bytes.toBytes("tempMax");
			byte[] c3 = Bytes.toBytes("tempMin");
			byte[] c4 = Bytes.toBytes("tempAvg");
			Put put = new Put(row);
			put.addColumn(family, c1, Bytes.toBytes(precipitacao));
			put.addColumn(family, c2, Bytes.toBytes(tempMax));
			put.addColumn(family, c3, Bytes.toBytes(tempMin));
			put.addColumn(family, c4, Bytes.toBytes(tempAvg));
			// 构建ImmutableBytesWritable
			ImmutableBytesWritable rw = new ImmutableBytesWritable(row);
			// 写入数据ImmutableBytesWritable
			context.write(rw, put);
		}

	}

	public static void main(String[] args) {
		// 创建表
		HbaseUtil hbaseUtil = new HbaseUtil();
		Connection conn = hbaseUtil.getConnection();
		Admin admin = hbaseUtil.getAdmin(conn);
		String tableName = "weathers";
		hbaseUtil.createTable(admin, tableName, true, "info");
		try {
			String input = "hdfs://master.hadoop.nuist:9000/brazil/*.csv";
			String output = "hdfs://master.hadoop.nuist:9000/brazil_bulk";
			FileSystem hdfs = FileSystem.get(URI.create(output), HbaseUtil.conf);
			hdfs.delete(new Path(output), true);
			hdfs.close();
			// 1:生成hfile
			Job job = Job.getInstance(hbaseUtil.conf);
			job.setInputFormatClass(TextInputFormat.class);
			FileInputFormat.setInputPaths(job, input);
			job.setMapperClass(DataBulkMapper.class);//注意修改
			job.setMapOutputKeyClass(ImmutableBytesWritable.class);
			job.setMapOutputValueClass(Put.class);
			// job.setReducerClass(PutSortReducer.class);//默认
			job.setOutputFormatClass(HFileOutputFormat2.class);
			FileOutputFormat.setOutputPath(job, new Path(output));
			Table table = hbaseUtil.geTable(conn, tableName);
			RegionLocator regionLocator = conn.getRegionLocator(TableName.valueOf(tableName));
			HFileOutputFormat2.configureIncrementalLoad(job, table, regionLocator);
			boolean flag = job.waitForCompletion(true);
			// 2：移动hfile文件至合适的hregionserver
			LoadIncrementalHFiles loader = new LoadIncrementalHFiles(HbaseUtil.conf);
			loader.doBulkLoad(new Path(output), admin, table, regionLocator);
			System.out.println("数据导入成功~~~");
			// 读取数据
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
