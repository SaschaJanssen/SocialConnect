package org.social.data;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.constants.Networks;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Customers;

public class UserNetworkKeywordsITCase extends SocialITCase {

	private CustomerNetworkKeywords unk = null;
	private Timestamp timestamp;

	public UserNetworkKeywordsITCase() {
		super();
	}

	@Before
	public void setUp() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		timestamp = new Timestamp(calendar.getTimeInMillis());

		Customers customer = new Customers();
		customer.setLastNetworkdAccess(timestamp);
		customer.setCustomerId(1L);

		unk = new CustomerNetworkKeywords(customer);
	}

	@Test
	public void testGetTwitterHash() {
		String hash = unk.getHashForNetwork(Networks.TWITTER);
		assertEquals("#Vapiano", hash);
	}

	@Test
	public void testGetTwitterQuery() {
		String query = unk.getQueryForNetwork(Networks.TWITTER);
		assertEquals("Vapiano", query);
	}

	@Test
	public void testGetTwitterMentioned() {
		String hash = unk.getMentionedForNetwork(Networks.TWITTER);
		assertEquals("", hash);
	}

	@Test
	public void testLastNetworkAccessTw() throws Exception {
		String networkAccess = unk.getLastAccessForNetwork(Networks.TWITTER);
		assertEquals("2012-07-10", networkAccess);
	}

	@Test
	public void testLastNetworkAccessFb() throws Exception {
		String networkAccess = unk.getLastAccessForNetwork(Networks.FACEBOOK);

		Long unixTx = timestamp.getTime() / 1000L;
		assertEquals(unixTx.toString(), networkAccess);
	}

}
