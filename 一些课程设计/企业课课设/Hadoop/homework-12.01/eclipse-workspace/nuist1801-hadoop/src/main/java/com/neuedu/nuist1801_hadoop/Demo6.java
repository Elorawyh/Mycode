package com.neuedu.nuist1801_hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class Demo6 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data");
			if(!hdfs.exists(dst)) {
				System.out.println("文件不存在。。。");
				return;
			}
			for (FileStatus f : hdfs.listStatus(dst)) {
				System.out.println("是文件嗎？" + f.isFile());
				System.out.println("是目錄嗎？" + f.isDirectory());
				System.out.println("全稱：" + f.getPath());
				System.out.println("字節：" + f.getLen());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
