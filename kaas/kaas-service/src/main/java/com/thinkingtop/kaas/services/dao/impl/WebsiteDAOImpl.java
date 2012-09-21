package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.WebsiteDAO;
import com.thinkingtop.kaas.services.model.Website;


/**
 * The user information database access implementation class
 * @author roadahead
 *
 */
@Component("websiteDAOImpl")
public class WebsiteDAOImpl implements WebsiteDAO {
	
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
	public Website getWebsite(Long id) {
		Website website = null;
		Session session = sessionFactory.getCurrentSession();
		website = (Website)session.get(Website.class,id);
		return website;
	}
	
	/**
	 * To the database stored in a user information recording
	 * @param website:The stored user information
	 * @return
	 */
	public boolean save(Website website) {
		Session session = sessionFactory.getCurrentSession();
		session.save(website);
		return true;
	}
	
	/**
	 * Return a user information recording
	 * @param websiteName:The user name
	 * @return
	 */
	public Website getWebsite(String websiteName) {
		Website website = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from Website website " + " where website.websiteName = '"+websiteName+"'").addEntity(Website.class);
		List<Website> websites = (List<Website>)q.list();
		if(!websites.isEmpty()){
			website = websites.get(0);
		}
		return website;
	}

}
