package org.social.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.social.core.constants.CraftedState;

public class CraftedStateTest {

	@Test
	public void testCraftedGood() {
		assertEquals("GOOD", CraftedState.GOOD.getName());
	}

	@Test
	public void testCraftedBad() {
		assertEquals("GOOD", CraftedState.GOOD.getName());
	}

	@Test
	public void testCraftedNot() {
		assertEquals("GOOD", CraftedState.GOOD.getName());
	}
}
