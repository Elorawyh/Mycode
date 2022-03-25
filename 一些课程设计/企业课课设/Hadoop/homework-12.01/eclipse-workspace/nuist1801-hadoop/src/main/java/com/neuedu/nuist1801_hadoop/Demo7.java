package com.neuedu.nuist1801_hadoop;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;


public class Demo7 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data");
			if(!hdfs.exists(dst)) {
				System.out.println("文件不存在。。。");
				return;
			}
			RemoteIterator<LocatedFileStatus> ri = hdfs.listFiles(dst,true);
			while(ri.hasNext()) {
				LocatedFileStatus f = ri.next();
				System.out.println("FileName:" + f.getPath());
				BufferedReader reader = new BufferedReader(new InputStreamReader(hdfs.open(f.getPath())));
				String line = reader.readLine();
				while(null != line) {
					System.out.println(line);
					line = reader.readLine();
				}
				reader.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
