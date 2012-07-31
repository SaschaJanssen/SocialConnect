package org.social.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.FacebookKraken;
import org.social.core.network.connection.FacebookConnection;

public class FacebookConnectionITCase extends SocialITCase {

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
		Customers customer = new Customers();
		customer.setCustomerId(1L);

		FacebookKraken con = new FacebookKraken(customer, new KeywordDAO(), new FacebookConnection());
		FilteredMessageList result = con.fetchAndCraftMessages();

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	@Test
	public void testFB_Connect() throws Exception {
		Customers customer = new Customers();
		customer.setCustomerId(1L);

		FacebookKraken con = new FacebookKraken(customer, new KeywordDAO(), new FacebookConnection());
		CustomerNetworkKeywords cnk = con.getCustomerNetworkKeywords();

		assertNotNull(cnk);
		assertEquals("Vapiano", cnk.getQueryForNetwork());
	}

}
