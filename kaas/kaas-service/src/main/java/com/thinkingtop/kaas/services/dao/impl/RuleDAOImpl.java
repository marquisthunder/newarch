package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
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

	public List<Rule> getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		String sql = "select * from Rule where  products ='"+inputItems;
		for(int i=0;i<outputItemsNum;i++){
			if(i==0){
				sql += "' and recommendation regexp '^[a-zA-Z0-9]";
			}else{
				sql += ",[a-zA-Z0-9]";
			}
		}
		sql += "$' order by confidence desc limit 0,"+outputQuantitye;
		List<Rule> rules= null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery(sql).addEntity(Rule.class);
		rules = (List<Rule>)q.list();
		return rules;
	}

}
