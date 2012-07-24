package org.social.network;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
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
	public void test() {

		Customers customer = new Customers();
		customer.setCustomerId(1L);

		SocialNetworkConnection yelp = new YelpConnection(customer);

		List<Messages> messages = yelp.fetchMessages();

		for (Messages message : messages) {
			System.out.println(message);
		}
	}

}