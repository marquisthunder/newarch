package com.thinkingtop.kaas.daemon.client;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class KaasDaemonPropertiesReader {
	private static KaasDaemonPropertiesReader reader = null;
	private static Map<String,String> map = new HashMap<String, String>();
	private KaasDaemonPropertiesReader() {
		
	}
	public static KaasDaemonPropertiesReader getInstance() {
		// use the getInstance to initialize the single instance
		if(reader == null) {
			reader = new KaasDaemonPropertiesReader();
			Properties props = new Properties();
			try {
				InputStream in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("default.properties");
				props.load(in);
				Enumeration<?> en = props.propertyNames();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String Property = props.getProperty(key);
					//System.out.println(key + Property);
					map.put(key, Property);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reader;
	}
	
	public String getProperty(String name) {
		return map.get(name);
	}

}
