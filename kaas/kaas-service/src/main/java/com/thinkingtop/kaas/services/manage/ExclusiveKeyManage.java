package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.ECommerce;

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
	 * A data storage
	 * @param ecommerce:user name
	 * @param keyString:user APIKey
	 */
	public void add(ECommerce ecommerce, StringBuffer keyString) {
		ExclusiveKey ek = new ExclusiveKey();
		ek.setEcommerce(ecommerce);
		ek.setKeyString(keyString.toString());
		exclusiveKeyDAO.save(ek);
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

	/**
	 * Judge whether have the authority
	 * @param APIKey:Ownership of the APIKey
	 * @return
	 */
	public boolean isActivation(String APIKey) {
		ExclusiveKey exclusiveKey = exclusiveKeyDAO.getExclusiveKey(APIKey);
		if(exclusiveKey!=null&&exclusiveKey.getState()==2){
			return true;
		}
		return false;
	}

	/**
	 * Judge whether they exist in the database so that a data
	 * @param ecommerceName:user name
	 * @param APIKey:user APIKey
	 * @return
	 */
	public boolean isHold(String ecommerceName, String APIKey) {
		ExclusiveKey exclusiveKey = exclusiveKeyDAO.getExclusiveKey(APIKey);
		if(exclusiveKey!=null&&exclusiveKey.getEcommerce().getEcommerceName().equals(ecommerceName)){
			return true;
		}
		return false;
	}
	/**
	 * Judge whether they exist in the database so that a APIKey data
	 * @param keyString:The APIKey string
	 * @return If the database of the existence of such APIKey ture is returned return false
	 */
	public boolean isHold(StringBuffer keyString) {
		return exclusiveKeyDAO.isHold(keyString.toString());
	}

	@Resource(name="exclusiveKeyDAOImpl")
	public void setExclusiveKeyDAO(ExclusiveKeyDAO exclusiveKeyDAO) {
		this.exclusiveKeyDAO = exclusiveKeyDAO;
	}

	/**
	 * Returns a APIKey entity class
	 * @param aPIKey:The returned APIKey string
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(String keyString) {
		return exclusiveKeyDAO.getExclusiveKey(keyString);
	}
	
}
