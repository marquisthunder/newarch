package com.thinkingtop.kaas.server;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.server.model.KaasJarInfo;
import com.thinkingtop.kaas.server.service.JarService;


public class Test {
	@org.junit.Test
	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		new ClassPathXmlApplicationContext();
		JarService jarService = (JarService) ac.getBean("jarService");
		//KaasJarInfo info = new KaasJarInfo();
		//info.setUser("222");
		//info.setJarName("name");
		//jarService.addJarInfo(info);
		//System.out.println(jarService.getFirstJar());
		//jarService.deleteJarInfo("wo");
		Iterator<KaasJarInfo> ite = jarService.getFirstJars().iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next().getJarName());
		}
	}
}
