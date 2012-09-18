package com.thinkingtop.kaas.services.manage;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;

/**
 * This is the APIKey management class
 * @author roadahead
 *
 */
@Component("exclusiveKeyManage")
public class ExclusiveKeyManage{
	private ExclusiveKeyDAO exclusiveKeyDAO;

	/**
	 * Put a APIKey data stored in the database
	 * @param exclusiveKey:APIKey implementation class, storage object
	 * @return
	 */
	public boolean add(ExclusiveKey exclusiveKey) {
		exclusiveKeyDAO.save(exclusiveKey);
		return true;
	}
	
	/**
	 * Returns a APIKey entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(long id){
		return exclusiveKeyDAO.getExclusiveKey(id);
	}
	
	
	
	public ExclusiveKeyDAO getExclusiveKeyDAO() {
		return exclusiveKeyDAO;
	}
	
	@Resource(name="exclusiveKeyDAOImpl")
	public void setExclusiveKeyDAO(ExclusiveKeyDAO exclusiveKeyDAO) {
		this.exclusiveKeyDAO = exclusiveKeyDAO;
	}

	/**
	 * Judge whether they exist in the database so that a APIKey data
	 * @param keyString:The APIKey string
	 * @return If the database of the existence of such APIKey ture is returned return false
	 */
	public boolean isHold(StringBuffer keyString) {
		return exclusiveKeyDAO.isHold(keyString.toString());
	}

	/**
	 * A data storage
	 * @param kebsite:user name
	 * @param keyString:user APIKey
	 */
	public void add(Kebsite kebsite, StringBuffer keyString) {
		ExclusiveKey ek = new ExclusiveKey();
		ek.setKebsite(kebsite);
		ek.setKeyString(keyString.toString());
		exclusiveKeyDAO.save(ek);
	}
	/**
	 * Judge whether they exist in the database so that a data
	 * @param kebsiteName:user name
	 * @param APIKey:user APIKey
	 * @return
	 */
	public boolean isHold(String kebsiteName, String APIKey) {
		ExclusiveKey exclusiveKey = exclusiveKeyDAO.getExclusiveKey(APIKey);
		if(exclusiveKey!=null&&exclusiveKey.getKebsite().getKebsiteName().equals(kebsiteName)){
			return true;
		}
		return false;
	}

	/**
	 * Judge whether have the authority
	 * @param APIKey:Ownership of the APIKey
	 * @return
	 */
	public boolean isActivation(String APIKey) {
		ExclusiveKey exclusiveKey = exclusiveKeyDAO.getExclusiveKey(APIKey);
		if(exclusiveKey!=null&&exclusiveKey.isActivation()){
			return true;
		}
		return false;
	}
	
}
