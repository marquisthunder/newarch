package com.thinkingtop.kaas.services.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.RuleDAO;
import com.thinkingtop.kaas.services.model.Rule;

@Component("ruleDAOImpl")
public class RuleDAOImpl implements RuleDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource(name="sessionFactory1")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String[] getRecommend(String scheme, String inputItems,
			int outputItemsNum, int outputQuantitye) {
		return null;
	}

	public Rule getRule(int id) {
		Rule rule = null;
		Session session = sessionFactory.getCurrentSession();
		rule = (Rule) session.get(Rule.class, id);
		return rule;
	}

}
