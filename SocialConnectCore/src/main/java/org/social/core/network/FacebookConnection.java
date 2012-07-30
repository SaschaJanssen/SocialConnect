package org.social.core.network;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.query.FacebookQuery;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilProperties;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;

public class FacebookConnection extends SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FacebookClient fbClient = null;
	private String MY_ACCESS_TOKEN = null;

	public FacebookConnection(Customers customer) {
		super(customer);

		loadProperties();

		fbClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

		getCustomersKeywords(Networks.FACEBOOK.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch posts from Facebook for customer: " + super.customer.getCustomerId());
		}

		FacebookQuery query = buildQueryFromKeywords();

		List<Messages> resultMessages = new ArrayList<Messages>();
		Connection<JsonObject> searchResult = fbClient.fetchConnection(query.constructQuery(), JsonObject.class);
		resultMessages.addAll(extractMessageData(searchResult));
		String nextPageUrl = searchResult.getNextPageUrl();

		while (nextPageUrl != null && !nextPageUrl.isEmpty()) {
			searchResult = fbClient.fetchConnectionPage(nextPageUrl, JsonObject.class);
			resultMessages.addAll(extractMessageData(searchResult));
			nextPageUrl = searchResult.getNextPageUrl();
		}

		FilteredMessageList filteredResultMessages = reliabilityAndSentimentMessages(resultMessages);

		return filteredResultMessages;
	}

	private FacebookQuery buildQueryFromKeywords() {
		FacebookQuery fbQuery = new FacebookQuery(super.customerNetworkKeywords);

		String since = UtilDateTime.connvertTimestampToFacebookTime(super.customer.getLastNetworkdAccess());
		fbQuery.setSince(since);
		fbQuery.setType("post");
		fbQuery.setLanguage("en_US");

		return fbQuery;
	}

	private List<Messages> extractMessageData(Connection<JsonObject> searchResult) {

		List<Messages> results = new ArrayList<Messages>();

		for (JsonObject object : searchResult.getData()) {
			if (!object.has("message")) {
				// object could be ignored if no message attribute is set.
				continue;
			}
			Messages messageData = new Messages(Networks.FACEBOOK.getName());

			messageData.setCustomerId(super.customer.getCustomerId());

			JsonObject userData = object.getJsonObject("from");
			messageData.setNetworkUser(userData.getString("name"));
			messageData.setNetworkUserId(userData.getString("id"));

			String fbMessageDate = object.getString("created_time");
			messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(fbMessageDate));

			messageData.setMessage(object.getString("message"));
			messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());

			results.add(messageData);
		}

		return results;
	}

	private void loadProperties() {
		MY_ACCESS_TOKEN = UtilProperties.getPropertyValue("conf/fb.properties", "token");
	}

}
