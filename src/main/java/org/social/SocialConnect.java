package org.social;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.consumer.SocialDataConsumer;
import org.social.data.MessageData;

public class SocialConnect {

	private static Logger logger = LoggerFactory.getLogger(SocialConnect.class);

	public static void main(String[] args) {
		try {
			loadProperties();
		} catch (IOException e) {
			logger.error("Shutting down.", e);
			System.exit(0);
		}

		SocialDataConsumer consumer = new SocialDataConsumer();
		List<MessageData> messageDataList = consumer.consumeData();

		// TODO 
		for (MessageData messageData : messageDataList) {
			logger.info(messageData.toJson().toString());
		}
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
