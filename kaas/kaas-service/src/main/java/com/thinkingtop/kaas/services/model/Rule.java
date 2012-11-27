package com.thinkingtop.kaas.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Rule of entity classes
 * @author roadahead
 *
 */
@Entity
@Table(name="Rule",
uniqueConstraints={@UniqueConstraint(columnNames={"products","recommendation"})})
public class Rule {
	private int id;
	private String products;
	private String recommendation;
	private Double confidence;
	private String flag;
	
	@Column(nullable=false)
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	@Column(nullable=false)
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
	@Column(nullable=false)
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	
	@Column(nullable=false)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
