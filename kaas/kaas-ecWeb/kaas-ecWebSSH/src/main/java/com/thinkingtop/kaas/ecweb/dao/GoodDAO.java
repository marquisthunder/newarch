package com.thinkingtop.kaas.ecweb.dao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.ecweb.model.Good;

@Component("goodDAO")
public class GoodDAO {
	static Logger logger=Logger.getLogger(GoodDAO.class);
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Good getGood(int id){
		Session session = sessionFactory.getCurrentSession();
		Good good = (Good)session.get(Good.class, id);
		return good;
	}
}
