package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.KeywordType;
import org.social.core.constants.Networks;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.domain.Messages;

public class DataCrafterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrafter() {
		DataCrafter crafter = new DataCrafter(setUpDemoData_One());

		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.HASH.getName());
		keywords.setKeyword("#WOLFGANGSSTEAKH");
		keywordListForNetwork.add(keywords);

		keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.QUERY.getName());
		keywords.setKeyword("Wolfgangs");
		keywordListForNetwork.add(keywords);

		keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.MENTIONED.getName());
		keywords.setKeyword("@WOLFGANGSSTEAKH");
		keywordListForNetwork.add(keywords);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(keywordListForNetwork);

		FilteredMessageList result = crafter.craft(cnk);

		assertEquals(2, result.countPositivMessages());
		assertEquals(2, result.countNegativeMessages());
	}

	@Test
	public void testCrafter_2() {
		DataCrafter crafter = new DataCrafter(setUpDemoData_Two());

		List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
		Keywords keywords = new Keywords();
		keywords.setCustomerId(2L);
		keywords.setKeywordTypeId(KeywordType.HASH.getName());
		keywords.setKeyword("#Vapiano");
		keywordListForNetwork.add(keywords);

		keywords = new Keywords();
		keywords.setCustomerId(1L);
		keywords.setKeywordTypeId(KeywordType.QUERY.getName());
		keywords.setKeyword("Vapiano");
		keywordListForNetwork.add(keywords);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(keywordListForNetwork);

		FilteredMessageList result = crafter.craft(cnk);

		assertEquals(1, result.countPositivMessages());
		assertEquals(3, result.countNegativeMessages());
	}

	private List<Messages> setUpDemoData_Two() {
		Messages demoData = new Messages(Networks.TWITTER.getName());

		List<Messages> rawData = new ArrayList<Messages>();
		demoData.setMessage("RT @girodicoppi: Thank our sponsors, or yours! Tweet thx to a Giro or @VirginiaCycling team sponsor, tag it #girodicoppi. You could win @Vapiano_USA gift!");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("I just ousted Sebi L. as the mayor of Vapiano on @foursquare! http://t.co/H068E93A");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("Vapiano Surfers Paradise - great food &amp; wine! http://t.co/PBcmtSo4");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("Thank our sponsors, or yours! Tweet thx to a @girodicoppi or @MABRA_org team sponsor, tag it #girodicoppi. You could win @Vapiano_USA gift!");
		rawData.add(demoData);
		return rawData;
	}

	private List<Messages> setUpDemoData_One() {
		Messages demoData = new Messages(Networks.TWITTER.getName());

		List<Messages> rawData = new ArrayList<Messages>();
		demoData.setMessage("Had wolfgangs for brunch. Today is already great.");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("Wolfgangs Vault  Weekly Be genuine. Be full of plenty. Feel popular. http://t.co/FGtbpFfj");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("Somebody should go to Real Food or Wolfgangs with me to celebrate the new pad not everybody at once lol");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.getName());
		demoData.setMessage("Killin some chest and biceps in the dungeon aka Wolfgangs gym w/ @Bobbypuryear");
		rawData.add(demoData);
		return rawData;
	}

}
