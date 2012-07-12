package org.social.core.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.query.FacebookQuery;
import org.social.core.util.UtilDateTime;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;

public class FacebookConnection extends SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FacebookClient fbClient = null;
	private String MY_ACCESS_TOKEN = null;

	public FacebookConnection(Customers customer) {
		super(customer);

		try {
			loadProperties();
		} catch (IOException e) {
			logger.error("Facebook connection will be ignored, properties couldn't read. ", e);
			return;
		}

		fbClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

		getCustomersKeywords(Networks.FACEBOOK.getName());
	}

	@Override
	public List<Messages> fetchMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch posts from Facebook for customer: " + super.customer.getCustomerId());
		}

		FacebookQuery query = buildQueryFromKeywords();

		List<Messages> results = new ArrayList<Messages>();
		Connection<JsonObject> searchResult = fbClient.fetchConnection(query.constructQuery(), JsonObject.class);
		results.addAll(extractMessageData(searchResult));
		String nextPageUrl = searchResult.getNextPageUrl();

		while (nextPageUrl != null && !nextPageUrl.isEmpty()) {
			searchResult = fbClient.fetchConnectionPage(nextPageUrl, JsonObject.class);
			results.addAll(extractMessageData(searchResult));
			nextPageUrl = searchResult.getNextPageUrl();
		}

		return results;
	}

	private FacebookQuery buildQueryFromKeywords() {
		FacebookQuery fbQuery = new FacebookQuery(super.customerNetworkKeywords);

		String since = UtilDateTime.connvertTimestampToFacebookTime(super.customer.getLastNetworkdAccess());
		fbQuery.setSince(since);
		fbQuery.setType("post");

		return fbQuery;
	}

	public List<Messages> fetchPlace(String query, String center, String distance, String limit, String until) {
		Connection<JsonObject> searchResult = fbClient.fetchConnection("search", JsonObject.class,
				Parameter.with("q", query), Parameter.with("type", "place"), Parameter.with("center", center),
				Parameter.with("distance", distance), Parameter.with("until", until), Parameter.with("limit", limit));

		return extractMessageData(searchResult);
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

	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf/fb.properties")));

		MY_ACCESS_TOKEN = properties.getProperty("token");
	}

}
