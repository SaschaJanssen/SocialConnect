package org.social.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.social.core.constants.Classification;

public class ClassificationStateTest {

	@Test
	public void testCraftedGood() {
		assertEquals("RELIABLE", Classification.RELIABLE.getName());
	}

	@Test
	public void testCraftedBad() {
		assertEquals("NOT_RELIABLE", Classification.NOT_RELIABLE.getName());
	}

	@Test
	public void testCraftedNot() {
		assertEquals("NOT_CLASSIFIED", Classification.NOT_CLASSIFIED.getName());
	}
}
