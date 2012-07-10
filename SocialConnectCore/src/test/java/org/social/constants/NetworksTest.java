package org.social.constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
