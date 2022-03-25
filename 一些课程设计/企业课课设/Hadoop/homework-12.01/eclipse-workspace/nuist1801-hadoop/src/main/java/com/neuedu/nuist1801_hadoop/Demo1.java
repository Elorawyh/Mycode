package com.neuedu.nuist1801_hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Demo1 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path src = new Path("d:/t.txt");
			Path dst = new Path("/data/t.txt");
			hdfs.copyFromLocalFile(src, dst);
			System.out.println("文件上传成功......");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
