package org.social.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.CustomerDAO;
import org.social.core.entity.helper.MessageDAO;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilProperties;

public class SocialConnect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public SocialConnect() {
		loadProperties();
	}

	public void start() {
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

		if (logger.isDebugEnabled()) {
			logger.debug("Finished social connect run successfully.");
		}

	}

	private void loadProperties() {

		System.setProperty("https.proxyHost",
				UtilProperties.getPropertyValue("conf/social.properties", "https.proxyHost"));
		System.setProperty("https.proxyPort",
				UtilProperties.getPropertyValue("conf/social.properties", "https.proxyPort"));

		System.setProperty("http.proxyHost",
				UtilProperties.getPropertyValue("conf/social.properties", "http.proxyHost"));
		System.setProperty("http.proxyPort",
				UtilProperties.getPropertyValue("conf/social.properties", "http.proxyPort"));

		System.setProperty("derby.system.home",
				UtilProperties.getPropertyValue("conf/social.properties", "derby.system.home"));
	}

}
