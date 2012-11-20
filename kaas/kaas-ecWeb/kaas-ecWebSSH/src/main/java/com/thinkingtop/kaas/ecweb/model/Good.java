package com.thinkingtop.kaas.ecweb.model;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name="ecs_goods")
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
	private byte is_real;
	private Date add_time;
	private byte[] bin;
	@Id
	@GeneratedValue
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	@Column(nullable=false)
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	@Column(nullable=false)
	public String getGoods_number() {
		return goods_number;
	}
	public void setGoods_number(String goods_number) {
		this.goods_number = goods_number;
	}
	@Column(nullable=false)
	public int getGoods_weight() {
		return goods_weight;
	}
	public void setGoods_weight(int goods_weight) {
		this.goods_weight = goods_weight;
	}
	@Column(nullable=false)
	public float getMarket_price() {
		return market_price;
	}
	public void setMarket_price(float market_price) {
		this.market_price = market_price;
	}
	@Column(nullable=false)
	public float getShop_price() {
		return shop_price;
	}
	public void setShop_price(float shop_price) {
		this.shop_price = shop_price;
	}
	@Column(nullable=false)
	public float getPromote_price() {
		return promote_price;
	}
	public void setPromote_price(float promote_price) {
		this.promote_price = promote_price;
	}
	@Column(nullable=false,columnDefinition="date")
	public Date getPromote_start_date() {
		return promote_start_date;
	}
	public void setPromote_start_date(Date promote_start_date) {
		this.promote_start_date = promote_start_date;
	}
	@Column(nullable=false,columnDefinition="date")
	public Date getPromote_end_date() {
		return promote_end_date;
	}
	public void setPromote_end_date(Date promote_end_date) {
		this.promote_end_date = promote_end_date;
	}
	@Column(nullable=false)
	public String getGoods_brief() {
		return goods_brief;
	}
	public void setGoods_brief(String goods_brief) {
		this.goods_brief = goods_brief;
	}
	@Column(nullable=false,columnDefinition="date")
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	@Column(nullable=false,columnDefinition="tinyint(1)")
	public byte getIs_real() {
		return is_real;
	}
	public void setIs_real(byte is_real) {
		this.is_real = is_real;
	}
	
	@Column(nullable=false,columnDefinition="TEXT")
	public String getGoods_desc() {
		return goods_desc;
	}
	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}
	@Column(nullable=false,columnDefinition="binary(1)")
	public byte[] getBin() {
		return bin;
	}
	public void setBin(byte[] bin) {
		this.bin = bin;
	}

}
