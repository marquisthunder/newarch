package com.thinkingtop.kaas.server.jar.reader;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private PropertiesReader() {
	}

	private static Properties props = null;

	static {
		InputStream is = PropertiesReader.class.getClassLoader()
				.getResourceAsStream("default.properties");
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

	public static String getProp(String key) {
		return props.getProperty(key);
	}

	
	public static void main(String args[]) {
		System.out.println(PropertiesReader.getProp("directory"));
	}
}
