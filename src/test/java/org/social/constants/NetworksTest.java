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

}
