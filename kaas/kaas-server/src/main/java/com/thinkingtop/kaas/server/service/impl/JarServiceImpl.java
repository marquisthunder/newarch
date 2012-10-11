package com.thinkingtop.kaas.server.service.impl;

import java.util.Set;

import com.thinkingtop.kaas.server.dao.JarDAO;
import com.thinkingtop.kaas.server.model.KaasJarInfo;
import com.thinkingtop.kaas.server.service.JarService;

/**
 * Hello world!
 *
 */
public class JarServiceImpl implements JarService {

	public JarDAO jarDAO;

	public JarDAO getJarDAO() {
		return jarDAO;
	}

	public void setJarDAO(JarDAO jarDAO) {
		this.jarDAO = jarDAO;
	}

	@Override
	public void addJarInfo(KaasJarInfo info) {
		jarDAO.addJarInfo(info);
		
	}

	@Override
	public void deleteJarInfo(String name) {
		jarDAO.deleteJarInfo(name);
	}

	@Override
	public void updateJarInfo(KaasJarInfo info) {
		jarDAO.updateJarInfo(info);
	}

	@Override
	public Set<KaasJarInfo> getFirstJars() {
		return jarDAO.getFirstJars();
	}

	@Override
	public KaasJarInfo getFirstJar() {
		return jarDAO.getFirstJar();
	}

	
}
