package org.social.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.CustomerDAO;
import org.social.core.entity.helper.MessageDAO;
import org.social.core.util.UtilDateTime;

public class SocialConnect {

	private static Logger logger = LoggerFactory.getLogger(SocialConnect.class);

	public static void main(String[] args) {
		try {
			loadProperties();
		} catch (IOException e) {
			logger.error("Shutting down.", e);
			System.exit(0);
		}

		CustomerDAO customerDao = new CustomerDAO();
		MessageDAO messageDao = new MessageDAO();

		List<Customers> customers = customerDao.getAllCustomersAndKeywords();
		for (Customers customer : customers) {
			SocialDataConsumer consumer = new SocialDataConsumer();
			FilteredMessageList filteredMessageDataList = consumer.consumeData(customer);
			consumer = null;

			messageDao.storeMessages(filteredMessageDataList);

			customerDao.updateCustomerNetworkAccess(customer, UtilDateTime.nowTimestamp());
		}


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
