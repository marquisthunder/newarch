package com.thinkingtop.kaas.server.webapps.monitor;
//choose this method
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceMonitor {

    public static void main(String[] args) {

        //define a folder root
        Path myDir = Paths.get("D:/");       

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
