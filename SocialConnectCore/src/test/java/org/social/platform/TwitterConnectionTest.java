package org.social.platform;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialTest;
import org.social.core.entity.domain.Messages;
import org.social.core.platform.TwitterConnection;
import org.social.core.query.TwitterQuery;

public class TwitterConnectionTest extends SocialTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public TwitterConnectionTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTwitterSearch() throws Exception {
		TwitterQuery query = new TwitterQuery(1L);
		query.setQuery("Vapiano");
		query.setLanguage("de");
		query.setSince("2012-06-01");

		TwitterConnection con = new TwitterConnection();
		List<Messages> result = con.fetchMessages(query);

		assertNotNull(result);

		if (logger.isDebugEnabled()) {
			logger.debug("Foud: " + result.size() + " results.");
			for (Messages messageData : result) {
				logger.debug(messageData.toString());
			}
		}
	}

}
