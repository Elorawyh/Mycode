package com.neuedu.nuist1801_hadoop;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class Demo5 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data/c.txt");
			if(!hdfs.exists(dst)) {
				System.out.println("文件不存在。。。");
				return;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(hdfs.open(dst)));
			String line = reader.readLine();
			while(null != line) {
				System.out.println(line);
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
