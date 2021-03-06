package com.thinkingtop.kaas.services.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import com.jolbox.bonecp.BoneCPDataSource;
import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;

public class BeforeTest {
	static Logger logger = Logger.getLogger(BeforeTest.class);

	@Test
	public void test() {
	}

	public static void init() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		BoneCPDataSource dataSource = (BoneCPDataSource) ctx
				.getBean("dataSource");
		try {
			Connection conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			st.execute("drop all objects;");
			logger.info("sqlURL:------"+ new DefaultResourceLoader().getResource("schema.sql")
							.getURL().toString());
			st.execute("runscript from '"
					+ new File(new DefaultResourceLoader()
							.getResource("schema.sql").getURL().toURI())
							.getPath() + "'");
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void init3() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		BoneCPDataSource dataSource = (BoneCPDataSource) ctx
				.getBean("dataSource3");
		try {
			Connection conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			st.execute("drop all objects;");
			logger.info("sqlURL:------"+ new DefaultResourceLoader().getResource("schema.sql")
							.getURL().toString());
			st.execute("runscript from '"
					+ new File(new DefaultResourceLoader()
							.getResource("schema.sql").getURL().toURI())
							.getPath() + "'");
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetClass() {
		try {
			Class<?> a = Class
					.forName("com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault");
			Algorithm aa;
			try {
				aa = (Algorithm) a.newInstance();
				logger.info("Algorithm.class.getClassLoader():--   "
						+ Thread.currentThread().getContextClassLoader()
								.getResource(""));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetClassPath(){
		logger.info(
		Thread.currentThread().getContextClassLoader().getResource(""));
		logger.info(BeforeTest.class.getClassLoader().getResource(""));
		logger.info(ClassLoader.getSystemResource(""));
		logger.info(BeforeTest.class.getResource("").toString());
		logger.info(BeforeTest.class.getResource("/"));
		logger.info(new File("").getAbsolutePath());
		logger.info(System.getProperty("user.dir"));
	}

	@Test
	public void testXML() {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(new File("").getAbsolutePath()+ "/../dist/result.xml");
			Element elements = document.getRootElement();
			Iterator<Element> applications = elements.elementIterator();
			while (applications.hasNext()) {
				Element application = applications.next();
				Attribute leaderAttr =application.attribute("id");
				/*logger.info(leaderAttr.getName());
				logger.info(leaderAttr.getValue());
				logger.info(application.getName());
				String str = application.getText();
				str = str.substring(0, str.length()-1);
				logger.info(str);*/
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	public void testFile(){
		File f = new File("../dist");
		logger.info("file path:--"+f.getAbsoluteFile().getPath());
		logger.info("file exists:---"+ f.exists());
	}
	
	@Test
	public void testH2() throws Exception{
		Class.forName("org.h2.Driver");
		   String dbTest2 = "jdbc:h2:/home/roadahead/programs/h2/data/scheme/scheme";
		   Connection conn = DriverManager.getConnection(dbTest2,"sa","");
		   // add application code here  
		   Statement stmt = conn.createStatement();
		    
		   /*stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");  
		   stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');");*/
		   ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");
		    while(rs.next()) {     
		     System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));  
		    }  
		   conn.close();  
	}
	
	@Test
	public void testPattern(){
		String patt = "a";
		Assert.assertEquals(true, patt.matches("^\\p{Alnum}"));
	}

}
