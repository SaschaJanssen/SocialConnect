package org.social.network;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.constants.KeywordType;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Keywords;
import org.social.core.network.connection.FoursquareConnection;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.FoursquareQuery;
import org.social.core.query.Query;

public class FoursquareConnectionITCase extends SocialITCase {

	private CustomerNetworkKeywords cnk;

	public FoursquareConnectionITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(3L);
		keywords.setKeywordTypeId(KeywordType.PAGE.getName());
		keywords.setKeyword("4b68590cf964a52012732be3");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testFoursquareSearch() {
		Query query = new FoursquareQuery(cnk);

		SocialNetworkConnection con = new FoursquareConnection();
		List<JSONObject> result = con.getRemoteData(query);

		assertNotNull(result);
		assertTrue(result.size() >= 5);
	}
}
