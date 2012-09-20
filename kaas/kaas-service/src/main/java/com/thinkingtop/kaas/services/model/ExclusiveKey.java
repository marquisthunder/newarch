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
	private Kebsite kebsite;
	private Date createData = new Date();
	private boolean activation;
	
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
	@ManyToOne()
	@JoinColumn(name="kebsiteid",nullable=false)
	public Kebsite getKebsite() {
		return kebsite;
	}
	@Column(nullable=false)
	public boolean isActivation() {
		return activation;
	}
	public void setActivation(boolean activation) {
		this.activation = activation;
	}
	public void setKebsite(Kebsite kebsite) {
		this.kebsite = kebsite;
	}
	@Column(nullable=false,unique=true)
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
}
