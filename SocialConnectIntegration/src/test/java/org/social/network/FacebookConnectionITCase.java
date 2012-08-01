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
import org.social.core.network.connection.FacebookConnection;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.FacebookQuery;
import org.social.core.query.Query;

public class FacebookConnectionITCase extends SocialITCase {

	private CustomerNetworkKeywords cnk;

	public FacebookConnectionITCase() {
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
	public void testFacebookSearch() {
		Query query = new FacebookQuery(cnk);

		SocialNetworkConnection con = new FacebookConnection();
		List<JSONObject> result = con.getRemoteData(query);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}
}
