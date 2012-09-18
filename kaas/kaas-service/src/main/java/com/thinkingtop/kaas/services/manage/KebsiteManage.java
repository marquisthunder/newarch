package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.KebsiteDAO;
import com.thinkingtop.kaas.services.model.Kebsite;

/**
 * This is the access of user information management
 * @author roadahead
 *
 */
@Component("kebsiteManage")
public class KebsiteManage{
	KebsiteDAO kebsiteDAO;
	
	/**
	 * To the database stored in a user information recording
	 * @param kebsite:The stored user information
	 * @return
	 */
	public boolean add(Kebsite kebsite) {
		kebsiteDAO.save(kebsite);
		return true;
	}
	/**
	 * Return a user information recording
	 * @param id:The user ID
	 * @return
	 */
	public Kebsite getKebsite(long id){
		return kebsiteDAO.getKebsite(id);
	}
	
	/**
	 * Return a user information recording
	 * @param KebsiteName:The user name
	 * @return
	 */
	public Kebsite getKebsite(String KebsiteName){
		return kebsiteDAO.getKebsite(KebsiteName);
	}
	
	
	public KebsiteDAO getKebsiteDAO() {
		return kebsiteDAO;
	}
	@Resource(name="kebsiteDAOImpl")
	public void setKebsiteDAO(KebsiteDAO kebsiteDAO) {
		this.kebsiteDAO = kebsiteDAO;
	}
	public boolean isHold(String kebsiteName) {
		Kebsite kebsite = getKebsite(kebsiteName);
		if(kebsite==null){
			return false;
		}else{
			return true;
		}
	}
}
