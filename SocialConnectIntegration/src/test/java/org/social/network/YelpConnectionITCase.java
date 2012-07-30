package org.social.network;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.network.SocialNetworkConnection;
import org.social.core.network.YelpConnection;

public class YelpConnectionITCase extends SocialITCase {

	public YelpConnectionITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testYelpSearch() {

		Customers customer = new Customers();
		customer.setCustomerId(1L);

		SocialNetworkConnection yelp = new YelpConnection(customer);

		FilteredMessageList messages = yelp.fetchAndCraftMessages();

		assertNotNull(messages);
		assertTrue(messages.size() > 200);
	}

}
