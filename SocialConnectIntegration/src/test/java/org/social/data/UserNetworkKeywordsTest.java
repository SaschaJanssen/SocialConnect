package org.social.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialTest;
import org.social.constants.Networks;

public class UserNetworkKeywordsTest extends SocialTest {

	private CustomerNetworkKeywords unk = null;

	public UserNetworkKeywordsTest() {
		super();
	}

	@Before
	public void setUp() {
		unk = new CustomerNetworkKeywords(1L);
	}

	@Test
	public void testGetTwitterHash() {
		String hash = unk.getHashForNetwork(Networks.TWITTER);
		assertEquals("#Vapiano", hash);
	}
	@Test
	public void testGetTwitterQuery() {
		String query = unk.getQueryForNetwork(Networks.TWITTER);
		assertEquals("Vapiano", query);
	}
	@Test
	public void testGetTwitterMentioned() {
		String hash = unk.getMentionedForNetwork(Networks.TWITTER);
		assertEquals("", hash);
	}

}
