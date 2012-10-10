package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.SchemeDAO;
import com.thinkingtop.kaas.services.model.Scheme;

/**
 * Scheme database access abstract class
 * @author roadahead
 *
 */
@Component("schemeDAOImpl")
public class SchemeDAOImpl implements SchemeDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Returns a Scheme entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public Scheme getScheme(int id){
		Scheme scheme = null;
		Session session = sessionFactory.getCurrentSession();
		scheme = (Scheme)session.get(Scheme.class,id);
		return scheme;
	}
	
	/**
	 * Put a Scheme data stored in the database
	 * @param Scheme:Scheme implementation class, storage object
	 * @return
	 */
	public boolean save(Scheme scheme) {
		Session session = sessionFactory.getCurrentSession();
		session.save(scheme);
		return true;
	}
	/**
	 * Judge whether they exist in the database so that a Scheme data
	 * @param schemeName: schemeName string
	 * @return
	 */
	public boolean isHold(String schemeName) {
		boolean ishold = false;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from Scheme scheme " + " where scheme.schemeName = '"+schemeName+"'").addEntity(Scheme.class);
		List<Scheme> schemes = (List<Scheme>)q.list();
		if(!schemes.isEmpty()){
			ishold = true;
		}
		return ishold;
	}

	/**
	 * Returns a Scheme entity class
	 * @param schemeName: Returns the name of a "schemeName" programme
	 * @return
	 */
	public Scheme getScheme(String schemeName) {
		Scheme scheme = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from Scheme scheme  where scheme.schemeName = '"+schemeName+"'").addEntity(Scheme.class);
		List<Scheme> schemes = (List<Scheme>)q.list();
		if(!schemes.isEmpty()){
			scheme = schemes.get(0);
		}
		return scheme;
	}
}
