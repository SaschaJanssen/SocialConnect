package org.social.constants;

import static org.junit.Assert.*;

import org.junit.Test;

public class KeywordTypeTest {

	@Test
	public void testQuery() {
		assertEquals("QUERY", KeywordType.QUERY.getName());
	}

	@Test
	public void testHash() {
		assertEquals("HASH", KeywordType.HASH.getName());
	}

	@Test
	public void testMentioned() {
		assertEquals("MENTIONED", KeywordType.MENTIONED.getName());
	}

	@Test
	public void testIsKeywordType() throws Exception {
		assertTrue(KeywordType.QUERY.isKeywordType("QUERY"));
		assertTrue(KeywordType.HASH.isKeywordType("HASH"));
		assertTrue(KeywordType.MENTIONED.isKeywordType("MENTIONED"));

		assertFalse(KeywordType.MENTIONED.isKeywordType("HASH"));
	}

}
