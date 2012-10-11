package com.thinkingtop.kaas.services.algorithm.entrance;

import java.util.HashMap;

import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.UpdateInfo;

import com.thinkingtop.kaas.services.algorithm.manage.JarAlgorithmManage;

public class Entrance  implements Installer{
	public static void main(String[] args) {
		JarAlgorithmManage algorithmManage = JarAlgorithmManage.getJarAlgorithmManage();
		algorithmManage.runIt();
	}

	@Override
	public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
			ProgressListener listener) throws InstallException {
		JarAlgorithmManage algorithmManage = JarAlgorithmManage.getJarAlgorithmManage();
		algorithmManage.runIt();
		return null;
	}
}
