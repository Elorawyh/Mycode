package com.neuedu.nuist1801_hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Demo4 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data/c.txt");
			if (!hdfs.exists(dst)) {
				System.out.println("文件不存在...");
				return;
			}
			FSDataInputStream inputStream = new FSDataInputStream(hdfs.open(dst));
			byte[] bytes = new byte[1024];
			int end = inputStream.read(bytes,0,bytes.length);
			System.out.print(new String(bytes));
			while(end != -1) {
				end = inputStream.read(bytes,0,bytes.length);
				System.out.print(new String(bytes));
				}
			inputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
