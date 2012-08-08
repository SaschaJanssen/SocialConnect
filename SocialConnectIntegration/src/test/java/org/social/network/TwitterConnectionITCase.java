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
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.network.connection.TwitterConnection;
import org.social.core.query.TwitterQuery;

public class TwitterConnectionITCase extends SocialITCase {

	private CustomerNetworkKeywords cnk;

	public TwitterConnectionITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.QUERY.getName());
		keywords.setKeyword("Vapiano");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testTwitterSearch() throws Exception {
		TwitterQuery query = new TwitterQuery(cnk);
		query.setLanguage("en");

		SocialNetworkConnection con = new TwitterConnection();
		List<JSONObject> result = con.getRemoteData(query);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

}
