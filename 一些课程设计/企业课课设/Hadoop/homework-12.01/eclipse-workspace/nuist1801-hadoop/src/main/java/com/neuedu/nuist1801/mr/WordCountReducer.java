package com.neuedu.nuist1801.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * reducer:统计
 * @author Aria
 *
 */

public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
	
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,
				Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
			long sum = 0;
			for (LongWritable v : values) {
				sum += v.get();
			}
			context.write(key,  new LongWritable(sum));
		}

}
