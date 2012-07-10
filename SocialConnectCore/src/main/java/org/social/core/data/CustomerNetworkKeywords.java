package org.social.core.data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.social.core.constants.KeywordType;
import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.helper.KeywordDAO;

public class CustomerNetworkKeywords {

	private final Map<String, String> facebookKeywords;
	private final Map<String, String> twitterKeywords;

	private final Timestamp lastNetworkAccess;

	public CustomerNetworkKeywords(Customers customer) {
		Long customerId = customer.getCustomerId();
		lastNetworkAccess = customer.getLastNetworkdAccess();

		KeywordDAO helper = new KeywordDAO();
		// TODO move DB access somewhere else

		// Get all Facebook Keywords
		List<Keywords> keywords = helper.getMappedKeywordByCustomerAndNetwork(customerId, Networks.FACEBOOK.getName());
		facebookKeywords = mapKeywords(keywords);

		// Get all Twitter Keywords
		keywords = helper.getMappedKeywordByCustomerAndNetwork(customerId, Networks.TWITTER.getName());
		twitterKeywords = mapKeywords(keywords);

	}

	public String getLastAccessForNetwork(Networks network) {
		return network.convertTimestampToNetworkTime(lastNetworkAccess);
	}

	public String getHashForNetwork(Networks network) {
		String hash = "";

		Map<String, String> networkKeywordMap = chooseNetworkMap(network);
		if (networkKeywordMap.containsKey(KeywordType.HASH.getName())) {
			hash = networkKeywordMap.get(KeywordType.HASH.getName());
		}

		return hash;
	}

	public String getMentionedForNetwork(Networks network) {
		String mentioned = "";

		Map<String, String> networkKeywordMap = chooseNetworkMap(network);
		if (networkKeywordMap.containsKey(KeywordType.MENTIONED.getName())) {
			mentioned = networkKeywordMap.get(KeywordType.MENTIONED.getName());
		}

		return mentioned;
	}

	public String getQueryForNetwork(Networks network) {
		String query = "";

		Map<String, String> networkKeywordMap = chooseNetworkMap(network);
		if (networkKeywordMap.containsKey(KeywordType.QUERY.getName())) {
			query = networkKeywordMap.get(KeywordType.QUERY.getName());
		}

		return query;
	}

	private Map<String, String> chooseNetworkMap(Networks network) {
		Map<String, String> networkKeywords = null;

		switch (network) {
		case TWITTER:
			networkKeywords = this.twitterKeywords;
			break;
		case FACEBOOK:
			networkKeywords = this.facebookKeywords;
			break;
		default:
			break;
		}

		return networkKeywords;

	}

	private Map<String, String> mapKeywords(List<Keywords> keywordsForCustomer) {
		Map<String, String> mappedKeywords = new HashMap<String, String>();

		for (Keywords keyword : keywordsForCustomer) {
			String keywordType = keyword.getKeywordTypeId();

			if (KeywordType.HASH.isKeywordType(keywordType)) {
				mappedKeywords.put(KeywordType.HASH.getName(), keyword.getKeyword());
			} else if (KeywordType.MENTIONED.isKeywordType(keywordType)) {
				mappedKeywords.put(KeywordType.MENTIONED.getName(), keyword.getKeyword());
			} else if (KeywordType.QUERY.isKeywordType(keywordType)) {
				mappedKeywords.put(KeywordType.QUERY.getName(), keyword.getKeyword());
			}
		}

		return mappedKeywords;
	}

}
