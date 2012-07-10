package org.social.consumer;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Customers;
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
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		Customers customer = new Customers();
		customer.setLastNetworkdAccess(timestamp);
		customer.setCustomerId(1L);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(customer);

		SocialDataConsumer consumer = new SocialDataConsumer();
		List<Messages> messageDataList = consumer.consumeData(1L, cnk);

		assertNotNull(messageDataList);

	}

}
