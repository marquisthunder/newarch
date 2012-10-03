package com.thinkingtop.kaas.server.webapps.monitor;
//choose this method
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;

import com.thinkingtop.kaas.server.jar.maintenance.Maintenance;
import com.thinkingtop.kaas.server.jar.reader.PropertiesReader;

public class WatchServiceMonitor {

    public static void main(String[] args) {

        //define a folder root
        Path myDir = Paths.get(PropertiesReader.getProp("directory"));       

        try {
           WatchService watcher = myDir.getFileSystem().newWatchService();
           myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
           StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);


           /*
            * Keep polling for events on the watched directory,
            */
           for(;;){
               WatchKey watckKey = watcher.take();

               //Poll all the events queued for the key
               for ( WatchEvent<?> event: watckKey.pollEvents()){
                   WatchEvent.Kind<?> kind = event.kind();
                   switch (kind.name()){
                       case "ENTRY_MODIFY":
                           System.out.println("Modified: "+event.context());
                           break;
                       case "ENTRY_DELETE":
                           System.out.println("Delete: "+event.context());
                           break;
                       case "ENTRY_CREATE":
                    	   System.out.println("Create: "+event.context());
                    	   File f = new File(PropertiesReader.getProp("directory")+event.context().toString());
                    	   //f.setLastModified(System.currentTimeMillis());
                    	   
                    	   
                    	   System.out.println("Create: "+new Date(f.lastModified()));
                    	   Date d = new Date(f.lastModified());
                    	   Maintenance.newInstance().addJarInfo(d, f.getName());
                    	   
                           break;
                   }
               }
               //reset is invoked to put the key back to ready state
               boolean valid = watckKey.reset();

               //If the key is invalid, just exit.
               if ( !valid){
                   break;
               }
           }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
