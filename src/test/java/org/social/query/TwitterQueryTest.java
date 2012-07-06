package org.social.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TwitterQueryTest {

	@Test
	public void testConstructQuery() {
		TwitterQuery query = new TwitterQuery(1L);
		query.setQuery("Test");
		query.setHash("#Test");
		query.setLanguage("de");
		query.setMentioned("@Test");
		query.setMinus("-Tree");
		query.setSince("2012-06-01");

		assertEquals(new Long(1), query.getCustomerId());
		assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test+--Tree&lang=de&since=2012-06-01&rpp=100",
				query.constructQuery());
	}

}
