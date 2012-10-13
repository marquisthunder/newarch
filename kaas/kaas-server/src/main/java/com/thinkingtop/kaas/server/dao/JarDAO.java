package com.thinkingtop.kaas.server.dao;

import java.util.List;

import com.thinkingtop.kaas.server.model.KaasJarInfo;

public interface JarDAO {
	public void addJarInfo(KaasJarInfo info);
	public void deleteJarInfo(String name);
	public void updateJarInfo(KaasJarInfo info);
	public List<KaasJarInfo> getFirstJars();
	public KaasJarInfo getFirstJar();
}