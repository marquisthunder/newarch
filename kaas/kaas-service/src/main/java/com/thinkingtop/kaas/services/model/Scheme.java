package com.thinkingtop.kaas.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Scheme {
	private int id;
	private String schemeName;
	private ECommerce ecommerce;
	private String algorithmNames;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(nullable=false,unique=true)
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	@ManyToOne()
	@JoinColumn(name="ecommerceid",nullable=false)
	public ECommerce getEcommerce() {
		return ecommerce;
	}
	public void setEcommerce(ECommerce ecommerce) {
		this.ecommerce = ecommerce;
	}
	@Column(nullable=false)
	public String getAlgorithmNames() {
		return algorithmNames;
	}
	public void setAlgorithmNames(String algorithmNames) {
		this.algorithmNames = algorithmNames;
	}
}
