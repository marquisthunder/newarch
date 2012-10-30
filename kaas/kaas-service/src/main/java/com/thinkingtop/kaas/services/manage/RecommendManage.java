package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.implfile.RuleDAOFileImpl;

@Component("recommendManage")
public class RecommendManage {
	static Logger logger=Logger.getLogger(RecommendManage.class);
	private SchemeManage schemeManage;
	private RuleDAOFileImpl ruleDAOFileImpl;
	
	public String[] getRecommend(String endUser, String scheme,
			String inputItems, int outputItemsNum, int outputQuantitye) {
		String[] Recommend = ruleDAOFileImpl.getRecommend(scheme,inputItems, outputItemsNum, outputQuantitye);
		return Recommend;
	}
	
	public SchemeManage getSchemeManage() {
		return schemeManage;
	}
	
	@Resource(name="schemeManage")
	public void setSchemeManage(SchemeManage schemeManage) {
		this.schemeManage = schemeManage;
	}
	public RuleDAOFileImpl getRuleDAOFileImpl() {
		return ruleDAOFileImpl;
	}
	
	@Resource(name="ruleDAOFileImpl")
	public void setRuleDAOFileImpl(RuleDAOFileImpl ruleDAOFileImpl) {
		this.ruleDAOFileImpl = ruleDAOFileImpl;
	}

}
