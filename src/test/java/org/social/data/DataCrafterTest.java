package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataCrafterTest {

	DataCrafter crafter;

	@Before
	public void setUp() throws Exception {
		List<MessageData> rawData = setUpDemoData();
		crafter = new DataCrafter(rawData);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrafter() {
		FilteredMessageList result = crafter.craft();

		assertEquals(2, result.countPositivMessages());
		assertEquals(2, result.countNegativeMessages());
	}

	private List<MessageData> setUpDemoData() {
		MessageData demoData = new MessageData("Twitter");

		List<MessageData> rawData = new ArrayList<MessageData>();
		demoData.setMessage("Had wolfgangs for brunch. Today is already great.");
		rawData.add(demoData);

		demoData = new MessageData("Twitter");
		demoData.setMessage("Wolfgangs Vault  Weekly Be genuine. Be full of plenty. Feel popular. http://t.co/FGtbpFfj");
		rawData.add(demoData);

		demoData = new MessageData("Twitter");
		demoData.setMessage("Somebody should go to Real Food or Wolfgangs with me to celebrate the new pad not everybody at once lol");
		rawData.add(demoData);

		demoData = new MessageData("Twitter");
		demoData.setMessage("Killin some chest and biceps in the dungeon aka Wolfgangs gym w/ @Bobbypuryear");
		rawData.add(demoData);
		return rawData;
	}

}
