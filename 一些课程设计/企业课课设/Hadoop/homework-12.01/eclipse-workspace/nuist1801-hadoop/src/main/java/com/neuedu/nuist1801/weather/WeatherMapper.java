package com.neuedu.nuist1801.weather;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 2.自定义Mapper类：用于将一行数据，转换为Weather对象
 * @author Aria
 */
public class WeatherMapper extends Mapper<LongWritable, Text, Weather, Text> {
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Weather, Text>.Context context)
			throws IOException, InterruptedException {
		//1949-10-01 14:21:02 34℃
		//1949-10-01 14:21:02 4℃
		String line = value.toString();
		if (StringUtils.isBlank(line)) {
			return;
		}
		//拆分字符串，并转换为Weather对象
		String[] items = line.split("\t");
		int year = Integer.parseInt(items[0].substring(0, 4)); //含前不含后
		int hot = Integer.parseInt(items[1].substring(0, items[1].length()-1));
		Weather w = new Weather(year, hot);
		//输出：<Weather对象，整行详细数据字符串>
		context.write(w, value);
	}

}
