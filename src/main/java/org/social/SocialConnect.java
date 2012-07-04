package org.social;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.consumer.SocialDataConsumer;
import org.social.data.DataCrafter;
import org.social.data.FilteredMessageList;
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

		Set<String> mentionedSet = new HashSet<String>();
		DataCrafter crafter = new DataCrafter(messageDataList);
		FilteredMessageList craftedResult = crafter.craft(mentionedSet);

		// TODO
		for (MessageData messageData : craftedResult.getPositivList()) {
			System.out.println(messageData.getNetwork());
			System.out.println(messageData.getGeoLocation() + " " + messageData.getMessage());
			System.out.println("---");
		}
	}

	private static void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf/social.properties")));

		System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
		System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));

		System.setProperty("http.proxyHost", properties.getProperty("http.proxyHost"));
		System.setProperty("http.proxyPort", properties.getProperty("http.proxyPort"));

		System.setProperty("derby.system.home", properties.getProperty("derby.system.home"));
	}

}
