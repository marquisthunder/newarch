package com.thinkingtop.kaas.server.service;

import java.util.Iterator;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.thinkingtop.kaas.server.VariablesInit;
import com.thinkingtop.kaas.server.model.KaasJarInfo;

public class JarServiceTest {

	@Test
	public void testGetFirstJars() {
		ApplicationContext ac = VariablesInit.ac;
		JarService jarService = (JarService) ac.getBean("jarService");
		Iterator<KaasJarInfo> ite = jarService.getFirstJars().iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next().getJarName());
		}
	}

}
