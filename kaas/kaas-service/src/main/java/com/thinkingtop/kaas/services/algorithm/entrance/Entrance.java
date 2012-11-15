package com.thinkingtop.kaas.services.algorithm.entrance;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.JUpdateUtilities;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.thinkingtop.kaas.services.algorithm.dao.impl.KaasOrderFrequentDAOImpl;
import com.thinkingtop.kaas.services.algorithm.dao.impl.KaasRuleDAOImpl;
import com.thinkingtop.kaas.services.algorithm.manage.JarAlgorithmManage;

public class Entrance  implements Installer{
	static Logger logger = Logger.getLogger(Entrance.class);
	public static void main(String[] args) {
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("algorithmbeans.xml");
		JarAlgorithmManage algorithmManage = (JarAlgorithmManage) acx.getBean("jarAlgorithmManage");
		
		ClassPathXmlApplicationContext acxDatabase = new ClassPathXmlApplicationContext("algorithmdatabase.xml");
		SessionFactory sessionFactory = (SessionFactory) acxDatabase.getBean("sessionFactory");
		//SessionFactory sessionFactory = gieSessionFactory();
		
		setSessionFactory(acx, sessionFactory);
		
		algorithmManage.runIt();
		acxDatabase.destroy();
		acx.destroy();
	}
	
	public static void setSessionFactory(ClassPathXmlApplicationContext acx,SessionFactory sessionFactory){
		HibernateTransactionManager txManager = (HibernateTransactionManager)acx.getBean("txManager");
		txManager.setSessionFactory(sessionFactory);
		KaasRuleDAOImpl kaasRuleDAOImpl = (KaasRuleDAOImpl)acx.getBean("kaasRuleDAOImpl");
		kaasRuleDAOImpl.setSessionFactory(sessionFactory);
		KaasOrderFrequentDAOImpl kaasOrderFrequentDAOImpl = (KaasOrderFrequentDAOImpl)acx.getBean("kaasOrderFrequentDAOImpl");
		kaasOrderFrequentDAOImpl.setSessionFactory(sessionFactory);
	}
	
	public static SessionFactory gieSessionFactory(){
		SessionFactory sessionFactory = null;
		try {
			Class<?> sessionf = Class.forName("com.thinkingtop.kaas.services.ReturnSessionFactory.ReturnSessionFactory");
			com.thinkingtop.kaas.services.ReturnSessionFactory.ReturnSessionFactory rsessionFactory = 
					(com.thinkingtop.kaas.services.ReturnSessionFactory.ReturnSessionFactory)sessionf.newInstance();
			sessionFactory = rsessionFactory.getSessionFactory();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
			ProgressListener listener) throws InstallException {
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("algorithmbeans.xml");
		JarAlgorithmManage algorithmManage = (JarAlgorithmManage) acx.getBean("jarAlgorithmManage");
		
		SessionFactory sessionFactory = gieSessionFactory();
		
		if(sessionFactory==null){
			logger.info("No sessionFactory");
		}
		
		setSessionFactory(acx, sessionFactory);
		algorithmManage.runIt();
		acx.destroy();
		
		
		JUpdateUtilities jup = new JUpdateUtilities();
		Status componentStatus = jup.getComponentStatus(status, "kaas-algorithm");

		if (componentStatus == null){
		componentStatus = new Status();
		componentStatus.setComponentName("kaas-algorithm");
		componentStatus.setVersion(new Long("1"));
		status.addStatus(componentStatus);
		}else{
		componentStatus.setVersion(componentStatus.getVersion() + 1);
		}
		return status;
	}
}
