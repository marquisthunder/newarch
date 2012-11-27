package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ECommerceDAO;
import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.ECommerce_Scheme;


/**
 * The user information database access implementation class
 * @author roadahead
 *
 */
@Component("ecommerceDAOImpl")
public class ECommerceDAOImpl implements ECommerceDAO {
	
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
	public ECommerce getECommerce(int id) {
		ECommerce ecommerce = null;
		Session session = sessionFactory.getCurrentSession();
		ecommerce = (ECommerce)session.get(ECommerce.class,id);
		return ecommerce;
	}
	
	/**
	 * To the database stored in a user information recording
	 * @param ecommerce:The stored user information
	 * @return
	 */
	public boolean save(ECommerce ecommerce) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ecommerce);
		return true;
	}
	
	/**
	 * Return a user information recording
	 * @param ecommerceName:The user name
	 * @return
	 */
	public ECommerce getECommerce(String ecommerceName) {
		ECommerce ecommerce = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from ECommerce ecommerce " + " where ecommerce.ecommerceName = '"+ecommerceName+"'").addEntity(ECommerce.class);
		List<ECommerce> ecommerces = (List<ECommerce>)q.list();
		if(!ecommerces.isEmpty()){
			ecommerce = ecommerces.get(0);
		}
		return ecommerce;
	}

	@Override
	public ECommerce getECommerceAndScheme(String ecommerceName) {
		ECommerce ecommerce = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from ECommerce ecommerce " + " where ecommerce.ecommerceName = '"+ecommerceName+"'").addEntity(ECommerce.class);
		List<ECommerce> ecommerces = (List<ECommerce>)q.list();
		if(!ecommerces.isEmpty()){
			ecommerce = ecommerces.get(0);
			Set<ECommerce_Scheme> ec_ss = ecommerce.getEcommerce_scheme();
			//System.out.println(ec_s);
			for(ECommerce_Scheme ec_s : ec_ss.toArray(new ECommerce_Scheme[ec_ss.size()])){
				ec_s.getId();
			};
		}
		return ecommerce;
	}

}
