package org.social.core.network.connection;

import java.util.ArrayList;
import java.util.List;

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
import org.social.core.query.Query;
import org.social.core.util.UtilProperties;

public class TwitterConnection implements SocialNetworkConnection{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String CONSUMER_KEY = "consumerKey";
	private String CONSUMER_SECRET = "consumerSecret";
	private String ACCESS_TOKEN = "accessToken";
	private String ACESS_TOEN_SECRET = "accessTokenSecret";

	private String consumerKey = "";
	private String consumerSecret = "";
	private String accessTokenPub = "";
	private String accessTokenSecret = "";

	private OAuthService service = null;
	private Token accessToken;

	public TwitterConnection() {
		loadProperties();

		ServiceBuilder builder = new ServiceBuilder();
		builder.provider(TwitterApi.SSL.class);
		builder.apiKey(consumerKey);
		builder.apiSecret(consumerSecret);

		service = builder.build();

		accessToken = new Token(accessTokenPub, accessTokenSecret);
	}

	@Override
	public List<JSONObject> getRemoteData(Query query) {
		List<JSONObject> resultMessages = new ArrayList<JSONObject>();
		String constructedQuery = query.constructQuery();

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
				resultMessages.add(json);
			}

			if (json.containsKey("next_page")) {
				constructedQuery = query.getSearchUrl() + json.getString("next_page");
			} else {
				break;
			}
		}
		return resultMessages;
	}

	private void loadProperties() {
		this.consumerKey = UtilProperties.getPropertyValue("conf/twitter.properties", CONSUMER_KEY);
		this.consumerSecret = UtilProperties.getPropertyValue("conf/twitter.properties", CONSUMER_SECRET);
		this.accessTokenPub = UtilProperties.getPropertyValue("conf/twitter.properties", ACCESS_TOKEN);
		this.accessTokenSecret = UtilProperties.getPropertyValue("conf/twitter.properties", ACESS_TOEN_SECRET);
	}

}
