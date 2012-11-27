package com.thinkingtop.kaas.services.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Scheme {
	private int id;
	private Date createDate = new Date();
	private String schemeName;
	private String algorithmNames;
	private Set<ECommerce_Scheme> ecommerce_scheme = new HashSet<ECommerce_Scheme>();
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

	@Column(nullable=false)
	public String getAlgorithmNames() {
		return algorithmNames;
	}
	public void setAlgorithmNames(String algorithmNames) {
		this.algorithmNames = algorithmNames;
	}
	
	@OneToMany(mappedBy = "scheme", cascade = { CascadeType.ALL })
	public Set<ECommerce_Scheme> getEcommerce_scheme() {
		return ecommerce_scheme;
	}
	public void setEcommerce_scheme(Set<ECommerce_Scheme> ecommerce_scheme) {
		this.ecommerce_scheme = ecommerce_scheme;
	}
	
	@Column(nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
