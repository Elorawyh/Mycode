package com.neuedu.nuist18_weathersystem;

import java.util.Scanner;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class Starter {
	private static Scanner input = new Scanner(System.in);
	private static HbaseUtil hbaseUtil = new HbaseUtil();

	public static void showMenu() {
		System.out.println("***********天气查询系统**************");
		System.out.println("1、查询某一天的天气数据");
		System.out.println("2、查询每年的最高气温");
		System.out.println("3、查询每年的最低气温");
		System.out.println("4、查询每年的平均气温");
		System.out.println("5、查询每年的下雨的天数");
		System.out.println("6、预测明天的天气");
		System.out.println("0、退出");
		System.out.print("请选择【0-6】进行操作：");
	}

	public static void main(String[] args) {
		System.out.println("系统初始中……");
//		BulkLoad.main(args);// 导入数据
//		TempMaxOfYear.main(args);// 统计每年的最高气温
//		TempMinOfYear.main(args);// 统计每年的最低气温
//		TempAvgOfYear.main(args);// 统计每年的平均气温
//		RainDayOfYear.main(args);// 统计每年的下雨的天数
		boolean flag = false;
		do {
			showMenu();
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				method1();
				break;
			case 2:
				method2();
				break;
			case 3:
				method3();
				break;
			case 4:
				method4();
				break;
			case 5:
				method5();
				break;
			case 6:
				method6();
				break;
			case 0:
				flag = true;
				break;
			default:
				System.out.println("输入错误~~");
				break;
			}
		} while (!flag);
		System.out.println("感谢您的使用，再见~~~");
	}

	private static void method6() {
		try {
			//预测
			String date="16/01";
			ForecastAfter.forecast(date);
			//显示
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "forecast");
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes(date)));
			scan.setFilter(filter);
			ResultScanner rs = table.getScanner(scan);
			System.out.println(date + "预测的天气信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					Float value = Bytes.toFloat(CellUtil.cloneValue(cell));
					System.out.println(cname+ ":"+value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void method5() {
		try {
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "results");
			Scan scan = new Scan();
			ResultScanner rs = table.getScanner(scan);
			System.out.println("每年的下雨的天数，信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					if ("rainday".equals(cname)) {
						System.out.println(Bytes.toString(r.getRow())+"年下雨的天数"+Bytes.toFloat(CellUtil.cloneValue(cell)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void method4() {
		try {
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "results");
			Scan scan = new Scan();
			ResultScanner rs = table.getScanner(scan);
			System.out.println("每年的平均气温，信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					if ("avg".equals(cname)) {
						System.out.println(Bytes.toString(r.getRow())+"年平均气温"+Bytes.toFloat(CellUtil.cloneValue(cell)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void method3() {
		try {
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "results");
			Scan scan = new Scan();
			ResultScanner rs = table.getScanner(scan);
			System.out.println("每年的最低气温，信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					if ("min".equals(cname)) {
						System.out.println(Bytes.toString(r.getRow())+"年最低气温"+Bytes.toFloat(CellUtil.cloneValue(cell)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void method2() {
		try {
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "results");
			Scan scan = new Scan();
			ResultScanner rs = table.getScanner(scan);
			System.out.println("每年的最高气温，信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					if ("max".equals(cname)) {
						System.out.println(Bytes.toString(r.getRow())+"年最高气温"+Bytes.toFloat(CellUtil.cloneValue(cell)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void method1() {
		try {
			System.out.println("请输入查询的日期(dd/mm/yyyy)：");
			String date = input.next();
			Connection conn = hbaseUtil.getConnection();
			Table table = hbaseUtil.geTable(conn, "weathers");
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareOperator.EQUAL, new SubstringComparator(date));
			scan.setFilter(filter);
			ResultScanner rs = table.getScanner(scan);
			System.out.println(date+",详细信息如下 ：");
			for (Result r : rs) {
				for (Cell cell : r.rawCells()) {
					String cname = Bytes.toString(CellUtil.cloneQualifier(cell));
					Float value = Bytes.toFloat(CellUtil.cloneValue(cell));
					System.out.println(cname+ ":"+value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
