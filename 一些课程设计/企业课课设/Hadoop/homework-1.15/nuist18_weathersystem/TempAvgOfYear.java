package com.neuedu.nuist18_weathersystem;

import java.io.IOException;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 统计每年的平均气温：从weathers表中读取数据 ，统计出每年平均温度，并写入表result中
 *
 */
public class TempAvgOfYear {
	static class TempAvgMapper extends TableMapper<Text, FloatWritable> {

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, FloatWritable>.Context context)
				throws IOException, InterruptedException {
			String row = Bytes.toString(value.getRow());
			System.out.println(row);
			String[] items = row.split("_");
			for (Cell cell : value.rawCells()) {
				String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
				if ("tempAvg".equals(cname)) {
					float max = Bytes.toFloat(CellUtil.cloneValue(cell));
					context.write(new Text(items[1].substring(6)), new FloatWritable(max));
				}

			}
		}

	}

	static class TempAvgReducer extends TableReducer<Text, FloatWritable, NullWritable> {

		@Override
		protected void reduce(Text key, Iterable<FloatWritable> values,
				Reducer<Text, FloatWritable, NullWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			// <1961,list<1,2,3,4,5,6,7,8,9>>
			float sum = 0;
			int day = 0;
			for (FloatWritable v : values) {
				if (v.get()>0) {
					sum += v.get();
					day++;
				}
			}
			// 构建Put构建
			byte[] row = Bytes.toBytes(key.toString());
			byte[] family = Bytes.toBytes("info");
			byte[] c1 = Bytes.toBytes("avg");
			Put put = new Put(row);
			put.addColumn(family, c1, Bytes.toBytes(sum/day));
			// 输出
			context.write(NullWritable.get(), put);
		}

	}

	public static void main(String[] args) {
		// 创建表
		HbaseUtil hbaseUtil = new HbaseUtil();
		Connection conn = hbaseUtil.getConnection();
		Admin admin = hbaseUtil.getAdmin(conn);
		String tableName = "results";
		hbaseUtil.createTable(admin, tableName, false, "info");
		try {
			HbaseUtil.conf.set(TableOutputFormat.OUTPUT_TABLE, tableName);
			Job job = Job.getInstance(HbaseUtil.conf);
			//设置读取源表weathers中的列
			Scan scan = new Scan();
			byte[] family = Bytes.toBytes("info");
			byte[] c1 = Bytes.toBytes("tempAvg");	
			scan.addColumn(family, c1);
			//设置mapper和redcuer
			TableMapReduceUtil.initTableMapperJob("weathers", scan, TempAvgMapper.class, Text.class, FloatWritable.class, job);
			TableMapReduceUtil.initTableReducerJob(tableName, TempAvgReducer.class, job);
			//执行
			boolean flag = job.waitForCompletion(true);
			if (flag) {
				System.out.println("每年平均温度统计完成~~~");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
