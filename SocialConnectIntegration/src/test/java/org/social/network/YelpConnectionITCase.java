package org.social.network;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.YelpKraken;

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

		SocialNetworkKraken yelp = new YelpKraken(customer, new KeywordDAO());

		FilteredMessageList messages = yelp.fetchAndCraftMessages();

		assertNotNull(messages);
		assertTrue(messages.size() > 200);
	}

}
