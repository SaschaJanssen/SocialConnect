package org.social.platform;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialTest;
import org.social.entity.domain.Messages;
import org.social.query.FacebookQuery;

public class FacebookConnectionTest extends SocialTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public FacebookConnectionTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFacebookSearch() {
		FacebookQuery query = new FacebookQuery(1L);
		query.setQuery("Vapiano");
		query.setSince("yesterday");
		query.setType("post");

		FacebookConnection con = new FacebookConnection();
		List<Messages> result = con.fetchMessages(query);

		assertNotNull(result);
		assertTrue(!result.isEmpty());

		if (logger.isDebugEnabled()) {
			logger.debug("Foud: " + result.size() + " results.");
			for (Messages messageData : result) {
				logger.debug(messageData.toString());
			}
		}
	}

}
