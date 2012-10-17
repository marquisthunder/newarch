package com.thinkingtop.kaas.services.algorithm.entrance;

import java.util.HashMap;

import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.JUpdateUtilities;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;

import com.thinkingtop.kaas.services.algorithm.manage.JarAlgorithmManage;

public class Entrance implements Installer {
	public static void main(String[] args) {
		JarAlgorithmManage algorithmManage = JarAlgorithmManage
				.getJarAlgorithmManage();
		algorithmManage.runIt();
	}

	public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
			ProgressListener listener) throws InstallException {
		JUpdateUtilities jup = new JUpdateUtilities();
		Status componentStatus = jup.getComponentStatus(status,
				"kaas-algorithm");

		if (componentStatus == null) {
			componentStatus = new Status();
			componentStatus.setComponentName("kaas-algorithm");
			componentStatus.setVersion(new Long("1"));
			status.addStatus(componentStatus);
		} else {
			componentStatus.setVersion(componentStatus.getVersion() + 1);
		}
		JarAlgorithmManage algorithmManage = JarAlgorithmManage
				.getJarAlgorithmManage();
		algorithmManage.runIt();
		return status;
	}
}
