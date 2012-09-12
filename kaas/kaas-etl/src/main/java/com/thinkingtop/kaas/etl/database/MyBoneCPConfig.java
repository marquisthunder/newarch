package com.thinkingtop.kaas.etl.database;

import java.util.Map;

import com.jolbox.bonecp.BoneCPConfig;
import com.thinkingtop.kaas.etl.reader.EtlInfoReader;

public class MyBoneCPConfig extends BoneCPConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the JDBC connection URL.
	 *
	 * @param jdbcUrl to set
	 */
	public void setJdbcUrl(String jdbcUrl) {
		EtlInfoReader reader = EtlInfoReader.getInstance();
		
		Map<String,String> map =(Map<String,String>) reader.getDatasourceParameters();
		super.setJdbcUrl(map.get("jdbcUrl"));
	}

	/**
	 * Sets username to use for connections.
	 *
	 * @param username to set
	 */
	public void setUsername(String username) {
		 EtlInfoReader reader = EtlInfoReader.getInstance();
			
			Map<String,String> map =(Map<String,String>) reader.getDatasourceParameters();
	        super.setUsername( map.get("user"));
	}

	/**
	 * Sets password to use for connections.
	 *
	 * @param password to set.
	 */
	public void setPassword(String password) {
		EtlInfoReader reader = EtlInfoReader.getInstance();
		
		Map<String,String> map =(Map<String,String>) reader.getDatasourceParameters();
		super.setPassword( map.get("password"));
	}
	
	

}