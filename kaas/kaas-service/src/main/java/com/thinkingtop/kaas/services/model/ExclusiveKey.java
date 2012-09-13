package com.thinkingtop.kaas.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * 这是与数据库对应的存储APIKey数据的实体类
 * @author roadahead
 */
@Entity
public class ExclusiveKey {
	private Long id;
	private String keyString;
	private Kebsite kebsite;
	private boolean activation;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	@ManyToOne
	@JoinColumn(name="kebsiteid")
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
}
