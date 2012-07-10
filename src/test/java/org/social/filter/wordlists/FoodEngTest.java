package org.social.filter.wordlists;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.core.filter.wordlists.FoodEng;

public class FoodEngTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWordListMatcher_MatchPositiv() {
		String phrase_1 = "Had wolfgangs for brunch. Today is already great.";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertTrue(matches);

		String phrase_2 = "Easy way to fix a monday.  Meet @schechybear at Wolfgangs for some bacon and beers.  #dominant";
		matches = FoodEng.matchesWordList(phrase_2);
		assertTrue(matches);
	}

	@Test
	public void testIgnoreCase() throws Exception {
		String phrase = "love FooD";
		boolean matches = FoodEng.matchesWordList(phrase);
		assertTrue(matches);
	}

	@Test
	public void testWordListMatcher_startWithTag() throws Exception {
		String phrase_1 = "lunch FooBaa";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertTrue(matches);
	}
	@Test
	public void testWordListMatcher_startWithTagNegative() throws Exception {
		String phrase_1 = "lunchFooBaa";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertFalse(matches);
	}

	@Test
	public void testWordListMatcher_endWithTag() throws Exception {
		String phrase_1 = "FooBaa lollipop";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertTrue(matches);
	}

	@Test
	public void testWordListMatcher_endWithTagNegative() throws Exception {
		String phrase_1 = "FooBaalollipop";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertFalse(matches);
	}

	@Test
	public void testWordListMatcher_MatchNegative() {
		String phrase_1 = "FooBaa";
		boolean matches = FoodEng.matchesWordList(phrase_1);
		assertFalse(matches);

		String phrase_2 = "RT @IAmJericho: Discovered Wolfgangs Vault App thx to @brentfitz and its insane! Listenin to Ozzy Live 1986-Randy and Zakk are great, but Jake was the SHIT!";
		matches = FoodEng.matchesWordList(phrase_2);
		assertFalse(matches);

	}

}
