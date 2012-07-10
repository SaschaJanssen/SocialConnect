package org.social.constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.social.core.constants.Networks;

public class NetworksTest {

	@Test
	public void testFacebook() {
		assertEquals("FACEBOOK", Networks.FACEBOOK.getName());
	}

	@Test
	public void testTwitter() throws Exception {
		assertEquals("TWITTER", Networks.TWITTER.getName());
	}

	@Test
	public void test_isNetwork() throws Exception {
		assertFalse(Networks.FACEBOOK.isNetwork("TWITTER"));
		assertTrue(Networks.FACEBOOK.isNetwork("FACEBOOK"));

		assertTrue(Networks.TWITTER.isNetwork("TWITTER"));
		assertFalse(Networks.TWITTER.isNetwork("FACEBOOK"));
	}

	@Test
	public void test_convertTimeTwitter() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String twitterTime = Networks.TWITTER.convertTimestampToNetworkTime(timestamp);
		assertEquals("2012-07-10", twitterTime);
	}

	@Test
	public void test_convertTimeFb() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String fbTime = Networks.FACEBOOK.convertTimestampToNetworkTime(timestamp);

		Long unixTs = timestamp.getTime() / 1000L;
		assertEquals(unixTs.toString(), fbTime);
	}

}
