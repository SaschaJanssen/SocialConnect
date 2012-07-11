package org.social.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialITCase;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.network.TwitterConnection;

public class TwitterConnectionITCase extends SocialITCase {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public TwitterConnectionITCase() {
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
		Customers customer = new Customers();
		customer.setCustomerId(1L);

		TwitterConnection con = new TwitterConnection(customer);
		List<Messages> result = con.fetchMessages();

		assertNotNull(result);

		if (logger.isDebugEnabled()) {
			logger.debug("Foud: " + result.size() + " results.");
			for (Messages messageData : result) {
				logger.debug(messageData.toString());
			}
		}
	}

	@Test
	public void testGetCustNetworkKeys() throws Exception {
		Customers customer = new Customers();
		customer.setCustomerId(1L);

		TwitterConnection con = new TwitterConnection(customer);
		CustomerNetworkKeywords cnk = con.getCustomerNetworkKeywords();

		assertNotNull(cnk);
		assertEquals("Vapiano", cnk.getQueryForNetwork());
	}

}