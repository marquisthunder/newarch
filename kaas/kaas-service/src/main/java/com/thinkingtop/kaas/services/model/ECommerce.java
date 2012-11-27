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

/**
 * It is with the database corresponding to the stored user information data
 * entity class
 * 
 * @author roadahead
 */
@Entity
public class ECommerce {
	private int id;
	private Date createDate = new Date();
	private String ecommerceName;
	private Set<ExclusiveKey> exclusiveKeys = new HashSet<ExclusiveKey>();
	private Set<ECommerce_Scheme> ecommerce_scheme = new HashSet<ECommerce_Scheme>();

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "ecommerce", cascade = { CascadeType.ALL })
	public Set<ExclusiveKey> getExclusiveKeys() {
		return exclusiveKeys;
	}

	public void setExclusiveKeys(Set<ExclusiveKey> exclusiveKeys) {
		this.exclusiveKeys = exclusiveKeys;
	}

	@Column(nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(nullable = false, unique = true)
	public String getEcommerceName() {
		return ecommerceName;
	}

	public void setEcommerceName(String ecommerceName) {
		this.ecommerceName = ecommerceName;
	}

	@OneToMany(mappedBy = "ecommerce", cascade = { CascadeType.ALL })
	public Set<ECommerce_Scheme> getEcommerce_scheme() {
		return ecommerce_scheme;
	}

	public void setEcommerce_scheme(Set<ECommerce_Scheme> ecommerce_scheme) {
		this.ecommerce_scheme = ecommerce_scheme;
	}

}
