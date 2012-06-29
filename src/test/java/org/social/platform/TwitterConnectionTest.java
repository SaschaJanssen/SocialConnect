package org.social.platform;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.data.MessageData;

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
		TwitterConnection con = new TwitterConnection();
		List<MessageData> result = con.fetchTweets("Vapiano", "de", "2012-06-01");

		assertNotNull(result);

		if (logger.isDebugEnabled()) {
			logger.debug("Foud: " + result.size() + " results.");
			for (MessageData messageData : result) {
				logger.debug(messageData.toJson().toString());
			}
		}
	}

}
