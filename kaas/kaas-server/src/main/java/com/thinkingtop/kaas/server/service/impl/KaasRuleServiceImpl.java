package com.thinkingtop.kaas.server.service.impl;

import java.io.File;

import com.thinkingtop.kaas.server.dao.KaasRuleDAO;
import com.thinkingtop.kaas.server.model.KaasRule;
import com.thinkingtop.kaas.server.service.KaasRuleService;

public class KaasRuleServiceImpl implements KaasRuleService{
	
	public KaasRuleDAO kaasRuleDAO;
	
	public KaasRuleDAO getKaasRuleDAO() {
		return kaasRuleDAO;
	}

	public void setKaasRuleDAO(KaasRuleDAO kaasRuleDAO) {
		this.kaasRuleDAO = kaasRuleDAO;
	}

	@Override
	public void insertFromFile(File file) {
		kaasRuleDAO.insertFromFile(file);
	}


}
