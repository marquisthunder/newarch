package com.thinkingtop.kaas.server.reader;

import java.io.InputStream;
import java.util.Properties;

public class KaasServerPropertiesReader {
	private KaasServerPropertiesReader() {
	}

	private static Properties props = null;

	static {
		InputStream is = KaasServerPropertiesReader.class.getClassLoader()
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
