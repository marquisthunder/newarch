package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.KebsiteDAO;
import com.thinkingtop.kaas.services.model.Kebsite;


/**
 * The user information database access implementation class
 * @author roadahead
 *
 */
@Component("kebsiteDAOImpl")
public class KebsiteDAOImpl implements KebsiteDAO {
	
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public Kebsite getKebsite(Long id) {
		Kebsite kebsite = null;
		Session session = sessionFactory.getCurrentSession();
		kebsite = (Kebsite)session.get(Kebsite.class,id);
		return kebsite;
	}
	
	/**
	 * To the database stored in a user information recording
	 * @param kebsite:The stored user information
	 * @return
	 */
	public boolean save(Kebsite kebsite) {
		Session session = sessionFactory.getCurrentSession();
		session.save(kebsite);
		return true;
	}
	
	/**
	 * Return a user information recording
	 * @param KebsiteName:The user name
	 * @return
	 */
	public Kebsite getKebsite(String kebsiteName) {
		Kebsite kebsite = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from Kebsite kebsite " + " where kebsite.kebsiteName = '"+kebsiteName+"'").addEntity(Kebsite.class);
		List<Kebsite> kebsites = (List<Kebsite>)q.list();
		if(!kebsites.isEmpty()){
			kebsite = kebsites.get(0);
		}
		return kebsite;
	}

}
