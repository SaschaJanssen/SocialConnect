package org.social.constants;

import static org.junit.Assert.*;

import org.junit.Test;

public class NetworksTest {

	@Test
	public void testFacebook() {
		assertEquals("FACEBOOK", Networks.FACEBOOK.toString());
	}

	@Test
	public void testTwitter() throws Exception {
		assertEquals("TWITTER", Networks.TWITTER.toString());
	}

	@Test
	public void test_isNetwork() throws Exception {
		assertFalse(Networks.FACEBOOK.isNetwork("TWITTER"));
		assertTrue(Networks.FACEBOOK.isNetwork("FACEBOOK"));

		assertTrue(Networks.TWITTER.isNetwork("TWITTER"));
		assertFalse(Networks.TWITTER.isNetwork("FACEBOOK"));
	}

}
