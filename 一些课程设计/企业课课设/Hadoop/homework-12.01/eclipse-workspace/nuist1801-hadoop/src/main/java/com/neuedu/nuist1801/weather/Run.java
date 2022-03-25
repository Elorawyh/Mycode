package com.neuedu.nuist1801.weather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * 6.运行类
 * @author Aria
 */
public class Run {
	
	public static void main(String[] args) {
		//配置对象
		Configuration conf = new Configuration();
		try {
			//HDFS
			FileSystem hdfs = FileSystem.get(conf);
			//输入与输出目标路径
			//定义输入目录：意味着读取该目录下所有文件
			// String input = "/books";
			// 定义输入文件路径，仅读取一个文件
			String input = "/weather_data";
			//输出路径是不允许存在的，否则异常
			String output = "/weather_result";
			//判断输出目录是否存在，存在则删除
			if (hdfs.exists(new Path(output))) {
				hdfs.delete(new Path(output), true);
			}
			//创建Job任务
			Job job = Job.getInstance(conf, "weather");
			//设置输入：选择最长的包名
			job.setInputFormatClass(TextInputFormat.class);
			//选择最长的包名
			FileInputFormat.setInputPaths(job, input);
			//设置mapper
			job.setMapperClass(WeatherMapper.class);
			job.setMapOutputKeyClass(Weather.class);
			job.setMapOutputValueClass(Text.class);
			//设置分区
			job.setPartitionerClass(WeatherPartitioner.class);
			job.setNumReduceTasks(3);
			//设置排序
			job.setSortComparatorClass(WeatherSort.class);
			//设置分组
			job.setGroupingComparatorClass(WeatherGrouping.class);
			//设置reducer
			job.setReducerClass(WeatherReducer.class);
			//设置输出
			job.setOutputKeyClass(Weather.class);
			job.setOutputValueClass(NullWritable.class);
			//选择最长的包名
			job.setOutputFormatClass(TextOutputFormat.class);
			//选择最长的包名
			FileOutputFormat.setOutputPath(job, new Path(output));
			//运行
			boolean flag = job.waitForCompletion(true);
			//查看结果
			if(flag) {
				System.out.println("温度统计结束... ...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
