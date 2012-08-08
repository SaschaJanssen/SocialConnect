package org.social.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.query.FoursquareQuery;
import org.social.core.query.Query;

public class FoursquareQueryTest {

	CustomerNetworkKeywords cnk;

	@Before
	public void setUp() {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.PAGE.getName());
		keywords.setKeyword("4b68590cf964a52012732be3");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testConstructQuery() {
		Query query = new FoursquareQuery(cnk);
		assertEquals(
				query.getSearchUrl()
						+ "4b68590cf964a52012732be3/tips?sort=recent&v=20120808&client_secret=THA353SUWBOA15AVQ5DBDAJ3J3RMYTN0X1YSBYHKUO1MRXCB&client_id=E3ZI3TJQMTKROQ5EUOBMCXFTGNGK5E3NWDCNO3Q5KQIDEOUW",
				query.constructQuery());
	}
}
