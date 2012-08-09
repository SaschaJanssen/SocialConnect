package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;

public class CustomerNetworkKeywordsTest {

    private CustomerNetworkKeywords unk = null;

    @Before
    public void setUp() {

        List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
        Keywords keywords = new Keywords();
        keywords.setCustomerId(1L);
        keywords.setKeywordTypeId(KeywordType.HASH.getName());
        keywords.setKeyword("#Vapiano");
        keywordListForNetwork.add(keywords);

        keywords = new Keywords();
        keywords.setCustomerId(1L);
        keywords.setKeywordTypeId(KeywordType.QUERY.getName());
        keywords.setKeyword("Vapiano");
        keywordListForNetwork.add(keywords);

        unk = new CustomerNetworkKeywords(keywordListForNetwork);
    }

    @Test
    public void testGetTwitterHash() {
        String hash = unk.getHashForNetwork();
        assertEquals("#Vapiano", hash);
    }

    @Test
    public void testGetTwitterQuery() {
        String query = unk.getQueryForNetwork();
        assertEquals("Vapiano", query);
    }

    @Test
    public void testGetTwitterMentioned() {
        String mentioned = unk.getMentionedForNetwork();
        assertEquals("", mentioned);
    }
}
