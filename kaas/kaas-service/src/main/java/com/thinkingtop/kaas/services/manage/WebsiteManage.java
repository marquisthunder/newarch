package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.WebsiteDAO;
import com.thinkingtop.kaas.services.model.Website;

/**
 * This is the access of user information management
 * @author roadahead
 *
 */
@Component("websiteManage")
public class WebsiteManage{
	private WebsiteDAO websiteDAO;
	
	/**
	 * To the database stored in a user information recording
	 * @param website:The stored user information
	 * @return
	 */
	public boolean add(Website website) {
		websiteDAO.save(website);
		return true;
	}
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public Website getWebsite(long id){
		return websiteDAO.getWebsite(id);
	}
	
	/**
	 * Return a user information recording
	 * @param websiteName:The user name
	 * @return
	 */
	public Website getWebsite(String websiteName){
		return websiteDAO.getWebsite(websiteName);
	}
	
	
	public WebsiteDAO getWebsiteDAO() {
		return websiteDAO;
	}
	@Resource(name="websiteDAOImpl")
	public void setWebsiteDAO(WebsiteDAO websiteDAO) {
		this.websiteDAO = websiteDAO;
	}
	public boolean isHold(String websiteName) {
		Website website = getWebsite(websiteName);
		if(website==null){
			return false;
		}else{
			return true;
		}
	}
}
