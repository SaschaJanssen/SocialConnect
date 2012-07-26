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
import org.social.core.query.OpenTableQuery;

public class OpenTableQueryTest {

	CustomerNetworkKeywords cnk;

	@Before
	public void setUp() {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.PAGE.getName());
		keywords.setKeyword("/biz/restaurant");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testConstructQuery() throws Exception {
		OpenTableQuery yq = new OpenTableQuery(cnk);

		assertEquals("/biz/restaurant/reviews.htm", yq.constructQuery());
		assertNotNull(yq.getSearchUrl());
	}

}
