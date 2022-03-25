package com.neuedu.nuist18_weathersystem;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * hbase工具类：创建表，删除表…… 项目配置文件：hbase-site.xml、core-site.xml、hdfs-site.xml
 */
public class HbaseUtil {
	public static Configuration conf;// 单例模式
	protected Connection conn;
	protected Admin admin;
	protected Table table;

	/** 创建Connection对象 */
	public Connection getConnection() {
		try {
			if (null == conf) {
				// 使用提供的配置文件自动配置
				conf = HBaseConfiguration.create();
			}
			this.conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.conn;
	}

	/** 创建Admin对象 */
	public Admin getAdmin(Connection conn) {
		try {
			this.admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.admin;
	}

	/** 创建Table对象 */
	public Table geTable(Connection conn, String tableName) {
		try {
			TableName tn = TableName.valueOf(tableName);
			this.table = conn.getTable(tn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}

	/** 创建表 */
	public boolean createTable(Admin admin, String tableName, boolean isDroped, String familyName) {
		try {
			TableName tn = TableName.valueOf(tableName);
			if (admin.tableExists(tn)) {
				if (isDroped) {
					admin.disableTable(tn);// 禁用表
					admin.deleteTable(tn);// 删除表
				} else {
					// 表存在但不删除
					return false;
				}
			}
			TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(tn);
			byte[] family = Bytes.toBytes(familyName);
			ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder.newBuilder(family);
			tdb.setColumnFamily(cdb.build());
			admin.createTable(tdb.build());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 资源释放 */
	public void close() {
		try {
			if (null != this.table) {
				this.table.close();
			}
			if (null != this.admin) {
				this.admin.close();
			}
			if (null != this.conn) {
				this.conn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
