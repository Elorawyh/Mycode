package com.neuedu.nuist1801_hadoop;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Demo3 {

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path dst = new Path("/data/w.txt");
			OutputStreamWriter out = new OutputStreamWriter(hdfs.create(dst));
			BufferedWriter writer = new BufferedWriter(out);
			writer.write("i love china");
			writer.newLine();
			writer.write("china love me");
			writer.flush();
			writer.close();
			out.close();
			System.out.println("new file end...");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
