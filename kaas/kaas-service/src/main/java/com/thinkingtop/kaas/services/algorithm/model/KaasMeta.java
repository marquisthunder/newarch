package com.thinkingtop.kaas.services.algorithm.model;

import java.util.Date;

public class KaasMeta {
	private String schemeName;
	private String eCommerceName;
	private Date createDate = new Date();
	
	public boolean createFile(){
		
		return true;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String geteCommerceName() {
		return eCommerceName;
	}
	public void seteCommerceName(String eCommerceName) {
		this.eCommerceName = eCommerceName;
	}
	
}
