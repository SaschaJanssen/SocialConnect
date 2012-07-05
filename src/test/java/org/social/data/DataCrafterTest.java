package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.constants.Networks;
import org.social.entity.domain.Messages;

public class DataCrafterTest {

	DataCrafter crafter;

	@Before
	public void setUp() throws Exception {
		List<Messages> rawData = setUpDemoData();
		crafter = new DataCrafter(rawData);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrafter() {
		Set<String> mentionedSet = new HashSet<String>();

		FilteredMessageList result = crafter.craft(mentionedSet);

		assertEquals(2, result.countPositivMessages());
		assertEquals(2, result.countNegativeMessages());
	}

	private List<Messages> setUpDemoData() {
		Messages demoData = new Messages(Networks.TWITTER.toString());

		List<Messages> rawData = new ArrayList<Messages>();
		demoData.setMessage("Had wolfgangs for brunch. Today is already great.");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.toString());
		demoData.setMessage("Wolfgangs Vault  Weekly Be genuine. Be full of plenty. Feel popular. http://t.co/FGtbpFfj");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.toString());
		demoData.setMessage("Somebody should go to Real Food or Wolfgangs with me to celebrate the new pad not everybody at once lol");
		rawData.add(demoData);

		demoData = new Messages(Networks.TWITTER.toString());
		demoData.setMessage("Killin some chest and biceps in the dungeon aka Wolfgangs gym w/ @Bobbypuryear");
		rawData.add(demoData);
		return rawData;
	}

}
