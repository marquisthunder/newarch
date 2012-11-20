/*

Copyright 2009 Wallace Wadge

This file is part of BoneCP.

BoneCP is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

BoneCP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with BoneCP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thinkingtop.kaas.etl.database;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Objects;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.hooks.ConnectionHook;
import com.thinkingtop.kaas.etl.reader.EtlInfoReader;


/**
 * Configuration class.
 *
 * @author wallacew
 */
public class KaasBoneCPConfig extends BoneCPConfig {
	
	public KaasBoneCPConfig() {
		//setClassLoader(this.getClass().getClassLoader());
		System.out.println(getClassLoader()+" woshi111");
		System.out.println(this.getClassLoader()+" woshi222");
		System.out.println(this.getClass().getClassLoader()+" woshi333");
	}
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
	
	/** Loads the given class, respecting the given classloader.
	 * @param clazz class to load
	 * @return Loaded class
	 * @throws ClassNotFoundException
	 */
	/*protected Class<?> loadClass(String clazz) throws ClassNotFoundException {
		System.out.println("我是1: "+this.getClass().getClassLoader());
		System.out.println("我是2: "+getClassLoader());
		if (getClassLoader() == null){
			return Class.forName(clazz);
		}
		return Class.forName(clazz, true, this.getClassLoader());
	}*/
	protected Class<?> loadClass(String clazz) throws ClassNotFoundException {
		System.out.println("来了来了来了来了来了来了来了来了"+clazz);
		return Class.forName(clazz, true, this.getClass().getClassLoader());

	}

	
}