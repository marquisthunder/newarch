package com.thinkingtop.kaas.etl.model;
/**
 * 
 * table item
 */
		
public class KaasItem {

	private String itemId;
	private String itemName;
	private int itemNumber;// item bar code
	private float marketPrice;
	private float shopPrice;

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public float getShopPrice() {
		return shopPrice;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public void setShopPrice(float shopPrice) {
		this.shopPrice = shopPrice;
	}
}
