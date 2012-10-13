package com.thinkingtop.kaas.server.service;

import java.util.List;
import java.util.Set;

import com.thinkingtop.kaas.server.model.KaasJarInfo;


/**
 * interface JarService
 * 
 */
public interface JarService {
	public void addJarInfo(KaasJarInfo info);
	public void deleteJarInfo(String name);
	public void updateJarInfo(KaasJarInfo info);
	public List<KaasJarInfo> getFirstJars();
	public KaasJarInfo getFirstJar();
}
