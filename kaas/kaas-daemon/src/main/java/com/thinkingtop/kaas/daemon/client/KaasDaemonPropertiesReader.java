package com.thinkingtop.kaas.daemon.client;

import java.io.InputStream;
import java.util.Properties;

public class KaasDaemonPropertiesReader {
	private KaasDaemonPropertiesReader() {
	}

	private static Properties props = null;

	static {
		InputStream is = KaasDaemonPropertiesReader.class.getClassLoader()
				.getResourceAsStream("running.properties");
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
	
	/*public static void main(String args[]) {
		System.out.println(PropertiesReader.getProp("directory"));
	}*/
}
