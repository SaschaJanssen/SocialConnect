package org.social.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.query.QypeQuery;

public class QypeQueryTest {

    CustomerNetworkKeywords cnk;

    @Before
    public void setUp() {
        List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
        Keywords keywords = new Keywords();
        keywords.setCustomerId(1L);
        keywords.setKeywordTypeId(KeywordType.PAGE.getName());
        keywords.setKeyword("139975");
        keywordListForNetwork.add(keywords);

        cnk = new CustomerNetworkKeywords(keywordListForNetwork);
    }

    @Test
    public void testConstructQuery() {
        QypeQuery query = new QypeQuery(cnk);
        query.setSince("2011-03-01T12:20:06+01:00");
        query.setLanguage("en");

        assertEquals(query.getSearchUrl()
                + "139975/reviews/en.json?order=date_created&consumer_key=1f9XapFtM974aajgXnTQ", query.constructQuery());
    }

}
