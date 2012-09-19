package com.thinkingtop.kaas.service.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import com.jolbox.bonecp.BoneCPDataSource;

public class BeforeTest {
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
            st.execute("runscript from '" + new DefaultResourceLoader().getResource("schema.sql").getURL().toString() + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
