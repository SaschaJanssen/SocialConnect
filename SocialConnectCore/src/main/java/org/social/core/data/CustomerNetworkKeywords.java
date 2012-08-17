package org.social.core.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.social.core.constants.KeywordType;
import org.social.core.entity.domain.Keywords;

public class CustomerNetworkKeywords {

    private final Map<KeywordType, String> networkKeywords;

    public CustomerNetworkKeywords(List<Keywords> keywordListForNetwork) {
        networkKeywords = mapKeywords(keywordListForNetwork);

    }

    public String getHashForNetwork() {
        String hash = "";

        if (networkKeywords.containsKey(KeywordType.HASH)) {
            hash = networkKeywords.get(KeywordType.HASH);
        }

        return hash;
    }

    public String getMentionedForNetwork() {
        String mentioned = "";

        if (networkKeywords.containsKey(KeywordType.MENTIONED)) {
            mentioned = networkKeywords.get(KeywordType.MENTIONED);
        }

        return mentioned;
    }

    public String getQueryForNetwork() {
        String query = "";

        if (networkKeywords.containsKey(KeywordType.QUERY)) {
            query = networkKeywords.get(KeywordType.QUERY);
        }

        return query;
    }

    private Map<KeywordType, String> mapKeywords(List<Keywords> keywordsForCustomer) {
        Map<KeywordType, String> mappedKeywords = new HashMap<KeywordType, String>();

        for (Keywords keyword : keywordsForCustomer) {
            String keywordType = keyword.getKeywordTypeId();

            if (KeywordType.HASH.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.HASH, keyword.getKeyword());
            } else if (KeywordType.MENTIONED.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.MENTIONED, keyword.getKeyword());
            } else if (KeywordType.QUERY.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.QUERY, keyword.getKeyword());
            } else if (KeywordType.PAGE.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.PAGE, keyword.getKeyword());
            }
        }

        return mappedKeywords;
    }

    public String getPage() {
        String page = "";

        if (networkKeywords.containsKey(KeywordType.PAGE)) {
            page = networkKeywords.get(KeywordType.PAGE);
        }

        return page;
    }

}
