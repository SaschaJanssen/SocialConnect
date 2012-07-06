package org.social.consumer;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.entity.domain.Customers;
import org.social.entity.domain.Messages;

public class SocialDataConsumerTest {

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
	public void testCreateDataConsumer() {
		List<Customers> customers = new ArrayList<Customers>();

		SocialDataConsumer consumer = new SocialDataConsumer(customers);
		List<Messages> messageDataList = consumer.consumeData();

		assertNotNull(messageDataList);

	}

}
