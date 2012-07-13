package org.social;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.social.core.util.UtilProperties;

public abstract class SocialITCase {
	public SocialITCase() {
		loadProperties();

		System.setProperty("derby.system.home", "target/runtime/derby/");
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
