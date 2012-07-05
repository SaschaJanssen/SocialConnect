package org.social.platform;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.entity.domain.Messages;
import org.social.query.TwitterQuery;

public class TwitterConnectionTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		System.setProperty("https.proxyHost", "cache.gsa.westlb.net");
		System.setProperty("https.proxyPort", "4430");

		System.setProperty("http.proxyHost", "cache.gsa.westlb.net");
		System.setProperty("http.proxyPort", "8080");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTwitterSearch() throws Exception {
		TwitterQuery query = new TwitterQuery();
		query.setDirect("Vapiano");
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
