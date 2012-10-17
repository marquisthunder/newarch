package com.thinkingtop.kaas.etl.reader;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private PropertiesReader() {
	}
	private static PropertiesReader reader = null;
	public static PropertiesReader getInstance() {
		if(reader==null) {
			reader = new PropertiesReader();
		}
		return reader;
	}

	private static Properties props = null;

	static {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("runner.properties");
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
	
	/*public static void main(String args[]) {
		System.out.println(PropertiesReader.getProp("directory"));
	}*/
}

