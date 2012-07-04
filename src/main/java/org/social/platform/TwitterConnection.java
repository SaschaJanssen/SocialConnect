package org.social.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.data.MessageData;
import org.social.query.TwitterQuery;

public class TwitterConnection implements SocialNetworkConnection<TwitterQuery>{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String CONSUMER_KEY = "consumerKey";
	private String CONSUMER_SECRET = "consumerSecret";
	private String ACCESS_TOKEN = "accessToken";
	private String ACESS_TOEN_SECRET = "accessTokenSecret";

	private String consumerKey = "";
	private String consumerSecret = "";
	private String accessTokenPub = "";
	private String accessTokenSecret = "";

	private String plattformName = "Twitter";

	private OAuthService service = null;
	private Token accessToken;

	public TwitterConnection() {
		try {
			loadProperties();
		} catch (IOException e) {
			logger.error("Twitter connection will be ignored, properties couldn't read. ", e);
			return;
		}

		ServiceBuilder builder = new ServiceBuilder();
		builder.provider(TwitterApi.SSL.class);
		builder.apiKey(consumerKey);
		builder.apiSecret(consumerSecret);

		service = builder.build();

		accessToken = new Token(accessTokenPub, accessTokenSecret);
	}

	public List<MessageData> fetchMessages(TwitterQuery query) {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch tweets from Twitter.");
		}

		String constructedQuery = query.constructQuery();

		List<MessageData> resultList = new ArrayList<MessageData>();

		while (true) {
			if (logger.isDebugEnabled()) {
				logger.debug("Twitter GET Request: " + constructedQuery);
			}
			OAuthRequest request = new OAuthRequest(Verb.GET, constructedQuery);
			service.signRequest(accessToken, request);
			Response response = request.send();

			String responseBody = response.getBody();
			if (responseBody.isEmpty()) {
				break;
			}

			JSONObject json = (JSONObject) JSONSerializer.toJSON(response.getBody());

			if (json.containsKey("results")) {
				resultList.addAll(extractMessageData(json));
			}

			if (json.containsKey("next_page")) {
				constructedQuery = query.getSearchUrl() + json.getString("next_page");
			} else {
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Got "+ resultList.size() + " tweets from Twitter.");
		}
		return resultList;
	}

	private List<MessageData> extractMessageData(JSONObject json) {
		List<MessageData> resultList = new ArrayList<MessageData>();
		JSONArray resultArray = json.getJSONArray("results");

		for (Object object : resultArray) {
			MessageData messageData = new MessageData(this.plattformName);

			JSONObject jsonObj = (JSONObject) object;
			messageData.setId(jsonObj.getString("id"));
			messageData.setFromUser(jsonObj.getString("from_user"));
			messageData.setLanguage(jsonObj.getString("iso_language_code"));
			messageData.setGeoLocation(jsonObj.getString("geo"));
			messageData.setMessage(jsonObj.getString("text"));
			messageData.setNetworkMessageDate(jsonObj.getString("created_at"));
			messageData.setMessageReceivedDate(new Date().toString());
			resultList.add(messageData);
		}

		return resultList;
	}

	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("conf/twitter.properties")));

		this.consumerKey = properties.getProperty(CONSUMER_KEY);
		this.consumerSecret = properties.getProperty(CONSUMER_SECRET);
		this.accessTokenPub = properties.getProperty(ACCESS_TOKEN);
		this.accessTokenSecret = properties.getProperty(ACESS_TOEN_SECRET);
	}
}
