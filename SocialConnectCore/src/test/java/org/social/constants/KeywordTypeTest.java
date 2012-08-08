package org.social.constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.social.core.constants.KeywordType;

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
	public void testPage() {
		assertEquals("PAGE", KeywordType.PAGE.getName());
	}

	@Test
	public void testIsKeywordType() throws Exception {
		assertTrue(KeywordType.QUERY.isKeywordType("QUERY"));
		assertTrue(KeywordType.HASH.isKeywordType("HASH"));
		assertTrue(KeywordType.MENTIONED.isKeywordType("MENTIONED"));
		assertTrue(KeywordType.PAGE.isKeywordType("PAGE"));

		assertFalse(KeywordType.MENTIONED.isKeywordType("HASH"));
	}

}
