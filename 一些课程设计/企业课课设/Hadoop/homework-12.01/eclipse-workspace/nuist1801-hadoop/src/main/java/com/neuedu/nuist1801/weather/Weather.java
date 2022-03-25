package com.neuedu.nuist1801.weather;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 1.实体类：传递数据，实现hadoop的序列化同时实现默认排序
 * 
 */
public class Weather implements WritableComparable<Weather> {
	private int year;
	private int hot;
	
	public Weather() {
	}
	
	public Weather(int year, int hot) {
		super();
		this.year = year;
		this.hot = hot;
	}
	
	@Override
	public String toString() {
		return this.year + "\t" + this.hot;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		//序列化
		out.writeInt(this.year);
		out.writeInt(this.hot);		
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		//反序列化
		this.year = in.readInt();
		this.hot = in.readInt();
	}
	
	@Override
	public int compareTo(Weather other) {
		//默认排序规则，也可以用于对象比较：1是大于，0是等于，-1是小于
		//不同年
		//return this.year-other.year;
		if (this.year != other.year) {
			return Integer.compare(this.year, other.year);
		}
		//相同年
		return Integer.compare(this.hot, other.hot);
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getHot() {
		return hot;
	}
	
	public void setHot(int hot) {
		this.hot = hot;
	}
}
