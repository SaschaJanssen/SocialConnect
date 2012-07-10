package org.social.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FacebookQueryTest {

	@Test
	public void testConstructQuery() {
		FacebookQuery query = new FacebookQuery(1L);
		query.setQuery("Test");
		query.setSince("yesterday");
		query.setType("post");

		assertEquals(new Long(1), query.getCustomerId());
		assertEquals(query.getSearchUrl() + "?q=Test&type=post&since=yesterday&limit=1500", query.constructQuery());
	}

}
