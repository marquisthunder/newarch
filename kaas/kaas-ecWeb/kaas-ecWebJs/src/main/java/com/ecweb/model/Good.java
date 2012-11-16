package com.ecweb.model;

import java.util.Date;

public class Good {
	private int goods_id;
	private String goods_name;
	private String goods_number;
	private int goods_weight;
	private float market_price;
	private float shop_price;
	private float promote_price;
	private Date promote_start_date;
	private Date promote_end_date;
	private String goods_brief;
	private String goods_desc;
	private int is_real;
	private Date add_time;
	private String bin;
	
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_number() {
		return goods_number;
	}
	public void setGoods_number(String goods_number) {
		this.goods_number = goods_number;
	}
	public int getGoods_weight() {
		return goods_weight;
	}
	public void setGoods_weight(int goods_weight) {
		this.goods_weight = goods_weight;
	}
	public float getMarket_price() {
		return market_price;
	}
	public void setMarket_price(float market_price) {
		this.market_price = market_price;
	}
	public float getShop_price() {
		return shop_price;
	}
	public void setShop_price(float shop_price) {
		this.shop_price = shop_price;
	}
	public float getPromote_price() {
		return promote_price;
	}
	public void setPromote_price(float promote_price) {
		this.promote_price = promote_price;
	}
	public Date getPromote_start_date() {
		return promote_start_date;
	}
	public void setPromote_start_date(Date promote_start_date) {
		this.promote_start_date = promote_start_date;
	}
	public Date getPromote_end_date() {
		return promote_end_date;
	}
	public void setPromote_end_date(Date promote_end_date) {
		this.promote_end_date = promote_end_date;
	}
	public String getGoods_brief() {
		return goods_brief;
	}
	public void setGoods_brief(String goods_brief) {
		this.goods_brief = goods_brief;
	}
	public String getGoods_desc() {
		return goods_desc;
	}
	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}
	public int getIs_real() {
		return is_real;
	}
	public void setIs_real(int is_real) {
		this.is_real = is_real;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}

}
