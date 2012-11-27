package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.model.ExclusiveKey;

/**
 * Realizing of APIKey database access class
 * @author roadahead
 *
 */
@Component("exclusiveKeyDAOImpl")
public class ExclusiveKeyDAOImpl implements ExclusiveKeyDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Returns a APIKey entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(Long id) {
		ExclusiveKey exclusiveKey = null;
		Session session = sessionFactory.getCurrentSession();
		exclusiveKey = (ExclusiveKey)session.get(ExclusiveKey.class,id);
		return exclusiveKey;
	}

	/**
	 * Put a APIKey data stored in the database
	 * @param exclusiveKey:APIKey implementation class, storage object
	 * @return
	 */
	public boolean save(ExclusiveKey exclusiveKey) {
		Session session = sessionFactory.getCurrentSession();
		session.save(exclusiveKey);
		return true;
	}
	/**
	 * Judge whether they exist in the database so that a APIKey data
	 * @param APIKey:The APIKey string
	 * @return
	 */
	public boolean isHold(String APIKey) {
		boolean exclusiveKey = false;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from ExclusiveKey exclusiveKey " + " where exclusiveKey.keyString = '"+APIKey+"'").addEntity(ExclusiveKey.class);
		List<ExclusiveKey> exclusiveKeys = (List<ExclusiveKey>)q.list();
		if(!exclusiveKeys.isEmpty()){
			exclusiveKey = true;
		}
		return exclusiveKey;
	}

	/**
	 * Returns a APIKey entity class
	 * @param aPIKey:The returned APIKey string
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(String aPIKey) {
		ExclusiveKey exclusiveKey = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from ExclusiveKey exclusiveKey  where exclusiveKey.keyString = '"+aPIKey+"'").addEntity(ExclusiveKey.class);
		List<ExclusiveKey> exclusiveKeys = (List<ExclusiveKey>)q.list();
		if(!exclusiveKeys.isEmpty()){
			exclusiveKey = exclusiveKeys.get(0);
		}
		return exclusiveKey;
	}
}
