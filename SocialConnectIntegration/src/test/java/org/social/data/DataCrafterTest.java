package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialTest;
import org.social.constants.Networks;
import org.social.entity.domain.Messages;

public class DataCrafterTest extends SocialTest {

	private DataCrafter crafter;

	public DataCrafterTest() {
		super();
	}

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
		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(2L);

		FilteredMessageList result = crafter.craft(cnk);

		assertEquals(2, result.countPositivMessages());
		assertEquals(2, result.countNegativeMessages());
	}

	private List<Messages> setUpDemoData() {
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
