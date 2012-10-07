package com.thinkingtop.kaas.etl.reader;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

private PropertiesReader() {}
	
	private static PropertiesReader reader = null;
	private static Map<String,String> map = new HashMap<String, String>();
	public static PropertiesReader getInstance() {// use the instructor to initialize the target file
		if(reader == null) {
			reader = new PropertiesReader();
			Properties props = new Properties();
			try {
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("runner.properties");
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

	/*public static void main(String args[]) {
		System.out.println(PropertiesReader.getInstance().getProperty("xml"));
	}*/

}
