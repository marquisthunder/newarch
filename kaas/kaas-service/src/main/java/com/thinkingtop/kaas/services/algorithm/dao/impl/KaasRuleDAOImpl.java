package com.thinkingtop.kaas.services.algorithm.dao.impl;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;

@Component("kaasRuleDAOImpl")
public class KaasRuleDAOImpl implements KaasRuleDAO {
	static Logger logger=Logger.getLogger(KaasRuleDAOImpl.class);
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int submit(KaasRule r) {
		Session session = sessionFactory.getCurrentSession();
		session.save(r);
		return 1;
	}

	public int submit(String dataName) {
		return 0;
	}

	public List<KaasRule> getRule(String string) {
		return null;
	}

	public List<String> getRuleMap(String Item) {
		return null;
	}

	public void clearMarsRuleAll() {
		String hql = "drop table Rule";
		Session session = sessionFactory.getCurrentSession();
		//logger.info("hql: ---"+hql);
        Query query = session.createSQLQuery(hql);
        query.executeUpdate();
        
        hql = "CREATE TABLE `Rule` (";
        hql += "`id` int(11) NOT NULL AUTO_INCREMENT,";
        hql += "`confidence` double NOT NULL,";
        hql += "`flag` varchar(255) NOT NULL,";
        hql += "`products` varchar(255) DEFAULT NULL,";
        hql += "`recommendation` varchar(255) DEFAULT NULL,";
        hql += "PRIMARY KEY (`id`),";
        hql += "UNIQUE KEY `products` (`products`,`recommendation`))";
        query = session.createSQLQuery(hql);
        query.executeUpdate();
	}
	
	public KaasRule getRule(int id) {
		Session session = sessionFactory.getCurrentSession();
		KaasRule r = (KaasRule) session.get(KaasRule.class, id);
		return r;
	}

	public int update(KaasRule r) {
		Session session = sessionFactory.getCurrentSession();
		session.update(r);
		return 1;
	}
	
	public KaasRule getRule(String products,String recommendation){
		String hql = "select * from Rule where products ='"+products+"' and recommendation='"+recommendation+"'";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery(hql).addEntity(KaasRule.class);
		List<KaasRule> rules = (List<KaasRule>)q.list();
		if(!rules.isEmpty()){
			return rules.get(0);
		}
		return null;
	}
	
	public Iterator<KaasRule> getRuleIterator(){
		String hql = "from KaasRule order by Products";
		Session session = sessionFactory.getCurrentSession();
		logger.info("hql:----"+hql);
		Query q = session.createQuery(hql);
		logger.info("hql1:----"+hql);
		Iterator<KaasRule> ier = q.iterate();
		return ier;
	}
	
	public boolean isHold(String products,String recommendation){
		String hql = "select count(*) from Rule where products ='"+products+"' and recommendation='"+recommendation+"'";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery(hql);
		Object count =  q.uniqueResult();
		int i = Integer.parseInt(count.toString());
		if(i>0){
			return true;
		}
		return false;
	}

	public List<KaasRule> getRules(String products) {
		String hql = "select * from Rule where products ='"+products+"'";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery(hql).addEntity(KaasRule.class);
		List<KaasRule> rules = (List<KaasRule>)q.list();
		return rules;
	}
}
