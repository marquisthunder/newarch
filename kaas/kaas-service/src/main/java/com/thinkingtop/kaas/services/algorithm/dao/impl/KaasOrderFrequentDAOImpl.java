package com.thinkingtop.kaas.services.algorithm.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
		//logger.info("-----------add sessionFactory");
		session.save(of);
		return 1;
	}

	public int submit() {
		return 0;
	}

	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet) {
		return null;
	}

	public int size() {
		return 0;
	}

	public KaasOrderFrequent getKeyMarsOrderFrequent(long id) {
		return null;
	}

	public void clearOrderFrequent() {
		
	}

}
