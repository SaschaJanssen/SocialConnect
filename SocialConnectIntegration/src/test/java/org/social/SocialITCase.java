package org.social;

import java.io.File;

import org.social.core.util.UtilProperties;

public abstract class SocialITCase {

	private String derbyHome = "target/runtime/derby/";

	public SocialITCase() {
		File dbHome = new File(derbyHome);
		if (!dbHome.exists()) {
			throw new RuntimeException("The test derby Database is not initialised. Please run the derby scripts.");
		}

		loadProperties();

		System.setProperty("derby.system.home", derbyHome);
	}

	private static void loadProperties() {

		System.setProperty("https.proxyHost",
				UtilProperties.getPropertyValue("conf/social.properties", "https.proxyHost"));
		System.setProperty("https.proxyPort",
				UtilProperties.getPropertyValue("conf/social.properties", "https.proxyPort"));

		System.setProperty("http.proxyHost",
				UtilProperties.getPropertyValue("conf/social.properties", "http.proxyHost"));
		System.setProperty("http.proxyPort",
				UtilProperties.getPropertyValue("conf/social.properties", "http.proxyPort"));

	}
}
