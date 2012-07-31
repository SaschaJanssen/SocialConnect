package org.social.core.network;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.FacebookQuery;
import org.social.core.util.UtilDateTime;

public class FacebookKraken extends SocialNetworkKraken {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SocialNetworkConnection connection;

	public FacebookKraken(Customers customer, KeywordDAO keywordDao, SocialNetworkConnection fbConnection) {
		super(customer, keywordDao);
		connection = fbConnection;
		getCustomersKeywords(Networks.FACEBOOK.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch posts from Facebook for customer: " + super.customer.getCustomerId());
		}

		FacebookQuery query = buildQueryFromKeywords();

		List<JSONObject> searchResult = connection.getRemoteData(query);
		List<Messages> resultMessages = extractMessageData(searchResult);

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

	private List<Messages> extractMessageData(List<JSONObject> searchResult) {

		List<Messages> results = new ArrayList<Messages>();

		for (JSONObject object : searchResult) {
			if (!object.has("message")) {
				// object could be ignored if no message attribute is set.
				continue;
			}
			Messages messageData = new Messages(Networks.FACEBOOK.getName());

			messageData.setCustomerId(super.customer.getCustomerId());

			JSONObject userData = object.getJSONObject("from");
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
}
