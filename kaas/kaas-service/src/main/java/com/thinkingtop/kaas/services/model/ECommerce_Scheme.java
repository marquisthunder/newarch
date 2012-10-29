package com.thinkingtop.kaas.services.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="ECommerce_Scheme",
uniqueConstraints={@UniqueConstraint(columnNames={"ecommerceid","schemeid"})})
public class ECommerce_Scheme {
	private int id;
	private ECommerce ecommerce;
	private Scheme scheme;
	private Date createDate = new Date();
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne()
	@JoinColumn(name="ecommerceid",nullable=false)
	public ECommerce getEcommerce() {
		return ecommerce;
	}
	public void setEcommerce(ECommerce ecommerce) {
		this.ecommerce = ecommerce;
	}
	
	@ManyToOne()
	@JoinColumn(name="schemeid",nullable=false)
	public Scheme getScheme() {
		return scheme;
	}
	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}
	
	@Column(nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
