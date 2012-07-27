package org.social.consumer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;

public class SocialDataConsumerITCase extends SocialITCase {

	public SocialDataConsumerITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateDataConsumer() {

		Customers customer = new Customers();
		customer.setCustomerId(1L);

		SocialDataConsumer consumer = new SocialDataConsumer();
		FilteredMessageList messageDataList = consumer.consumeData(customer);

		assertNotNull(messageDataList);
		assertTrue(messageDataList.size() > 1);
	}

}
