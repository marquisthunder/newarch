package com.thinkingtop.kaas.services.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * It is with the database corresponding to the stored user information data entity class
 * @author roadahead
 */
@Entity
public class Kebsite {
	private Long id;
	private String kebsiteName;
	private Set<ExclusiveKey> exclusiveKey = new HashSet<ExclusiveKey>();
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getKebsiteName() {
		return kebsiteName;
	}
	public void setKebsiteName(String kebsiteName) {
		this.kebsiteName = kebsiteName;
	}
	@OneToMany(mappedBy="kebsite",
			cascade={CascadeType.ALL}
	)
	public Set<ExclusiveKey> getExclusiveKey() {
		return exclusiveKey;
	}
	public void setExclusiveKey(Set<ExclusiveKey> exclusiveKey) {
		this.exclusiveKey = exclusiveKey;
	}
}
