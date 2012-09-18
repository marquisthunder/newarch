package com.thinkingtop.kaas.etl.reader;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {
	public String getProperty(String name) {
		FileInputStream is = null;
		Properties dbProps = new Properties();
		try {
			is = new FileInputStream("src/test/resources/runner.properties");
			dbProps.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbProps.getProperty(name);
	}
	public static void main(String args[]) {
		System.out.print(new PropertiesReader().getProperty("xml"));
	}

}
