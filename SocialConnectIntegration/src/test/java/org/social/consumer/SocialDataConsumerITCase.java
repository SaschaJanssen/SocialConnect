package org.social.consumer;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Messages;

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
		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(1L);

		SocialDataConsumer consumer = new SocialDataConsumer();
		List<Messages> messageDataList = consumer.consumeData(1L, cnk);

		assertNotNull(messageDataList);

	}

}
