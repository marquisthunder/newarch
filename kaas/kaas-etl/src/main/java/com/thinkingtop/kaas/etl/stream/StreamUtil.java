package com.thinkingtop.kaas.etl.stream;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.thinkingtop.kaas.etl.reader.PropertiesReader;

public class StreamUtil {
	public static InputStream getEtlXMLStream() {
		//PropertiesReader reader = PropertiesReader.getInstance();
		String type = PropertiesReader.getProperty("inType");
		if(type.equals("native")) { 
			try {
			URL xmlUrl =  new File(PropertiesReader.getProperty("in")).toURI().toURL();
			InputStream isXML = xmlUrl.openStream();
			return isXML;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(type.equals("network")) {
			try {
				URL xmlUrl =  new URL(PropertiesReader.getProperty("in"));
				InputStream isXML = xmlUrl.openStream();
				return isXML;
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
