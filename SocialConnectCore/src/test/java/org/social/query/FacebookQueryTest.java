package org.social.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.query.FacebookQuery;

public class FacebookQueryTest {

	CustomerNetworkKeywords cnk;

	@Before
	public void setUp() {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.QUERY.getName());
		keywords.setKeyword("Test");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testConstructQuery() {
		FacebookQuery query = new FacebookQuery(cnk);
		query.setSince("yesterday");
		query.setType("post");

		assertEquals(query.getSearchUrl() + "?q=Test&type=post&since=yesterday&limit=1500", query.constructQuery());

		query = new FacebookQuery(cnk);
		query.setType("post");
		assertEquals(query.getSearchUrl() + "?q=Test&type=post&limit=1500", query.constructQuery());
	}

}
