package com.neuedu.nuist1801.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class RunWordCount {
	
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			String input = "/books/4300-0.txt";
			String output = "/wordcoun_result";
			if (hdfs.exists(new Path(output))) {
				hdfs.delete(new Path(output), true);
			}
			Job job = Job.getInstance(conf, "wordcout");
			job.setInputFormatClass(TextInputFormat.class);
			FileInputFormat.setInputPaths(job, input);
			job.setMapperClass(WordCountMapper.class);
			job.setMapOutputValueClass(LongWritable.class);
			job.setReducerClass(WordCountReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(LongWritable.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			FileOutputFormat.setOutputPath(job, new Path(output));
			boolean flag = job.waitForCompletion(true);
			if(flag) {
				System.out.println("词频统计结果...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
