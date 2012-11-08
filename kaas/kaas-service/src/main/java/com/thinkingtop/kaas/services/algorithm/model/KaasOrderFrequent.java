package com.thinkingtop.kaas.services.algorithm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order combination entity class
 * @author roadahead
 *
 */
@Entity
@Table(name="OrderFrequent")
public class KaasOrderFrequent {
	private long id;
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
	
	@Column(nullable = false)
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		this.combination = combination;
	}
	
	@Column(nullable = false)
	public Integer getFrequent() {
		return frequent;
	}
	public void setFrequent(Integer frequent) {
		this.frequent = frequent;
	}
	@Column(nullable = false)
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	@Column(nullable = false)
	public String getOfType() {
		return ofType;
	}
	public void setOfType(String ofType) {
		this.ofType = ofType;
	}
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


}
