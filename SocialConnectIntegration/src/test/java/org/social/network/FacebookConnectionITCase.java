package org.social.network;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialITCase;
import org.social.core.entity.domain.Messages;
import org.social.core.network.FacebookConnection;
import org.social.core.query.FacebookQuery;

public class FacebookConnectionITCase extends SocialITCase {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public FacebookConnectionITCase() {
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
