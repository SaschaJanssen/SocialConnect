package org.social.data;

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
	private Customers customer;

	public DataCrafterITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		customer = new Customers();
		customer.setLastNetworkdAccess(timestamp);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrafter() {
		List<Messages> rawData = setUpDemoData_One();
		crafter = new DataCrafter(rawData);

		customer.setCustomerId(2L);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(customer);

		FilteredMessageList result = crafter.craft(cnk);

		assertEquals(2, result.countPositivMessages());
		assertEquals(2, result.countNegativeMessages());
	}

	@Test
	public void testCrafter_2() {
		List<Messages> rawData = setUpDemoData_Two();
		crafter = new DataCrafter(rawData);

		customer.setCustomerId(1L);

		CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(customer);

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
