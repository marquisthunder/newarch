package com.thinkingtop.kaas.services.model;

/**
 * Order combination entity class
 * @author roadahead
 *
 */
public class KaasOrderFrequent {
	private String combination;
	private Integer frequent;
	private int itemNum;
	private String ofType;
	public KaasOrderFrequent(){}
	public KaasOrderFrequent(String freqSet,Integer support,int level,String ofType){
		this.combination = freqSet;
		this.frequent = support;
		this.itemNum = level;
		this.ofType = ofType;
	}
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		combination = combination;
	}
	public Integer getFrequent() {
		return frequent;
	}
	public void setFrequent(Integer frequent) {
		frequent = frequent;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getOfType() {
		return ofType;
	}
	public void setOfType(String ofType) {
		this.ofType = ofType;
	}
	

}
