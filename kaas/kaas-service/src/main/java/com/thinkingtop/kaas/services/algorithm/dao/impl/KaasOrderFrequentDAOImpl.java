package com.thinkingtop.kaas.services.algorithm.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.algorithm.dao.implfile.KaasOrderFrequentDAOFileImpl;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;

@Component("kaasOrderFrequentDAOImpl")
public class KaasOrderFrequentDAOImpl implements KaasOrderFrequentDAO {
	static Logger logger=Logger.getLogger(KaasOrderFrequentDAOFileImpl.class);
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int submit(KaasOrderFrequent of) {
		Session session = sessionFactory.getCurrentSession();
		session.save(of);
		return 1;
	}

	public int submit() {
		return 1;
	}

	public KaasOrderFrequent findOneByProperty(String myFreqSet) {
		KaasOrderFrequent kaasOrderFrequent = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from OrderFrequent orderFrequent " 
		+ " where orderFrequent.combination = '"+myFreqSet+"'").addEntity(KaasOrderFrequent.class);
		List<KaasOrderFrequent> orderFrequents = (List<KaasOrderFrequent>)q.list();
		if(!orderFrequents.isEmpty()){
			kaasOrderFrequent = orderFrequents.get(0);
		}
		return kaasOrderFrequent;
	}

	
	public long size() {
		Session session = sessionFactory.getCurrentSession();
		String hql ="select count(*) from  OrderFrequent";
		Query q = session.createSQLQuery(hql);
		Object count =  q.uniqueResult();
		long i = Long.valueOf(count.toString());
		return i;
	}

	public KaasOrderFrequent getKeyMarsOrderFrequent(long id) {
		Session session = sessionFactory.getCurrentSession();
		KaasOrderFrequent kaasOrderFrequent = (KaasOrderFrequent) session.get(KaasOrderFrequent.class, id);
		return kaasOrderFrequent;
	}

	public void clearOrderFrequent() {
		String hql = "drop table OrderFrequent";
		Session session = sessionFactory.getCurrentSession();
		//logger.info("hql: ---"+hql);
        Query query = session.createSQLQuery(hql);
        query.executeUpdate();
        
        hql = "CREATE TABLE `OrderFrequent` (";
        hql += "`id` bigint(20) NOT NULL AUTO_INCREMENT,";
        hql += "`combination` varchar(255) NOT NULL,";
        hql += "`frequent` int(11) NOT NULL,";
        hql += "`itemNum` int(11) NOT NULL,";
        hql += "`ofType` varchar(255) NOT NULL,";
        hql += "PRIMARY KEY (`id`),";
        hql += "UNIQUE KEY `combination` (`combination`))";
        query = session.createSQLQuery(hql);
        query.executeUpdate();
        		
	}

	public boolean isHold(KaasOrderFrequent of){
		Session session = sessionFactory.getCurrentSession();
		String hql ="select count(*) from  OrderFrequent where combination = '" + of.getCombination()+"'";
		logger.info("hql: ---"+hql);
		Query q = session.createSQLQuery(hql);
		Object count =  q.uniqueResult();
		int i = Integer.parseInt(count.toString());
		if(i>0){
			return true;
		}
		return false;
	}
	
	public void update(KaasOrderFrequent of) {
		Session session = sessionFactory.getCurrentSession();
		session.update(of);
	}

}
