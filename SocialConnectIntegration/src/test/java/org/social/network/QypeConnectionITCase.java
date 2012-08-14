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
import org.social.core.network.connection.QypeConnection;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.QypeQuery;

public class QypeConnectionITCase extends SocialITCase {

	private CustomerNetworkKeywords cnk;

	public QypeConnectionITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.PAGE.getName());
		keywords.setKeyword("139975");
		keywordListForNetwork.add(keywords);

		cnk = new CustomerNetworkKeywords(keywordListForNetwork);
	}

	@Test
	public void testQypeSearch() {
		QypeQuery query = new QypeQuery(cnk);
		query.setLanguage("en");
		query.setSince("2012-01-01 18:48:05.123456");

		SocialNetworkConnection con = new QypeConnection();
		List<JSONObject> result = con.getRemoteData(query);

		assertNotNull(result);
		assertTrue(result.size() >= 1);
	}
}
