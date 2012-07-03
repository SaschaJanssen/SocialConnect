package org.social.query;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterQueryTest {

	TwitterQuery query;

	@Before
	public void setUp() throws Exception {
		query = new TwitterQuery();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructQuery() {
		query.setDirect("Test");
		query.setHash("#Test");
		query.setLanguage("de");
		query.setMentioned("@Test");
		query.setMinus("-Tree");
		query.setSince("2012-06-01");

		assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test+--Tree&lang=de&since=2012-06-01&rpp=100", query.constructQuery());
	}

}
