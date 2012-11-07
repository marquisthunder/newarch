package com.thinkingtop.kaas.server.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.thinkingtop.kaas.server.VariablesInit;
import com.thinkingtop.kaas.server.dao.KaasRuleDAO;

public class KaasRuleDAOImplTest {

	@Test
	public void testInsertFromFile() {
		ApplicationContext ac = VariablesInit.ac;
		KaasRuleDAO kaasRuleDAO =  (KaasRuleDAO) ac.getBean("kaasRuleDAO");
		//kaasRuleDAO.insertFromFile(null);
	}

}
