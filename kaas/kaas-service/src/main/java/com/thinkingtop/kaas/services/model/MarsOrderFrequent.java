package com.thinkingtop.kaas.services.model;

public class MarsOrderFrequent {
	private String freqSet;
	private Integer support;
	private int level;
	private String ofType;
	public MarsOrderFrequent(){}
	public MarsOrderFrequent(String freqSet,Integer support,int level,String ofType){
		this.freqSet = freqSet;
		this.support = support;
		this.level = level;
		this.ofType = ofType;
	}
	public String getFreqSet() {
		return freqSet;
	}
	public void setFreqSet(String freqSet) {
		this.freqSet = freqSet;
	}
	public Integer getSupport() {
		return support;
	}
	public void setSupport(Integer support) {
		this.support = support;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getOfType() {
		return ofType;
	}
	public void setOfType(String ofType) {
		this.ofType = ofType;
	}

}
