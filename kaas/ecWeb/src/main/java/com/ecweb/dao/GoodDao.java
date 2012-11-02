package com.ecweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecweb.model.Good;

public class GoodDao {
	/**
	 * 1、所使用的mysql驱动包为mysql-connector-java-5.0.8-bin.jar 2、Statement 用于执行静态 SQL
	 * 语句并返回它所生成结果的对象 在默认情况下，同一时间每个 Statement 对象在只能打开一个 ResultSet 对象。 因此,如果读取一个
	 * ResultSet 对象与读取另一个交叉， 则这两个对象必须是由不同的Statement 对象生成的。 如果存在某个语句的打开的当前
	 * ResultSet 对象， 则Statement 接口中的所有执行方法都会隐式关闭它。 3、ResultSet
	 * 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。 ResultSet 对象具有指向其当前数据行的指针。最初，指针被置于第一行之前。
	 * next 方法将指针移动到下一行； 因为该方法在 ResultSet 对象中没有下一行时返回 false， 所以可以在 while 循环中
	 * 使用它来迭代结果集。
	 **/
	static Connection conn = null;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Loading driver package abnormal! Please check it!");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ecshop", "root", "");
		} catch (SQLException e) {
			System.out.println("Link database is abnormal!");
			e.printStackTrace();
		}
	}

	public static Connection getConnectionByJDBC() {
		return conn;
	}

	public Good getGood(int id){
		String sql = "select * from ecs_goods where goods_id = " + id;
		Good good = new Good();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idstr = rs.getString("goods_id");
				String name = rs.getString("goods_name");
				String goods_number = rs.getString("goods_number");
				String goods_weight = rs.getString("goods_weight");
				String market_price = rs.getString("market_price");
				String shop_price = rs.getString("shop_price");
				String promote_price = rs.getString("promote_price");
				String promote_start_date = rs.getString("promote_start_date");
				String promote_end_date = rs.getString("promote_end_date");
				String goods_brief = rs.getString("goods_brief");
				String goods_desc = rs.getString("goods_desc");
				String is_real = rs.getString("is_real");
				String add_time = rs.getString("add_time");
				String bin = rs.getString("bin");
				
				good.setGoods_id(Integer.parseInt(idstr));
				good.setGoods_name(name);
				good.setGoods_number(goods_number);
				good.setGoods_weight(Integer.parseInt(goods_weight));
				good.setMarket_price(Float.valueOf(market_price));
				good.setShop_price(Float.valueOf(shop_price));
				good.setPromote_price(Float.valueOf(promote_price));
				good.setPromote_start_date(parseDate(promote_start_date, "yyyy-MM-dd"));
				good.setPromote_end_date(parseDate(promote_end_date, "yyyy-MM-dd"));
				good.setGoods_brief(goods_brief);
				good.setGoods_desc(goods_desc);
				good.setIs_real(Integer.parseInt(is_real));
				good.setAdd_time(parseDate(add_time, "yyyy-MM-dd"));
				good.setBin(bin);
				
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// 预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行)
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return good;
	}
	
	public static void test() {
		String sql = "select * from ecs_goods";
		try {
			// 创建一个jdbc声明
			Statement stmt = conn.createStatement();
			// 执行查询
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("goods_id");
				String name = rs.getString("goods_name");
				System.out.println(id + "  -  " + name);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// 预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行)
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public Date parseDate(String dateStr,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) {
		GoodDao testjdbc = new GoodDao();
		Good good = testjdbc.getGood(1);
		System.out.println(good.getGoods_id() + "  -  " + good.getGoods_name()+ "  -  " + good.getGoods_number()+ "  -  " + good.getGoods_weight()+ "  -  " 
				+ good.getMarket_price()+ "  -  " + good.getShop_price()+ "  -  " +  good.getPromote_price() + "  -  " + good.getPromote_start_date() + "  -  " 
				+ good.getPromote_end_date()+ "  -  " + good.getGoods_brief()+ "  -  " + good.getGoods_desc()+ "  -  " + good.getIs_real()+ "  -  " 
				+ good.getAdd_time()+ "  -  " +good.getBin());
	}

}
