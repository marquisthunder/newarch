package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.Scheme;


/**
 * Scheme database access abstract class
 * @author roadahead
 *
 */
public interface SchemeDAO {
	/**
	 * Returns a Scheme entity class
	 * @param id:Entity class ID
	 * @return
	 */
	public Scheme getScheme(int id);
	
	/**
	 * Put a Scheme data stored in the database
	 * @param Scheme:Scheme implementation class, storage object
	 * @return
	 */
	public boolean save(Scheme Scheme);
	
	/**
	 * Judge whether they exist in the database so that a Scheme data
	 * @param schemeName: schemeName string
	 * @return
	 */
	public boolean isHold(String schemeName);

	/**
	 * Returns a Scheme entity class
	 * @param schemeName: Returns the name of a "schemeName" programme
	 * @return
	 */
	public Scheme getScheme(String schemeName);
}
