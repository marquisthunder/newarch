package com.thinkingtop.kaas.etl.reader;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private PropertiesReader() {
	}

	private static Properties props = null;


	static {
		//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("runner.properties");
		
		InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream("runner.properties");
		props = new Properties();
		try {
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}
	
	public static void main(String args[]) {
		System.out.println(PropertiesReader.getProperty("inType"));
	}
}

