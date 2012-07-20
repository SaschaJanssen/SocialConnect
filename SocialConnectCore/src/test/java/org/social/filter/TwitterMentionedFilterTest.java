package org.social.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.core.filter.TwitterMentionedFilter;

public class TwitterMentionedFilterTest {

	TwitterMentionedFilter mentionedFilter;

	@Before
	public void setUp() throws Exception {
		Set<String> mentionedSet = new HashSet<String>();
		mentionedSet.add("@Vapiano");
		mentionedSet.add("#Vapiano");
		mentionedSet.add("#Pizza");

		mentionedFilter = new TwitterMentionedFilter(mentionedSet);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsMentioned() {
		String phrase_1 = "Bewertung zu #Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		boolean matches = mentionedFilter.mentioned(phrase_1);
		assertTrue(matches);


		String phrase_2 = "Bewertung zu @Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		matches = mentionedFilter.mentioned(phrase_2);
		assertTrue(matches);

		String phrase_3 = "Bewertung zu@Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		matches = mentionedFilter.mentioned(phrase_3);
		assertTrue(matches);

		String phrase_4 = "Thank our sponsors, or yours! Tweet thx to a @girodicoppi or @MABRA_org team sponsor, tag it #girodicoppi. You could win @Vapiano_USA gift!";
		matches = mentionedFilter.mentioned(phrase_4);
		assertFalse(matches);

		String phrase_5 = "Bewertung zu #vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		matches = mentionedFilter.mentioned(phrase_5);
		assertTrue(matches);

		String phrase = "I'm at Vapiano (Mexico City, DF) w/ 4 others http://t.co/v35ToPC3";
		matches = mentionedFilter.mentioned(phrase);
		assertTrue(matches);

		phrase = "@ Vapiano on M St. has free Peroni 'refills' today if you seat in the Terrace. #MiWashingtonDC";
		matches = mentionedFilter.mentioned(phrase);
		assertTrue(matches);
	}

	@Test
	public void testEndsWithHash() throws Exception {
		String phrase_3 = "Bewertung zu Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten. Beste #Pizza";
		boolean matches = mentionedFilter.mentioned(phrase_3);
		assertTrue(matches);
	}

	@Test
	public void testStartsWithHash() throws Exception {
		String phrase_4 = "#Pizza Bewertung zu Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		boolean matches = mentionedFilter.mentioned(phrase_4);
		assertTrue(matches);

		String phrase_1 = "#pizza Bewertung zu Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		matches = mentionedFilter.mentioned(phrase_1);
		assertTrue(matches);
	}

	@Test
	public void testFakeHash() throws Exception {
		String phrase = "@Vapiano_Fake Bewertung zu Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		boolean matches = mentionedFilter.mentioned(phrase);
		assertFalse(matches);
	}

	@Test
	public void testNegativeNoMEnitions() throws Exception {
		String phrase_5 = "Bewertung zu Vapiano (Deutz, Köln,  von Golden_Ticket_Dunny): 4 von 5 Punkten";
		boolean matches = mentionedFilter.mentioned(phrase_5);
		assertFalse(matches);
	}

}
