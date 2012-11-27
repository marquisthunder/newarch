package com.thinkingtop.kaas.services.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.SchemeDAO;
import com.thinkingtop.kaas.services.model.Scheme;

/**
 * This is the Scheme management class
 * @author roadahead
 */
@Component("schemeManage")
public class SchemeManage {
	private SchemeDAO schemeDAO;
	
	/**
	 * Put a Scheme data stored in the database
	 * @param scheme: implementation class, storage object
	 * @return
	 */
	public boolean add(Scheme scheme) {
		schemeDAO.save(scheme);
		return true;
	}
	
	/**
	 * Returns a Scheme entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public Scheme getScheme(int id){
		return schemeDAO.getScheme(id);
	}
	
	/**
	 * Returns a Scheme entity class
	 * @param schemeName: Returns the name of a "schemeName" programme
	 * @return
	 */
	public Scheme getScheme(String schemeName){
		return schemeDAO.getScheme(schemeName);
	}
	
	public SchemeDAO getSchemeDAO() {
		return schemeDAO;
	}

	@Resource(name="schemeDAOImpl")
	public void setSchemeDAO(SchemeDAO schemeDAO) {
		this.schemeDAO = schemeDAO;
	}
}
