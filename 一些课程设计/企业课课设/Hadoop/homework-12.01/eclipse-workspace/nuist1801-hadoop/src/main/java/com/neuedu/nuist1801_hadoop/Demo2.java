package com.neuedu.nuist1801_hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Demo2 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data/c.txt");
			FSDataOutputStream outputStream = hdfs.create(dst);
			String memo = "i love china";
			byte[] buff = memo.getBytes();
			outputStream.write(buff,0,buff.length);
			outputStream.close();
			System.out.println("new file end...");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
