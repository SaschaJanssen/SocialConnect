package org.social.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.core.util.UtilValidate;

public class UtilValidateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStirng_IsEmpty() {
		assertTrue(UtilValidate.isEmpty(""));
		assertTrue(UtilValidate.isEmpty(null));
		assertTrue(UtilValidate.isEmpty(new String()));

		assertFalse(UtilValidate.isEmpty("Foo"));
	}

	@Test
	public void testStirng_IsNotEmpty() throws Exception {
		assertTrue(UtilValidate.isNotEmpty("Baa"));

		assertFalse(UtilValidate.isNotEmpty(""));
		assertFalse(UtilValidate.isNotEmpty(null));
		assertFalse(UtilValidate.isNotEmpty(new String()));
	}

}
