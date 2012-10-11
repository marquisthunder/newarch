package com.thinkingtop.kaas.server.dao;

import java.util.Set;

import com.thinkingtop.kaas.server.model.KaasJarInfo;

public interface JarDAO {
	public void addJarInfo(KaasJarInfo info);
	public void deleteJarInfo(String name);
	public void updateJarInfo(KaasJarInfo info);
	public Set<KaasJarInfo> getFirstJars();
	public KaasJarInfo getFirstJar();
}