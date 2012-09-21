package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.Website;

/**
 * The user information database access abstract class
 * @author roadahead
 *
 */
public interface WebsiteDAO {
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public Website getWebsite(Long id);
	
	/**
	 * Return a user information recording
	 * @param websiteName:The user name
	 * @return
	 */
	public Website getWebsite(String websiteName);
	
	/**
	 * To the database stored in a user information recording
	 * @param website:The stored user information
	 * @return
	 */
	public boolean save(Website website);
}
