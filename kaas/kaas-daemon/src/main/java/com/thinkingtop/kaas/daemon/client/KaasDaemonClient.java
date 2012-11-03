package com.thinkingtop.kaas.daemon.client;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.hardcode.juf.BadConfigurationException;
import org.hardcode.juf.ClientStatusException;
import org.hardcode.juf.DownloadException;
import org.hardcode.juf.InstallException;
import org.hardcode.juf.JUpdate;
import org.hardcode.juf.status.UpdateInfo;
import org.hardcode.juf.ui.UpdatePanel;
import org.hardcode.juf.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.daemon.upload.ClientMain;
import com.thinkingtop.kaas.services.service.ExclusiveKeyService;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceService;


/** 
 * 
 */
public class KaasDaemonClient {
	private static final Logger logger = LoggerFactory.getLogger(KaasDaemonClient.class.getName());
	
	public static void main(String[] args) {
	/*	URL wsdlURL;
		try {
			wsdlURL = new URL("http://localhost:8080/kaas-service/services/Service?wsdl");
		
			QName serviceQName = new QName("http://service.services.kaas.thinkingtop.com/", "ExclusiveKeyServiceService");
			QName portQName = new QName("http://service.services.kaas.thinkingtop.com/", "ExclusiveKeyServicePort");
			Service service = Service.create(wsdlURL, serviceQName);
			ExclusiveKeyService port = (ExclusiveKeyService) service.getPort(portQName, ExclusiveKeyService.class);
			
			System.out.println(port.getAPIKeyState("jingdong", "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ").get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//System.out.println(getAPIKeyState("jingdong", "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ").get(1));
		KaasDaemonClient jus = new KaasDaemonClient();
		jus.run();
		
		
		/*
		 * for web service validate
		 */
		/*KaasDeamonAPIKeyValidator validator = KaasDeamonAPIKeyValidator.newInstance();
		List<String> result = validator.getAPIKeyState("jingdong", "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ");
		if(!result.get(0).equals("2")) {
			logger.info("not illegal");
		}
		else {
			KaasDaemonClient jus = new KaasDaemonClient();
			jus.run();
		}*/
//		KaasDaemonClient jus = new KaasDaemonClient();
//		jus.run();
	}

	public void run() {
		
		JUpdate update = new JUpdate();
		UpdateInfo clientUpdateInfo = null;
		try {
			// sample is the name of update component..in this case,the name is
			// specified.
			clientUpdateInfo = update.getClientUpdateInformation("sample");
			// if clientUpdateInfo is not null,
			// means the File(System.getProperty("user.home") + File.separator
			// +"juf/org.hardcode.juf.JUpdate." + updateName + ".xml" is
			// existed.
			// otherwise, the program would update the clientUpdateInfo set the
			// urlPrefix with an address.
			if (clientUpdateInfo == null) {
				clientUpdateInfo = new UpdateInfo();
				clientUpdateInfo.setUrlPrefix("http://"+ KaasDaemonPropertiesReader.getInstance().getProperty("updateServerIp") + ":8081/kaas/updates.xml");
			}
		} catch (IOException e) {
			logger.info("Could not get the information from the updated");
			System.exit(-1);
		}

		Update[] actualizaciones = null;
		try {
			// the function checkUpdates will use the UrlPrefix to download a
			// xml file...
			// the sample od this xml looks like:
			/*
			 * <?xml version="1.0" encoding="UTF-8" ?> <updates> <update
			 * component-name="sample" version="1"> <installer
			 * class-name="SampleUpdate"
			 * jar-url="http://localhost:8080/juf/kaas-etl-1.0.jar" />
			 * <feature>I am content</feature> </update> </updates>
			 */
			// then the program display all the informations in the dialog..
			// the user could download the jar and install it(after choose
			// it.)...
			// go into the function checkUpdates to find more information..
			actualizaciones = update.checkUpdates("sample", clientUpdateInfo,null);
			UpdatePanel up = new UpdatePanel();
			up.setModel(actualizaciones);
			final JDialog jf = new JDialog();
			// jf.setModal(true);

			jf.setModalityType(ModalityType.APPLICATION_MODAL);
			jf.getContentPane().add(up, java.awt.BorderLayout.CENTER);
			JButton j_exit;
			j_exit = new JButton("sure");
			j_exit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					jf.dispose();
				}
			});
			jf.getContentPane().add(j_exit, java.awt.BorderLayout.SOUTH);
			jf.setSize(new Dimension(400, 400));
			// jf.show();//
			jf.setVisible(true);
			// System.out.println("==================================");
			actualizaciones = up.getSelectedUpdates();

		} catch (DownloadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientStatusException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			for (int i = 0; i < actualizaciones.length; i++) {
				/*
				 * load the url jar into memory..
				 * do it in this way...we donot need to modify the code of "juf" framework
				 */
				URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
				Method add;
				try {
					add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
					add.setAccessible(true);
					add.invoke(classLoader, new Object[] {new URL(actualizaciones[i].getInstaller().getJarUrl())});
				}catch (Exception e) {
					// TODO: handle exception
				}
				update.doUpdate(null, clientUpdateInfo, "sample",actualizaciones[i], null);
			}
		} catch (BadConfigurationException e2) {
			e2.printStackTrace();
		} catch (ClientStatusException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InstallException e) {
			e.printStackTrace();
		}
		JFrame js = new JFrame();
		js.setLayout(new FlowLayout());
		js.add(new JLabel("update successfully"));
		js.setSize(400, 400);
		js.setVisible(true);
		new ClientMain().startUpload(null);
		// System.exit(0);
	}

	/*private static void p(Object o) {
		System.out.println(o);
	}*/
	 private static List<String> getAPIKeyState(String name, String key) {
		 ExclusiveKeyServiceService service = new ExclusiveKeyServiceService();
		 ExclusiveKeyService port = service.getExclusiveKeyServicePort();
		 return port.getAPIKeyState(name, key);
	 }
}
