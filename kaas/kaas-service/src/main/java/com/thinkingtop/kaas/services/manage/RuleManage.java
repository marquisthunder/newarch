package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.RuleDAO;
import com.thinkingtop.kaas.services.model.Rule;

@Component("ruleManage")
public class RuleManage {
	private RuleDAO ruleDAO;
	public Rule getRule(int id){
		return ruleDAO.getRule(id);
	}
	public RuleDAO getRuleDAO() {
		return ruleDAO;
	}
	
	@Resource(name="ruleDAOImpl")
	public void setRuleDAO(RuleDAO ruleDAO) {
		this.ruleDAO = ruleDAO;
	}
}
