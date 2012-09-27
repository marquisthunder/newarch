package com.thinkingtop.kaas.services.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.core.annotation.Order;
/**
 * It is with the database corresponding to the stored user information data entity class
 * @author roadahead
 */
@Entity
public class ECommerce {
	private Long id;
	private Date createDate = new Date();
	private String ecommerceName;
	private Set<ExclusiveKey> exclusiveKey = new HashSet<ExclusiveKey>();
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy="ecommerce",
			cascade={CascadeType.ALL}
	)
	public Set<ExclusiveKey> getExclusiveKey() {
		return exclusiveKey;
	}
	public void setExclusiveKey(Set<ExclusiveKey> exclusiveKey) {
		this.exclusiveKey = exclusiveKey;
	}
	@Column(nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(nullable=false,unique=true)
	public String getEcommerceName() {
		return ecommerceName;
	}
	public void setEcommerceName(String ecommerceName) {
		this.ecommerceName = ecommerceName;
	}
}
