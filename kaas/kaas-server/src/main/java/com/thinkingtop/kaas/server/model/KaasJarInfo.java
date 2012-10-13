package com.thinkingtop.kaas.server.model;

import java.util.Date;

/**
 * @author kaas 
 * This class is used for describing the attributes of jar files.
 * The attributes includes jarname, expired time, id, lastmodified,
 *         user.
 */
public class KaasJarInfo {

	private Date expired;
	private String id;
	private String jarName;
	private Date lastModified;
	private String user;

	public Date getExpired() {
		return expired;
	}

	public String getId() {
		return id;
	}

	public String getJarName() {
		return jarName;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public String getUser() {
		return user;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
