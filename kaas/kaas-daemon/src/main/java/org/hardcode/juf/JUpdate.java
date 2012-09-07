/*
 * Created on 14-nov-2004
 */
package org.hardcode.juf;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import org.hardcode.juf.sample.NewClassLoader;
import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;
import org.hardcode.juf.update.Update;
import org.hardcode.juf.update.Updates;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Clase base de la librer�a
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class JUpdate {
	/**
	 * Crea un update con los datos del fichero que se pasa como par�metro
	 * 
	 * @param name
	 *            Nombre de la actualizaci�n
	 * @param file
	 *            Fichero con la informaci�n de la actualizaci�n inicial
	 * 
	 * @return Devuelve la informaci�n que el cliente tiene sobre la
	 *         actualizaci�n cuyo nombre se pasa como par�metro. Si hay
	 *         informaci�n en el cliente se lee la misma, si no la hay se
	 *         obtiene la informaci�n del fichero que se pasa como par�metro
	 * 
	 * @throws IOException
	 *             Si se produce un error leyendo los datos del fichero que se
	 *             pasa como par�metro
	 * @throws RuntimeException
	 *             DOCUMENT ME!
	 */
	public UpdateInfo getClientUpdateInformation(String name)
			throws IOException {
		// Trata de leer la informaci�n
		try {
			return getUpdateStatus(name);
		} catch (ClientStatusException e) {
			return null;
		}
	}

	/**
	 * Realiza la actualizaci�n
	 * 
	 * @param clientStatus
	 *            HashMap que se pasa a las actualizaciones cuando se ejecutan
	 * @param name
	 *            Nombre de la aplicaci�n a la que pertenece la actualizaci�n u
	 * @param u
	 *            Actualizacion que se quiere instalar
	 * @param listener
	 *            Listener de eventos
	 * 
	 * @throws BadConfigurationException
	 *             Si la informaci�n descriptiva de la actualizaci�n en el
	 *             servidor no es correcta
	 * @throws ClientStatusException
	 *             Si no se puede actualizar el estado de la actualizaci�n en el
	 *             cliente tras haberla ejecutado por problemas de formato
	 * @throws InstallException
	 *             Si se produce un error en la ejecuci�n instalaci�n
	 * @throws IOException
	 *             Si falla al actualizar el estado de la actualizaci�n en el
	 *             cliente
	 */
	public void doUpdate(HashMap clientStatus, UpdateInfo clientUpdateInfo,
			String name, Update u, ProgressListener listener)
			throws BadConfigurationException, ClientStatusException,
			InstallException, IOException {
		// System.out.println("ssssssssssssssssssssssssssssssssssssssssssss");
		URL jarURL;
		Installer installer = null;
		
		//download(jarURL,new File("src/main/resources/test.jar"));
		
		
		//Runtime.getRuntime().exec("java -jar /home/test.jar");
		try {
			jarURL = new URL(u.getInstaller().getJarUrl());
			//downloadFile(jarURL, "test2.jar");
			//NewClassLoader nc = new NewClassLoader();
			//nc.addPath("src/main/resources");
			
			//System.out.println(new File("src/main/resources/").getAbsoluteFile()+",,,,,,,,,");
			//URLClassLoader loader = new URLClassLoader(new URL[] { jarURL });
			//NewClassLoader loader = new NewClassLoader();
			//loader.addPath("target/classes/test2.jar");
			
		    File file1 = new File("target/classes/test2.jar"); 
		     
            URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader(); 
            Method add;
			try {
				add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
				add.setAccessible(true); 
				add.invoke(classloader, new Object[] { file1.toURI().toURL() });
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
			
			
			//URLClassLoader nc = new URLClassLoader(new URL[] {new File("target/classes").toURL()});
			Enumeration resources = classloader.getResources("");
			while(resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				System.out.println(url+"3333333333333");
			}
			
			//System.out.println((loader.getSystemClassLoader().getResources("").toString()+".................."));
			System.out.println("11111111111111111111111111111111111111111111111");
			String s[] = System.getProperty("java.class.path").split(":");
			for(int i=0;i<s.length;i++) {
				System.out.println(s[i]);
			}
			System.out.println("11111111111111111111111111111111111111111111111");
			
			
			Class c = classloader.loadClass(u.getInstaller().getClassName());
			
			
			//Class s = classloader.loadClass("com.thinkingtop.kaas.database.MyDataSource");
			//System.out.println(s.getName()+" 00000000000000000");
			
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

		// installer.install(clientStatus, clientUpdateInfo, listener);
		setUpdateStatus(name,installer.install(clientStatus, clientUpdateInfo, listener));
		// 1st,5th parameter are null.
		// HashMap clientStatus, ProgressListener listener

	}

	public void download(URL url, File temp) throws IOException, NoServerFileException {
		URLConnection conn = null;
		InputStream is = null;

		try {
			conn = url.openConnection();
			is = url.openStream();
		} catch (IOException e) {
			throw new NoServerFileException(e.getMessage());
		}

		DataInputStream dis = new DataInputStream(is);
		int size = conn.getContentLength();

		DataOutputStream dos;
		dos = new DataOutputStream(new FileOutputStream(temp));

		if (size != 0) {
			byte[] buffer = new byte[10240];
			int n;
			int completed = 0;

			while ((n = dis.read(buffer)) == 10240) {
				completed += n;
				dos.write(buffer);

				//notifyProgress((100 * completed) / size);
			}

			dos.write(buffer, 0, n);
		} else {
			byte[] buffer = new byte[10240];
			int n;

			while ((n = dis.read(buffer)) == 10240) {
				dos.write(buffer);
			}

			dos.write(buffer, 0, n);
		}

		dis.close();
		dos.close();
	}

	public String downloadFile(URL theUrl,String target){
        URLConnection con=null;
       // URL theUrl=null;
        try {
           // theUrl=new URL(url);//建立地址
            con = theUrl.openConnection();//打开连接
            con.setConnectTimeout(30000);
            con.connect();//连接
        } catch (MalformedURLException e) {
            return "给定的URL地址有误，请查看";
        }
        catch (IOException e) {
            return "无法连接到远程机器，请重试！";
        }
        File file = new File("target/classes");
        if(file.exists()==false){
            file.mkdir();
        }
        String type = con.getContentType();
        if (type != null) {
            byte[] buffer = new byte[102400];
            int read;
            try {
                FileOutputStream os = new FileOutputStream(file+"/"+target);
                InputStream in = con.getInputStream();//重定向输入
                while ((read = in.read(buffer)) > 0) {//读取输出
                    os.write(buffer, 0, read);//写入本地文件
                }
                os.close();
                in.close();
            } catch (FileNotFoundException e) {
                return "所要下载的文件不存在！";
            }catch (IOException e) {
                return "读取远程文件时出错！";
            }
        } else {
            return "文件未找着:"+theUrl.getPath();
        }
        return "";
    }

	/**
	 * Obtiene el estado correspondiente al componente con nombre componentName
	 * que se encuentra en el objeto UpdateInfo que se pasa como par�metro
	 * 
	 * @param ui
	 *            Informaci�n de la actualizaci�n
	 * @param packageName
	 *            Nombre del componente del cual se quiere obtener su estado
	 * 
	 * @return Estado del componente o null si no se encuentra un componente con
	 *         ese nombre en el UpdateInfo
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
	 * Obtiene la informaci�n del servidor sobre las actualizaciones disponibles
	 * posteriores a la versi�n actual
	 * 
	 * @param name
	 *            Nombre de la actualizaci�n
	 * @param listener
	 *            Listener del progreso
	 * 
	 * @return Updates disponibles
	 * 
	 * @throws DownloadException
	 *             Si la actualizaci�n existe pero no se puede descargar
	 * @throws ClientStatusException
	 *             Si la actualizaci�n se descarg� pero no se comprende el
	 *             contenido
	 * @throws IOException
	 *             Si falla el proceso de forma gen�rica
	 */
	public Update[] checkUpdates(String name, UpdateInfo clientUpdateInfo,
			ProgressListener listener) throws DownloadException,
			ClientStatusException, IOException {

		Updates serverStatus = getUpdate(clientUpdateInfo, listener);
		Update[] todas = serverStatus.getUpdate();
		ArrayList updates = new ArrayList();

		for (int i = 0; i < todas.length; i++) {
			Status estado = getStatus(clientUpdateInfo,
					todas[i].getComponentName());

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
	 * Obtiene la informaci�n de la actualizaci�n
	 * 
	 * @param st
	 *            Informaci�n de la actualizaci�n que se quiere descargar
	 * @param listener
	 *            Listener del proceso
	 * 
	 * @return Jupdate
	 * 
	 * @throws DownloadException
	 *             Si no se pudo descargar la informaci�n de la update pero �sta
	 *             existe
	 * @throws ClientStatusException
	 *             No se entiende el descriptor de actualizacion
	 * @throws IOException
	 *             Si falla el proceso de forma gen�rica
	 * @throws RuntimeException
	 *             DOCUMENT ME!
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
		 * File temp; temp = File.createTempFile("Jupdate", ".xml");
		 * temp.deleteOnExit(); JUpdateUtilities juu = new JUpdateUtilities();
		 * if (listener != null) { juu.addProgressListener(listener); } try {
		 * juu.download(url, temp); } catch (IOException e4) { throw new
		 * DownloadException(e4); }
		 */
		try {
			/*
			 * InputStreamReader isr = new InputStreamReader(url.openStream());
			 * int b; while ((b = isr.read()) != -1) { System.out.print((char)
			 * b); } isr.close();
			 */

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
	 * Obtiene el estado de la actualizaci�n de nombre name
	 * 
	 * @param name
	 *            Nombre de la actualizaci�n
	 * 
	 * @return La informaci�n de la actualizaci�n o null si no hay ninguna
	 *         actualizaci�n instalada
	 * 
	 * @throws ClientStatusException
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
	 * @param name
	 *            DOCUMENT ME!
	 * @param info
	 * 
	 * @throws ClientStatusException
	 * @throws IOException
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
	 * Obtiene el fichero asociado a una actualizaci�n
	 * 
	 * @param updateName
	 *            Nombre de la actualizaci�n
	 * 
	 * @return Fichero de la actualizaci�n. Puede no existir
	 */
	private File getFile(String updateName) {
		File ret = new File(System.getProperty("user.home") + File.separator
				+ "juf/org.hardcode.juf.JUpdate." + updateName + ".xml");

		return ret;
	}
}

/**
 * 
 *not add test2.jar
 /home/liang/gitLocalRepository/mycode_branch_new/kaas-daemon/target/classes
/home/liang/springsource/apache-maven-3.0.3/repository/org/codehaus/castor/castor-xml/1.3rc1/castor-xml-1.3rc1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/codehaus/castor/castor-core/1.3rc1/castor-core-1.3rc1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-dbcp/commons-dbcp/1.4/commons-dbcp-1.4.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-pool/commons-pool/1.5.4/commons-pool-1.5.4.jar
/home/liang/springsource/apache-maven-3.0.3/repository/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/mysql/mysql-connector-java/5.1.20/mysql-connector-java-5.1.20.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-jdbc/3.1.2.RELEASE/spring-jdbc-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-beans/3.1.2.RELEASE/spring-beans-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-core/3.1.2.RELEASE/spring-core-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-asm/3.1.2.RELEASE/spring-asm-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-tx/3.1.2.RELEASE/spring-tx-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-aop/3.1.2.RELEASE/spring-aop-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-context/3.1.2.RELEASE/spring-context-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-expression/3.1.2.RELEASE/spring-expression-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/jdom/jdom/1.1.3/jdom-1.1.3.jar
/home/liang/springsource/apache-maven-3.0.3/repository/jaxen/jaxen/1.1.3/jaxen-1.1.3.jar
/home/liang/springsource/apache-maven-3.0.3/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/jdom/jdom/1.0/jdom-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xml-apis/xml-apis/1.3.02/xml-apis-1.3.02.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xerces/xercesImpl/2.6.2/xercesImpl-2.6.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xom/xom/1.0/xom-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xerces/xmlParserAPIs/2.6.2/xmlParserAPIs-2.6.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xalan/xalan/2.6.0/xalan-2.6.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/com/ibm/icu/icu4j/2.6.1/icu4j-2.6.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/net/sourceforge/jtds/jtds/1.2.4/jtds-1.2.4.jar 
 */
/***
 * add test2.jar
 11111111111111111111111111111111111111111111111
/home/liang/gitLocalRepository/mycode_branch_new/kaas-daemon/target/classes
/home/liang/springsource/apache-maven-3.0.3/repository/org/codehaus/castor/castor-xml/1.3rc1/castor-xml-1.3rc1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/codehaus/castor/castor-core/1.3rc1/castor-core-1.3rc1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-dbcp/commons-dbcp/1.4/commons-dbcp-1.4.jar
/home/liang/springsource/apache-maven-3.0.3/repository/commons-pool/commons-pool/1.5.4/commons-pool-1.5.4.jar
/home/liang/springsource/apache-maven-3.0.3/repository/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/mysql/mysql-connector-java/5.1.20/mysql-connector-java-5.1.20.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-jdbc/3.1.2.RELEASE/spring-jdbc-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-beans/3.1.2.RELEASE/spring-beans-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-core/3.1.2.RELEASE/spring-core-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-asm/3.1.2.RELEASE/spring-asm-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-tx/3.1.2.RELEASE/spring-tx-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-aop/3.1.2.RELEASE/spring-aop-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-context/3.1.2.RELEASE/spring-context-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/springframework/spring-expression/3.1.2.RELEASE/spring-expression-3.1.2.RELEASE.jar
/home/liang/springsource/apache-maven-3.0.3/repository/org/jdom/jdom/1.1.3/jdom-1.1.3.jar
/home/liang/springsource/apache-maven-3.0.3/repository/jaxen/jaxen/1.1.3/jaxen-1.1.3.jar
/home/liang/springsource/apache-maven-3.0.3/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/jdom/jdom/1.0/jdom-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xml-apis/xml-apis/1.3.02/xml-apis-1.3.02.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xerces/xercesImpl/2.6.2/xercesImpl-2.6.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xom/xom/1.0/xom-1.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xerces/xmlParserAPIs/2.6.2/xmlParserAPIs-2.6.2.jar
/home/liang/springsource/apache-maven-3.0.3/repository/xalan/xalan/2.6.0/xalan-2.6.0.jar
/home/liang/springsource/apache-maven-3.0.3/repository/com/ibm/icu/icu4j/2.6.1/icu4j-2.6.1.jar
/home/liang/springsource/apache-maven-3.0.3/repository/net/sourceforge/jtds/jtds/1.2.4/jtds-1.2.4.jar
11111111111111111111111111111111111111111111111
 *
 */
 
