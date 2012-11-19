package com.thinkingtop.kaas.server.jar.monitor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.server.jar.maintenance.Maintenance;
import com.thinkingtop.kaas.server.model.KaasJarInfo;
import com.thinkingtop.kaas.server.reader.KaasServerPropertiesReader;
import com.thinkingtop.kaas.server.service.JarBeanFactory;

public class JWatch{
	private static final Logger logger = LoggerFactory.getLogger(JWatch.class.getName());

	public void startMonitor() {
		Path myDir = Paths.get(KaasServerPropertiesReader.getProp("directory"));

		try {
			WatchService watchService = myDir.getFileSystem().newWatchService();
			myDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			/*
			 * Keep polling for events on the watched directory,
			 */
			for (;;) {
				WatchKey watckKey = watchService.take();

				// Poll all the events queued for the key
				for (WatchEvent<?> event : watckKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					switch (kind.name()) {
					case "ENTRY_MODIFY":
						System.out.println("Modified: " + event.context());
						break;
					case "ENTRY_DELETE":
						System.out.println("Delete: " + event.context());
						break;
					case "ENTRY_CREATE":
						//System.out.println("Create: " + event.context());
						logger.info("Create: " + event.context());
						File f = new File(KaasServerPropertiesReader.getProp("directory")+ event.context().toString());
						// f.setLastModified(System.currentTimeMillis());
						//System.out.println("Create: "+ new Date(f.lastModified()));
						logger.info("Create: "+ new Date(f.lastModified()));
						Date d = new Date(f.lastModified());
						Date d2= new Date(f.lastModified()+5000);
						System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

						Maintenance m = (Maintenance)JarBeanFactory.newInstance().getBean("maintenance");
						KaasJarInfo info = new KaasJarInfo();
						info.setExpired(d2);
						info.setLastModified(d);
						info.setJarName(f.getName());
						info.setUser("my");
						m.addJarInfo(info);
						break;
					}
				}
				// reset is invoked to put the key back to ready state
				boolean valid = watckKey.reset();

				// If the key is invalid, just exit.
				if (!valid) {
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
}
