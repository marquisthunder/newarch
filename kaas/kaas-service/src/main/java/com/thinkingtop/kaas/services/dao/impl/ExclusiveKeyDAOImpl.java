package com.thinkingtop.kaas.services.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;

/**
 * APIKey数据库访问实现类
 * @author roadahead
 *
 */
@Component("exclusiveKeyDAOImpl")
public class ExclusiveKeyDAOImpl implements ExclusiveKeyDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 返回一个APIKey实体类
	 * @param id 所要返回的实体类的ID
	 * @return
	 */
	public ExclusiveKey getExclusiveKey(Long id) {
		ExclusiveKey exclusiveKey = null;
		Session session = sessionFactory.getCurrentSession();
		//System.out.println("111111111111111111111111111");
		exclusiveKey = (ExclusiveKey)session.get(ExclusiveKey.class,id);
		return exclusiveKey;
	}

	/**
	 * 向数据库中存储一条APIKey数据
	 * @param exclusiveKey 所要存储的APIKey的实体类
	 * @return
	 */
	public boolean save(ExclusiveKey exclusiveKey) {
		Session session = sessionFactory.getCurrentSession();
		//System.out.println("222222222222222222222");
		session.save(exclusiveKey);
		return true;
	}
	/**
	 * 判断数据库中是否存在这样的一条APIKey数据
	 * @param APIKey 所要检查的APIKey
	 * @return
	 */
	public boolean isHold(String APIKey) {
		boolean exclusiveKey = false;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery q = session.createSQLQuery("select * from ExclusiveKey exclusiveKey " + " where exclusiveKey.keyString like '"+APIKey+"'").addEntity(ExclusiveKey.class);
		List<ExclusiveKey> exclusiveKeys = (List<ExclusiveKey>)q.list();
		if(!exclusiveKeys.isEmpty()){
			for(ExclusiveKey e : exclusiveKeys){
				if(e.getKeyString().equals(APIKey)){
					exclusiveKey = true;
					return exclusiveKey;
				}
			}
		}
		return exclusiveKey;
	}

}
