package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

public class KaasPay {
	public static void logResult(String sWord) {
		String log_path = "/var/lib/openshift/e6ad0434ec5a4dcba7091c65c852a52b/jbossews-1.0/jbossews-1.0/logs/jbossews-1.0/jbossews-1.0/logs/kaaslog/";
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
