package com.microsoft.sqlserver.sqlserver_odbc;

import java.sql.*;

import org.junit.Test;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	@Test
	public void test() {
		DB db = new DB();
		db.dbConnect("jdbc:jtds:sqlserver://192.168.1.101:1433;DatabaseName=ecshop",
				"sa", "root");
	}
}

class DB {
	public DB() {
	}

	public void dbConnect(String db_connect_string, String db_userid,
			String db_password) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection conn = DriverManager.getConnection(db_connect_string,
					db_userid, db_password);
			System.out.println("connected" + "---------------------------");
			Statement stmt = conn.createStatement();// 创建SQL命令对象
			System.out.println("查询");
			System.out.println("开始读取数据");
			ResultSet rs = stmt.executeQuery("SELECT * FROM ecs_order");// 返回SQL语句查询结果集(集合)
			// 循环输出每一条记录
			while (rs.next()) {
				// 输出每个字段
				System.out.println(rs.getInt("order_id") + "\t"
						+ rs.getString("name"));
			}
			stmt.close();//关闭命令对象连接
			conn.close();//关闭数据库连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
};