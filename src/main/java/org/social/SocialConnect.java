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
import org.social.entity.domain.Customers;
import org.social.entity.domain.Messages;
import org.social.entity.helper.CustomerHelper;
import org.social.entity.helper.MessageHelper;

public class SocialConnect {

	private static Logger logger = LoggerFactory.getLogger(SocialConnect.class);

	public static void main(String[] args) {
		try {
			loadProperties();
		} catch (IOException e) {
			logger.error("Shutting down.", e);
			System.exit(0);
		}

		List<Customers> customers = new CustomerHelper().getAllCustomersAndKeywords();

		SocialDataConsumer consumer = new SocialDataConsumer(customers);
		List<Messages> messageDataList = consumer.consumeData();

		MessageHelper persistMessages = new MessageHelper();
		persistMessages.storeMessages(messageDataList);

		Set<String> mentionedSet = new HashSet<String>();
		mentionedSet.add("#WOLFGANGSSTEAKH");
		mentionedSet.add("@WOLFGANGSSTEAKH");
		DataCrafter crafter = new DataCrafter(messageDataList);
		FilteredMessageList craftedResult = crafter.craft(mentionedSet);

		persistMessages.updateMessages(craftedResult.getNegativeList());
		persistMessages.updateMessages(craftedResult.getPositivList());

		if (logger.isInfoEnabled()) {
			logger.info("Shutdown social connect.");
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
