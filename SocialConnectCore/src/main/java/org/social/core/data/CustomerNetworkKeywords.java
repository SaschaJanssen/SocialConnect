package org.social.core.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.social.core.constants.KeywordType;
import org.social.core.entity.domain.Keywords;

public class CustomerNetworkKeywords {

	private final Map<String, String> networkKeywords;

	public CustomerNetworkKeywords(List<Keywords> keywordListForNetwork) {
		networkKeywords = mapKeywords(keywordListForNetwork);

	}
	public String getHashForNetwork() {
		String hash = "";

		if (networkKeywords.containsKey(KeywordType.HASH.getName())) {
			hash = networkKeywords.get(KeywordType.HASH.getName());
		}

		return hash;
	}

	public String getMentionedForNetwork() {
		String mentioned = "";

		if (networkKeywords.containsKey(KeywordType.MENTIONED.getName())) {
			mentioned = networkKeywords.get(KeywordType.MENTIONED.getName());
		}

		return mentioned;
	}

	public String getQueryForNetwork() {
		String query = "";

		if (networkKeywords.containsKey(KeywordType.QUERY.getName())) {
			query = networkKeywords.get(KeywordType.QUERY.getName());
		}

		return query;
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
