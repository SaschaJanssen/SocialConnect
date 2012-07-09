package org.social.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.social.constants.KeywordType;
import org.social.constants.Networks;
import org.social.entity.domain.Keywords;
import org.social.entity.helper.KeywordHelper;

public class CustomerNetworkKeywords {

	private final Map<String, String> facebookKeywords;
	private final Map<String, String> twitterKeywords;

	public CustomerNetworkKeywords(Long customerId) {
		KeywordHelper helper = new KeywordHelper();

		// Get all Facebook Keywords
		List<Keywords> keywords = helper.getMappedKeywordByCustomerAndNetwork(customerId, Networks.FACEBOOK.getName());
		facebookKeywords = mapKeywords(keywords);

		// Get all Twitter Keywords
		keywords = helper.getMappedKeywordByCustomerAndNetwork(customerId, Networks.TWITTER.getName());
		twitterKeywords = mapKeywords(keywords);

	}

	public String getHashForNetwork(Networks network) {
		String hash = null;

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
		String query = null;

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
