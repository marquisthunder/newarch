package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.Kebsite;

/**
 * The user information database access abstract class
 * @author roadahead
 *
 */
public interface KebsiteDAO {
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public Kebsite getKebsite(Long id);
	
	/**
	 * Return a user information recording
	 * @param KebsiteName:The user name
	 * @return
	 */
	public Kebsite getKebsite(String kebsiteName);
	
	/**
	 * To the database stored in a user information recording
	 * @param kebsite:The stored user information
	 * @return
	 */
	public boolean save(Kebsite kebsite);
}
