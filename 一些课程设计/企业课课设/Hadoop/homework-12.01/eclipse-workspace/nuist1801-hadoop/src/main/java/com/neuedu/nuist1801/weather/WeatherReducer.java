package com.neuedu.nuist1801.weather;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 6.自定义reducer：完成每个最高温度的统计
 * @author Aria
 *
 */
public class WeatherReducer extends Reducer<Weather, Text, Weather, NullWritable> {
	private static final int TOPK = 3;
	
	@Override
	protected void reduce(Weather key, Iterable<Text> values, Reducer<Weather, Text, Weather, NullWritable>.Context context)
			throws IOException, InterruptedException {
		int i = 0;
		for (Text v : values) {
			context.write(key, NullWritable.get());
			if(++i >= TOPK) {
				break;
			}
		}
	}

}
