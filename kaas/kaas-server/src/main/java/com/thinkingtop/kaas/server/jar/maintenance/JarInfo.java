package com.thinkingtop.kaas.server.jar.maintenance;

import java.util.Date;

public class JarInfo {
	private Date expired;
	private String jarName;

	public Date getExpired() {
		return expired;
	}

	public String getJarName() {
		return jarName;
	}
	
	public void setExpired(Date expired) {
		this.expired = expired;
	}
	
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
}
