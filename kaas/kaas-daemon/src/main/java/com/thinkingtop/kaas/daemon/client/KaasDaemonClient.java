package com.thinkingtop.kaas.daemon.client;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.hardcode.juf.BadConfigurationException;
import org.hardcode.juf.ClientStatusException;
import org.hardcode.juf.DownloadException;
import org.hardcode.juf.InstallException;
import org.hardcode.juf.JUpdate;
import org.hardcode.juf.status.UpdateInfo;
import org.hardcode.juf.ui.UpdatePanel;
import org.hardcode.juf.update.Update;

/** 
 * 
 */
public class KaasDaemonClient {

	public static void main(String[] args) {

		

		// KaasDaemonClient jus = new KaasDaemonClient();
		// jus.run();
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
				clientUpdateInfo.setUrlPrefix("http://"
						+ KaasDaemonPropertiesReader.getInstance().getProperty(
								"updateServerIp") + ":8080/kaas/updates.xml");
			}
		} catch (IOException e) {
			System.err
					.println("Could not get the information from the updated");
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
			actualizaciones = update.checkUpdates("sample", clientUpdateInfo,
					null);
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

				update.doUpdate(null, clientUpdateInfo, "sample",
						actualizaciones[i], null);
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
		// System.exit(0);
	}
}
