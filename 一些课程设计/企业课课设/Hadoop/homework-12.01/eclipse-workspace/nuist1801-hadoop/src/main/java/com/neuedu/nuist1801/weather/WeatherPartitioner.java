package com.neuedu.nuist1801.weather;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 3.自定义分区类：1个分区处理同一年份的数据
 * @author Aria
 *
 */
public class WeatherPartitioner extends Partitioner<Weather, Text> {
	
	@Override
	public int getPartition(Weather key, Text value, int numPartitions) {
		//1949-->numPartitions=3-->1949 - 1940 = 9 --> 9 % 3 = 0
		//1950-->numPartitions=3-->1950 - 1940 = 10 --> 10 % 3 = 1
		//1949-->numPartitions=3-->1949 - 1940 = 11 --> 11 % 3 = 2
		return (key.getYear() - 1940) % numPartitions;
	}

}
