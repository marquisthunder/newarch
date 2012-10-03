package com.thinkingtop.kaas.server.jar.maintenance;

import java.util.Date;

public class JarInfo {
	private Date expiredDate;
	private String jarName;
	public Date getExpiredDate() {
		return expiredDate;
	}
	public String getJarName() {
		return jarName;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
}
