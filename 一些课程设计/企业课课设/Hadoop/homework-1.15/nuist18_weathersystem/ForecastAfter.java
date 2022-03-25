package com.neuedu.nuist18_weathersystem;

import java.io.DataInput;
import java.io.DataOutput;
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
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 预测
 *
 */
public class ForecastAfter {
	static class Weather implements WritableComparable<Weather> {
		private String date;
		private float temp;
		private float max;
		private float min;
		public Weather() {
		}
		
		public Weather(String date, float temp, float max, float min) {
			super();
			this.date = date;
			this.temp = temp;
			this.max = max;
			this.min = min;
		}
		@Override
		public int compareTo(Weather other) {
			if (null == other) {
				return 1;
			}
			return this.date.compareTo(other.date);
		}
		@Override
		public void write(DataOutput out) throws IOException {
			out.writeUTF(this.date);
			out.writeFloat(this.temp);
			out.writeFloat(this.max);
			out.writeFloat(this.min);
		}
		@Override
		public void readFields(DataInput in) throws IOException {
			this.date = in.readUTF();
			this.temp = in.readFloat();
			this.max = in.readFloat();
			this.min = in.readFloat();
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public float getTemp() {
			return temp;
		}
		public void setTemp(float temp) {
			this.temp = temp;
		}
		public float getMax() {
			return max;
		}
		public void setMax(float max) {
			this.max = max;
		}
		public float getMin() {
			return min;
		}
		public void setMin(float min) {
			this.min = min;
		}

		
		
	}
	static class TempForecastMapper extends TableMapper<Text, Weather> {

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, Weather>.Context context)
				throws IOException, InterruptedException {
			String row = Bytes.toString(value.getRow());
			String[] items = row.split("_");
			String after_date = context.getConfiguration().get("date");
			//31/12/2019
			//31/12
			if (!after_date.equals(items[1].substring(0,5))) {
				return;
			}
			Weather w = new Weather();
			w.setDate(after_date);
			for (Cell cell : value.rawCells()) {
				String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
				if ("tempMax".equals(cname)) {
					float max = Bytes.toFloat(CellUtil.cloneValue(cell));
					System.out.println("max" + max);
					 w.setMax(max);
				}else if ("tempMin".equals(cname)) {
					float min = Bytes.toFloat(CellUtil.cloneValue(cell));
					System.out.println("min" + min);
					w.setMin(min);
				}else if ("tempAvg".equals(cname)) {
					float avg = Bytes.toFloat(CellUtil.cloneValue(cell));
					System.out.println("avg" + avg);
					w.setTemp(avg);
				}
			}
			System.out.println("line end...");
			context.write(new Text(after_date), w);
		}

	}

	static class TempForecastReducer extends TableReducer<Text, Weather, NullWritable> {

		@Override
		protected void reduce(Text key, Iterable<Weather> values,
				Reducer<Text, Weather, NullWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			// <31/12,list<w,w,w>>
			float min = 0;
			float max = 0;
			float avg = 0;
			for (Weather w : values) {
				if (w.getMax()>0) {
					max = w.getMax();
				}
				if (w.getMin()>0) {
					min = w.getMin();
				}
				if (w.getTemp()>0) {
					avg = w.getTemp();
				}
			}
			// 构建Put构建
			byte[] row = Bytes.toBytes(key.toString());
			byte[] family = Bytes.toBytes("info");
			byte[] c1 = Bytes.toBytes("min");
			byte[] c2 = Bytes.toBytes("max");
			byte[] c3 = Bytes.toBytes("avg");
			Put put = new Put(row);
			put.addColumn(family, c1, Bytes.toBytes(min));
			put.addColumn(family, c2, Bytes.toBytes(max));
			put.addColumn(family, c3, Bytes.toBytes(avg));
			// 输出
			context.write(NullWritable.get(), put);
		}

	}

	public static void forecast(String date) {
		// 创建表
		HbaseUtil hbaseUtil = new HbaseUtil();
		Connection conn = hbaseUtil.getConnection();
		Admin admin = hbaseUtil.getAdmin(conn);
		String tableName = "forecast";
		hbaseUtil.createTable(admin, tableName, false, "info");
		//预测日期:31/12
		HbaseUtil.conf.set("date", date);
		try {
			HbaseUtil.conf.set(TableOutputFormat.OUTPUT_TABLE, tableName);
			Job job = Job.getInstance(HbaseUtil.conf);
			//设置读取源表weathers中的列
			Scan scan = new Scan();
			byte[] family = Bytes.toBytes("info");
			byte[] c1 = Bytes.toBytes("tempMin");	
			byte[] c2 = Bytes.toBytes("tempMax");	
			byte[] c3 = Bytes.toBytes("tempAvg");	
			scan.addColumn(family, c1);
			scan.addColumn(family, c2);
			scan.addColumn(family, c3);
			//设置mapper和redcuer
			TableMapReduceUtil.initTableMapperJob("weathers", scan, TempForecastMapper.class, Text.class, Weather.class, job);
			TableMapReduceUtil.initTableReducerJob(tableName, TempForecastReducer.class, job);
			//执行
			boolean flag = job.waitForCompletion(true);
			if (flag) {
				System.out.println("预测统计完成~~~");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
