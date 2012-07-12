package org.social.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.query.TwitterQuery;

public class TwitterQueryTest {

	CustomerNetworkKeywords cnk;

	@Before
	public void setUp() {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.QUERY.getName());
		keywords.setKeyword("Test");
		keywordListForNetwork.add(keywords);

		keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.HASH.getName());
		keywords.setKeyword("#Test");
		keywordListForNetwork.add(keywords);

		keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.MENTIONED.getName());
		keywords.setKeyword("@Test");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testConstructQuery() {
		TwitterQuery query = new TwitterQuery(cnk);
		query.setLanguage("de");
		query.setMinus("-Tree");
		query.setSince("2012-06-01");

		assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test+--Tree&lang=de&since=2012-06-01&rpp=100",
				query.constructQuery());

		query = new TwitterQuery(cnk);
		query.setLanguage("de");

		assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test&lang=de&rpp=100",
				query.constructQuery());
	}

}
