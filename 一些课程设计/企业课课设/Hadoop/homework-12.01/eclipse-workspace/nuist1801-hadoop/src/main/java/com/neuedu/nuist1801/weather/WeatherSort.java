package com.neuedu.nuist1801.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 4.自定义排序：继承自WritableComparator,构造器中需要注意
 * @author Aria
 */
public class WeatherSort extends WritableComparator {
	public WeatherSort() {
		super(Weather.class, true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		Weather w1 = (Weather) a;
		Weather w2 = (Weather) b;
		//不同年份
		if (w1.getYear() != w2.getYear()) {
			return Integer.compare(w1.getYear(), w2.getYear());
		}
		//相同年份，温度从高到低
		return - Integer.compare(w1.getHot(), w2.getHot());
	}
}
