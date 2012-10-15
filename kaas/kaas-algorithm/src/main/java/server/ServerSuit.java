package server;

import java.io.File;
import java.util.HashMap;

import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.UpdateInfo;

public class ServerSuit implements Installer{
public static void main(String[] args) {
	String path = new File("").getAbsolutePath();
	path = "/home/roadahead/workspace/newarch/kaas/dist/ddd";
	int i ;
	if(path.endsWith("kaas-package")){
		path = path + "/../dist/";
		System.out.println(path);
	}else if((i = path.lastIndexOf("dist"))!=-1){
		System.out.println(i);
		System.out.println(path.substring(0,i)+"dist");
	}
}

public UpdateInfo install(HashMap arg0, UpdateInfo arg1, ProgressListener arg2)
		throws InstallException {
	// TODO Auto-generated method stub
	return null;
}
}
