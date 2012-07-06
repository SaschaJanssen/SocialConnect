package org.social.query;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FacebookQueryTest {

	FacebookQuery query;

	@Before
	public void setUp() throws Exception {
		query = new FacebookQuery(1L);
	}


	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructQuery() {
		query.setQuery("Test");
		query.setSince("yesterday");
		query.setType("post");

		assertEquals(query.getSearchUrl() + "?q=Test&type=post&since=yesterday&limit=1500", query.constructQuery());
	}

}
