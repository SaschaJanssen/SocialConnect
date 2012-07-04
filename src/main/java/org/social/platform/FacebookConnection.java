package org.social.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.data.MessageData;
import org.social.query.FacebookQuery;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;

public class FacebookConnection implements SocialNetworkConnection<FacebookQuery> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FacebookClient fbClient = null;
	private String MY_ACCESS_TOKEN = null;

	private String plattformName = "Facebook";

	public FacebookConnection() {
		loadProperties();
		fbClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
	}

	@Override
	public List<MessageData> fetchMessages(FacebookQuery query) {
		List<MessageData> results = new ArrayList<MessageData>();

		Connection<JsonObject> searchResult = fbClient.fetchConnection(query.constructQuery(), JsonObject.class);
		results.addAll(extractMessageData(searchResult));
		String nextPageUrl = searchResult.getNextPageUrl();

		while (nextPageUrl != null && !nextPageUrl.isEmpty()) {
			searchResult = fbClient.fetchConnectionPage(nextPageUrl, JsonObject.class);
			results.addAll(extractMessageData(searchResult));
			nextPageUrl = searchResult.getNextPageUrl();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Got " + results.size() + " messages from Facebook.");
		}

		return results;
	}

	public List<MessageData> fetchPlace(String query, String center, String distance, String limit, String until) {
		Connection<JsonObject> searchResult = fbClient.fetchConnection("search", JsonObject.class,
				Parameter.with("q", query), Parameter.with("type", "place"), Parameter.with("center", center),
				Parameter.with("distance", distance), Parameter.with("until", until), Parameter.with("limit", limit));

		return extractMessageData(searchResult);
	}

	private List<MessageData> extractMessageData(Connection<JsonObject> searchResult) {

		List<MessageData> results = new ArrayList<MessageData>();

		for (JsonObject object : searchResult.getData()) {
			if (!object.has("message")) {
				// object could be ignored if no message attribute is set.
				continue;
			}
			MessageData messageData = new MessageData(this.plattformName);

			JsonObject userData = object.getJsonObject("from");
			messageData.setFromUser(userData.getString("name"));
			messageData.setFromUserId(userData.getString("id"));
			messageData.setId(object.getString("id"));
			messageData.setMessage(object.getString("message"));
			messageData.setNetworkMessageDate(object.getString("created_time"));
			messageData.setMessageReceivedDate(new Date().toString());

			results.add(messageData);
		}

		return results;
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("conf/fb.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		MY_ACCESS_TOKEN = properties.getProperty("token");
	}

}
