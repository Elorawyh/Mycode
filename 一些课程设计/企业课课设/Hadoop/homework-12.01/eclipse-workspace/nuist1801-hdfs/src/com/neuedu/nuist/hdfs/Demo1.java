package com.neuedu.nuist.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * 
 * hdfs编程:上传文件
 * @author Aria
 *
 */
public class Demo1 {

	public static void main(String[] args) {
		//1.构建配置对象
		Configuration conf = new Configuration();
		//2.设置配置对象的属性（与core-site.xml和hdfs-site.xml中保持一致）
		try {
			//3.获取HDFS分布式文件系统
			FileSystem hdfs = FileSystem.get(conf);
			//4.定义上传文件的源路径与目标文件
			Path src = new Path("d:/t.txt");
			//HDFS路径：绝对路径
			//Path dst = new Path("hdfs://master.hadoop.nuist:9000/data/t.txt");
			//HDFS路径：相对路径
			Path dst = new Path("/data/t.txt");
			//5.上传文件
			hdfs.copyFromLocalFile(src, dst);
			System.out.println("文件上传成功.....");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
