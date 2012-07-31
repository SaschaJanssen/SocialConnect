package org.social.core.network;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.TwitterQuery;
import org.social.core.util.UtilDateTime;

public class TwitterKraken extends SocialNetworkKraken {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final SocialNetworkConnection connection;

	public TwitterKraken(Customers customer, KeywordDAO keywordDao, SocialNetworkConnection twitterConnection) {
		super(customer, keywordDao);
		this.connection = twitterConnection;
		this.customer = customer;

		getCustomersKeywords(Networks.TWITTER.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch tweets from Twitter for customer: " + this.customer.getCustomerId());
		}

		TwitterQuery query = buildQueryFromKeywords();

		List<JSONObject> resultObjects = connection.getRemoteData(query);
		List<Messages> resultList = new ArrayList<Messages>();
		for (JSONObject jo : resultObjects) {
			resultList.addAll(extractMessageData(jo));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Got " + resultObjects.size() + " tweets from Twitter.");
		}

		FilteredMessageList filteredResultMessages = reliabilityAndSentimentMessages(resultList);

		return filteredResultMessages;
	}

	private TwitterQuery buildQueryFromKeywords() {
		TwitterQuery twitterQuery = new TwitterQuery(super.customerNetworkKeywords);

		String since = UtilDateTime.connvertTimestampToTwitterTime(this.customer.getLastNetworkdAccess());
		twitterQuery.setSince(since);
		twitterQuery.setLanguage("en");

		return twitterQuery;
	}

	private List<Messages> extractMessageData(JSONObject json) {
		List<Messages> resultList = new ArrayList<Messages>();
		JSONArray resultArray = json.getJSONArray("results");

		for (Object object : resultArray) {
			Messages messageData = new Messages(Networks.TWITTER.getName());

			messageData.setCustomerId(customer.getCustomerId());

			JSONObject jsonObj = (JSONObject) object;

			messageData.setNetworkUser(jsonObj.getString("from_user"));
			messageData.setNetworkUserId(jsonObj.getString("from_user_id"));
			messageData.setLanguage(jsonObj.getString("iso_language_code"));

			JSONObject geo = jsonObj.getJSONObject("geo");
			if (!geo.isNullObject() && geo.has("coordinates")) {
				messageData.setGeoLocation(geo.getString("coordinates"));
			}
			messageData.setMessage(jsonObj.getString("text"));

			String createdAt = jsonObj.getString("created_at");
			messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(createdAt));
			messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());
			resultList.add(messageData);
		}

		return resultList;
	}

}
