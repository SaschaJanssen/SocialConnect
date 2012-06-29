package org.social.consumer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.data.MessageData;

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
		SocialDataConsumer consumer = new SocialDataConsumer();
		List<MessageData> messageDataList = consumer.consumeData();

		assertNotNull(messageDataList);

	}

}
