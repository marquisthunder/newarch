package com.thinkingtop.kaas.server.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.thinkingtop.kaas.server.dao.KaasRuleDAO;
import com.thinkingtop.kaas.server.model.KaasRule;

public class KaasRuleDAOImpl implements KaasRuleDAO{

	private static final Logger logger = LoggerFactory.getLogger(KaasRuleDAOImpl.class.getName());
	
	/*JdbcTemplate*/
	private JdbcTemplate jdbcTemplate;

	/* set to inject the proxy class of jdbcTemplate*/
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insert(KaasRule kaasRule) {
		
	}

	@Override
	public void insertFromFile(File file) {
		file = new File("../dist/data/kaas-server/scheme/date2");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			String[] args = null;
			KaasRule kaasRule = new KaasRule();
			while((line=br.readLine())!=null) {
				args = line.split("==");
				kaasRule.setProducts(args[0]);
				kaasRule.setRecommendation(args[1]);
				kaasRule.setConfidence(Double.parseDouble(args[2]));
				kaasRule.setFlag(args[3]);
				String str = "insert into rule(products,recommendation,confidence,flag) "+"values ('" + kaasRule.getProducts() + "','" +kaasRule.getRecommendation() +"', '"+kaasRule.getConfidence()+"','"+kaasRule.getFlag()+"')";
				logger.info(str);
				jdbcTemplate.update(str);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
