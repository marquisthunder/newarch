package com.thinkingtop.kaas.etl.writer;

import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.JUpdateUtilities;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.*;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.etl.reader.PropertiesReader;
import com.thinkingtop.kaas.etl.result.Result;

/**
 * 
 * @author Xiaojie Liang at 2012.01.20
 * 
 */
public class ResultWriter {

	private static Logger logger = LoggerFactory.getLogger(ResultWriter.class.getName()); 
	static final String fileName = PropertiesReader.getInstance().getProperty("out");// the name of the target file
	private static Element root;
	private static ResultWriter reader = null;

	private ResultWriter() {
	}

	public static ResultWriter getInstance() {// use the instructor to
												// initialize the target file
		if (reader == null) {
			reader = new ResultWriter();
			/*try {
				File f = new File("src/main/resources/"+fileName);
				if(!f.exists()) {
					//f.createNewFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		return reader;
	}

	public void writeToXml(Map<String, LinkedHashSet<String>> map) {
		//SAXBuilder sb1 = new SAXBuilder();
		//Element root1 = null;
		
		Element kaasOrders = new Element("kaasOrders");
		Document des = new Document(kaasOrders);
		/*
		 * write item list
		 */
		Set<String> key = map.keySet();
		Iterator<String> ite = key.iterator();
		while (ite.hasNext()) {
			String itemId = ite.next();
			LinkedHashSet<String> set = map.get(itemId);
			Iterator<String> ite2 = set.iterator();
			StringBuilder sb = new StringBuilder();
			while(ite2.hasNext()) {
				String item = ite2.next();
				if(!item.equals("0")) {
					sb.append(item+",");
				}
			}
			Element kaasOrder = new Element("kaasOrder");
			kaasOrder.setAttribute("id",itemId);
			kaasOrder.setText(sb.toString());
			kaasOrders.addContent(kaasOrder);
		}
		/*
		 * ends
		 */
		
		
		//root1.addContent(items);
		XMLOutputter xmlOutput = new XMLOutputter();
		File dir=null;
		String s = null;
		try {
			//System.out.println(Thread.currentThread().getContextClassLoader().getResource("src/main/resources/"+fileName).toURI());
			//s = Thread.currentThread().getContextClassLoader().getResource(fileName).toURI().getPath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlOutput.setFormat(Format.getPrettyFormat());

		try {
			FileWriter fr = new FileWriter(fileName);
			//fileName = "../dist/result.xml"
			xmlOutput.output(des, fr);
			fr.close();
			des = null;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		
	}
	
	public static void main(String args[]) {
		ResultWriter.getInstance().writeToXml(new Result().getResult());
	}

}
