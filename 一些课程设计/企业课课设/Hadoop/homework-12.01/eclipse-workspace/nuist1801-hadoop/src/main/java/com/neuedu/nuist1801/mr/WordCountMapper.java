package com.neuedu.nuist1801.mr;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * map:将一行句子，拆分为单个的单词
 * 
 */



public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				String word = st.nextToken();
				context.write(new Text(word), new LongWritable(1));
			}
		}

}
