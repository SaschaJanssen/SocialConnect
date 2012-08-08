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
	public void testYELP() throws Exception {
		assertEquals("YELP", Networks.YELP.getName());
	}

	@Test
	public void testQYPE() throws Exception {
		assertEquals("QYPE", Networks.QYPE.getName());
	}

	@Test
	public void testOPENTABLE() throws Exception {
		assertEquals("OPENTABLE", Networks.OPENTABLE.getName());
	}

	@Test
	public void testTRIPADVISOR() throws Exception {
		assertEquals("TRIPADVISOR", Networks.TRIPADVISOR.getName());
	}

	@Test
	public void testZAGAT() throws Exception {
		assertEquals("ZAGAT", Networks.ZAGAT.getName());
	}

	@Test
	public void testFOURSQUARE() throws Exception {
		assertEquals("FOURSQUARE", Networks.FOURSQUARE.getName());
	}

	@Test
	public void test_isNetwork() throws Exception {
		assertFalse(Networks.FACEBOOK.isNetwork("TWITTER"));
		assertTrue(Networks.FACEBOOK.isNetwork("FACEBOOK"));

		assertTrue(Networks.TWITTER.isNetwork("TWITTER"));
		assertFalse(Networks.TWITTER.isNetwork("FACEBOOK"));
	}
}
