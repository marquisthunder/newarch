/*
 * Created on 14-nov-2004
 */
package org.hardcode.juf;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;
import org.hardcode.juf.update.Update;
import org.hardcode.juf.update.Updates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * base class 
 *
 * @author kaas 
 */
public class JUpdate {
    /**
     * Create an update with the file data that is passed
     *
      * @Param name Name of the UPDATE
      * @Param file the file with actuicial informacde
      *
      * @Return Returns the information the customer has on
      *
      * @Throws IOException If an error occurs reading the data from file
      * 
      * @Throws RuntimeException
     */
    public UpdateInfo getClientUpdateInformation(String name)
        throws IOException {
        //Try to read information
        try {
            return getUpdateStatus(name);
        } catch (ClientStatusException e) {
            return null;
        }
    }

    /**
    * Perform the update
    *
    * @Param HashMap clientStatus passed to updates when running
    * 
    * @Param name Name which belongs aplication upgrade
    * 
    * @Param or update that you want to install
    * @Param listener Listener Event
    *
    * @Throws BadConfigurationException If the information
    * Updated on the server is not correct
    * @Throws ClientStatusException If you can update the status of the
    * UPDATE on the client after having executed
    * Formatting problems
    * @Throws InstallException If an error occurs in the example
    * install
    * @Throws IOException If you fail to update the status of the application
    * On the client
    */
    public void doUpdate(HashMap clientStatus, UpdateInfo clientUpdateInfo, String name, Update u,
        ProgressListener listener) 
        throws BadConfigurationException, ClientStatusException, 
            InstallException, IOException {
        URL jarURL;
        Installer installer = null;

        try {
            jarURL = new URL(u.getInstaller().getJarUrl());

            URLClassLoader loader = new URLClassLoader(new URL[] { jarURL });
            
            Class c = loader.loadClass(u.getInstaller().getClassName());
            //System.out.println(c.getName()+"------------------------");
            installer = (Installer) c.newInstance();
        } catch (MalformedURLException e) {
            throw new BadConfigurationException(e);
        } catch (ClassNotFoundException e) {
            throw new BadConfigurationException(e);
        } catch (InstantiationException e) {
            throw new BadConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new BadConfigurationException(e);
        }

        
        //installer.install(clientStatus, clientUpdateInfo, listener);
        setUpdateStatus(name,installer.install(clientStatus, clientUpdateInfo, listener));
        //1st,5th parameter are null.
        //HashMap clientStatus, ProgressListener listener
        
        
    }

    /**
    * Gets the state corresponding to the named component componentName
    * Found in the UpdateInfo object passed as parameter
    *
    * @Param ui of the Upgrade Information
    * @Param packageName Name of the component which is to get your
    * state
    *
    * @Return status component or null if there is a component
    * With that name in the UpdateInfo
    */
    private Status getStatus(UpdateInfo ui, String packageName) {
        Status[] status = ui.getStatus();

        for (int i = 0; i < status.length; i++) {
            if (status[i].getComponentName().equals(packageName)) {
                return status[i];
            }
        }

        return null;
    }

    /**
    * Gets the server information about updates
    * Available after the current version
    *
    * @Param name Name of the upgrade
    * @Param listener Listener progress
    *
    * @Return Updates available
    *
    * @Throws DownloadException If the upgrade there but you can not
    * Download
    * @Throws ClientStatusException If the upgrade is download but not
    * Includes the content
    * @Throws IOException If a process fails GenRich
    */
    public Update[] checkUpdates(String name, UpdateInfo clientUpdateInfo, ProgressListener listener)
        throws DownloadException, ClientStatusException, IOException {

        Updates serverStatus = getUpdate(clientUpdateInfo, listener);
        Update[] todas = serverStatus.getUpdate();
        ArrayList updates = new ArrayList();

        for (int i = 0; i < todas.length; i++) {
            Status estado = getStatus(clientUpdateInfo, todas[i].getComponentName());

            if (estado == null) {
                updates.add(todas[i]);
            } else {
                if (estado.getVersion() < todas[i].getVersion()) {
                    updates.add(todas[i]);
                }
            }
        }

        return (Update[]) updates.toArray(new Update[0]);
    }

    /**
    * Get the information from the Upgrade
    *
    * @Param st Information of the upgrade you want to download
    * @Param listener Listener process
    *
    * @Return Jupdate
    *
    * @Throws DownloadException If unable to download the information from the
    * Update but there sta
    * @ Throws ClientStatusException no means the descriptor
    * upgrade
    * @Throws IOException If a process fails GenRich
    * @Throws RuntimeException
    */
    private Updates getUpdate(UpdateInfo st, ProgressListener listener)
        throws DownloadException, ClientStatusException, IOException {
        URL url;

        try {
            url = new URL(st.getUrlPrefix());
        } catch (MalformedURLException e2) {
            throw new RuntimeException("URL no v�lida");
        }

        /*
           File temp;
           temp = File.createTempFile("Jupdate", ".xml");
           temp.deleteOnExit();
           JUpdateUtilities juu = new JUpdateUtilities();
           if (listener != null) {
               juu.addProgressListener(listener);
           }
           try {
               juu.download(url, temp);
           } catch (IOException e4) {
               throw new DownloadException(e4);
           }
         */
        try {
        /*	InputStreamReader isr = new InputStreamReader(url.openStream());
			int b;
			while ((b = isr.read()) != -1)
			{
				System.out.print((char) b);
			}
			isr.close();*/

        	
            Updates update = (Updates) Updates.unmarshal(new InputStreamReader(
                        url.openStream()));

            return update;
        } catch (MarshalException e) {
            throw new ClientStatusException(e);
        } catch (ValidationException e) {
            throw new ClientStatusException(e);
        }
    }

    /**
    * Gets the status of the upgrade of given name
    *
    * @Param name Name of the upgrade
    *
    * @Return The information of update null if no
    * No upgrade installed
    *
    * @ Throws ClientStatusException
    */
    private UpdateInfo getUpdateStatus(String name)
        throws ClientStatusException {
        try {
            File f = getFile(name);
            UpdateInfo info = (UpdateInfo) UpdateInfo.unmarshal(new FileReader(
                        f));

            return info;
        } catch (MarshalException e) {
            throw new ClientStatusException(e);
        } catch (ValidationException e) {
            throw new ClientStatusException(e);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
    * DOCUMENT ME!
    *
    * @Param name DOCUMENT ME!
    * @Param info
    *
    * @Throws ClientStatusException
    * @Throws IOException
    */
    private void setUpdateStatus(String name, UpdateInfo info)
        throws ClientStatusException, IOException {
        try {
            File f = getFile(name);
            info.marshal(new FileWriter(f));
        } catch (MarshalException e) {
            throw new ClientStatusException(e);
        } catch (ValidationException e) {
            throw new ClientStatusException(e);
        }
    }

    /**
    * Gets the file associated with an upgrade
    *
    * @Param name the upgrade UpdateName
    *
    * @Return the upgrade file. Can not be
    */
    private File getFile(String updateName) {
        File ret = new File(System.getProperty("user.home") + File.separator +
                "juf/org.hardcode.juf.JUpdate." + updateName + ".xml");

        return ret;
    }
}
