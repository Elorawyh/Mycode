package com.neuedu.nuist1801.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 5.自定义分组：继承自WritableComparator,构造器中需要注意
 * @author Aria
 */
public class WeatherGrouping extends WritableComparator {
	public WeatherGrouping() {
		super(Weather.class, true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		Weather w1 = (Weather) a;
		Weather w2 = (Weather) b;
		//根据年份比较，升序
		return Integer.compare(w1.getYear(), w2.getYear());
	}
}
