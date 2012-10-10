package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ECommerceDAO;
import com.thinkingtop.kaas.services.model.ECommerce;

/**
 * This is the access of user information management
 * @author roadahead
 *
 */
@Component("ecommerceManage")
public class ECommerceManage{
	private ECommerceDAO ecommerceDAO;
	
	/**
	 * To the database stored in a user information recording
	 * @param ecommerce:The stored user information
	 * @return
	 */
	public boolean add(ECommerce ecommerce) {
		ecommerceDAO.save(ecommerce);
		return true;
	}
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public ECommerce getECommerce(long id){
		return ecommerceDAO.getECommerce(id);
	}
	
	/**
	 * Return a user information recording
	 * @param ecommerceName:The user name
	 * @return
	 */
	public ECommerce getECommerce(String ecommerceName){
		return ecommerceDAO.getECommerce(ecommerceName);
	}
	
	/**
	 * Return a user information recording
	 * @param ecommerceName:The user name
	 * @return
	 */
	public ECommerce getECommerceAndScheme(String ecommerceName){
		return ecommerceDAO.getECommerceAndScheme(ecommerceName);
	}
	
	public ECommerceDAO getECommerceDAO() {
		return ecommerceDAO;
	}
	@Resource(name="ecommerceDAOImpl")
	public void setECommerceDAO(ECommerceDAO ecommerceDAO) {
		this.ecommerceDAO = ecommerceDAO;
	}
	public boolean isHold(String ecommerceName) {
		ECommerce ecommerce = getECommerce(ecommerceName);
		if(ecommerce==null){
			return false;
		}else{
			return true;
		}
	}
}
