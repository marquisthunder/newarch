package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.ECommerce;

/**
 * The user information database access abstract class
 * @author roadahead
 *
 */
public interface ECommerceDAO {
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public ECommerce getECommerce(int id);
	
	/**
	 * Return a user information recording
	 * @param ecommerceName:The user name
	 * @return
	 */
	public ECommerce getECommerce(String ecommerceName);
	
	/**
	 * To the database stored in a user information recording
	 * @param ecommerce:The stored user information
	 * @return
	 */
	public boolean save(ECommerce ecommerce);

	public ECommerce getECommerceAndScheme(String ecommerceName);
}
