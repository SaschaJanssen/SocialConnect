package org.social.data;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.constants.Networks;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;

public class DataCrafterITCase extends SocialITCase {

	private DataCrafter crafter;

	public DataCrafterITCase() {
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
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		Customers customer = new Customers();
		customer.setLastNetworkdAccess(timestamp);
		customer.setCustomerId(2L);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(customer);

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
