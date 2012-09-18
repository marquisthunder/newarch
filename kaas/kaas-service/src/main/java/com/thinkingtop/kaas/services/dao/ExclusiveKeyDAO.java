package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.ExclusiveKey;

/**
 * APIKey database access abstract class
 * @author roadahead
 *
 */
public interface ExclusiveKeyDAO {
	/**
	 * Returns a APIKey entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(Long id);
	
	/**
	 * Put a APIKey data stored in the database
	 * @param exclusiveKey:APIKey implementation class, storage object
	 * @return
	 */
	public boolean save(ExclusiveKey kebsite);
	
	/**
	 * Judge whether they exist in the database so that a APIKey data
	 * @param APIKey:The APIKey string
	 * @return
	 */
	public boolean isHold(String APIKey);

	/**
	 * Returns a APIKey entity class
	 * @param aPIKey:The returned APIKey string
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(String aPIKey);
}
