package com.thinkingtop.kaas.services.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * It is with the database corresponding to the stored APIKey data entity class
 * @author roadahead
 */
@Entity
public class ExclusiveKey {
	private Long id;
	private String keyString;
	private ECommerce ecommerce;
	private Date createData = new Date();
	private int state = 1;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable=false,unique=true)
	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	@Column(nullable=false,columnDefinition="int(2) default 1")
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Column(nullable=false)
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	@ManyToOne()
	@JoinColumn(name="ecommerceid",nullable=false)
	public ECommerce getEcommerce() {
		return ecommerce;
	}
	public void setEcommerce(ECommerce ecommerce) {
		this.ecommerce = ecommerce;
	}
	
}
