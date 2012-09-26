package com.thinkingtop.kaas.service.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import com.jolbox.bonecp.BoneCPDataSource;

public class BeforeTest {
    static Logger logger=Logger.getLogger(BeforeTest.class);
	@Test
	public void test() {
	}

	public static void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		BoneCPDataSource dataSource = (BoneCPDataSource)ctx.getBean("dataSource");
		try {
            Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            st.execute("drop all objects;");
            logger.info("sqlURL:------"+new DefaultResourceLoader().getResource("schema.sql").getURL().toString());
            st.execute("runscript from '" + new File(new DefaultResourceLoader().getResource("schema.sql").getURL().toURI()).getPath() + "'");
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}

}
