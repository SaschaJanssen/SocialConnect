package org.social.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.query.Query;
import org.social.core.query.ZagatQuery;

public class ZagatQueryTest {

    CustomerNetworkKeywords cnk;

    @Before
    public void setUp() {
        List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
        Keywords keywords = new Keywords();
        keywords.setCustomerId(1L);
        keywords.setKeywordTypeId(KeywordType.PAGE.getName());
        keywords.setKeyword("/r/n/five-guys-queens-3");
        keywordListForNetwork.add(keywords);

        cnk = new CustomerNetworkKeywords(keywordListForNetwork);
    }

    @Test
    public void testConstructQuery() throws Exception {
        Query yq = new ZagatQuery(cnk);

        assertEquals("/r/n/five-guys-queens-3/reviews", yq.constructQuery());
        assertNotNull(yq.getSearchUrl());
    }

}
