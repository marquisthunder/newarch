package com.thinkingtop.kaas.ecweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thinkingtop.kaas.ecweb.model.Good;

public class GoodDao {
	static Connection conn = null;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Loading driver package abnormal! Please check it!");
			e.printStackTrace();
		}
		try {
			/*conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ecshop", "root", "");*/
			conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.0.101:3306/ecshop1", "root", "");
		} catch (SQLException e) {
			System.out.println("Link database is abnormal!");
			e.printStackTrace();
		}
	}

	public static Connection getConnectionByJDBC() {
		return conn;
	}

	public static Good getGood(int id){
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
			/*try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(); 
			}*/
		}
		//printlngood(good);
		return good;
	}
	
	public static void test() {
		String sql = "select * from ecs_goods";
		try {
			Statement stmt = conn.createStatement();
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
	
	public static Date parseDate(String dateStr,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static void printlngood(Good good){
		System.out.println(good.getGoods_id() + "  -  " + good.getGoods_name()+ "  -  " + good.getGoods_number()+ "  -  " + good.getGoods_weight()+ "  -  " 
				+ good.getMarket_price()+ "  -  " + good.getShop_price()+ "  -  " +  good.getPromote_price() + "  -  " + good.getPromote_start_date() + "  -  " 
				+ good.getPromote_end_date()+ "  -  " + good.getGoods_brief()+ "  -  " + good.getGoods_desc()+ "  -  " + good.getIs_real()+ "  -  " 
				+ good.getAdd_time()+ "  -  " +good.getBin());
	}

	public static void main(String[] args) {
		GoodDao testjdbc = new GoodDao();
		Good good = testjdbc.getGood(1);
		good = testjdbc.getGood(7);
		System.out.println(good.getGoods_id() + "  -  " + good.getGoods_name()+ "  -  " + good.getGoods_number()+ "  -  " + good.getGoods_weight()+ "  -  " 
				+ good.getMarket_price()+ "  -  " + good.getShop_price()+ "  -  " +  good.getPromote_price() + "  -  " + good.getPromote_start_date() + "  -  " 
				+ good.getPromote_end_date()+ "  -  " + good.getGoods_brief()+ "  -  " + good.getGoods_desc()+ "  -  " + good.getIs_real()+ "  -  " 
				+ good.getAdd_time()+ "  -  " +good.getBin());
	}

}
