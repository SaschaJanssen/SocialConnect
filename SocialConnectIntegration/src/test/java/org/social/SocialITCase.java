package org.social;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class SocialITCase {
	public SocialITCase() {
		try {
			loadProperties();
		} catch (IOException e) {
			return;
		}

		System.setProperty("derby.system.home", "target/runtime/derby/");
	}

	private static void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf/social.properties")));

		System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
		System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));

		System.setProperty("http.proxyHost", properties.getProperty("http.proxyHost"));
		System.setProperty("http.proxyPort", properties.getProperty("http.proxyPort"));

	}
}
