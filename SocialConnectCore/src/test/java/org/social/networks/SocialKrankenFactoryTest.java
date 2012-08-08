package org.social.networks;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.social.core.entity.domain.Customers;
import org.social.core.network.FacebookKraken;
import org.social.core.network.FoursquareKraken;
import org.social.core.network.OpenTableKraken;
import org.social.core.network.QypeKraken;
import org.social.core.network.SocialKrakenFactory;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.TripAdvisorKraken;
import org.social.core.network.TwitterKraken;
import org.social.core.network.YelpKraken;
import org.social.core.network.ZagatKraken;

public class SocialKrankenFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFactoryForAllNetworks() {
		SocialNetworkKraken snk = SocialKrakenFactory.getInstance("FACEBOOK", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof FacebookKraken);

		snk = SocialKrakenFactory.getInstance("TWITTER", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof TwitterKraken);

		snk = SocialKrakenFactory.getInstance("YELP", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof YelpKraken);

		snk = SocialKrakenFactory.getInstance("OPENTABLE", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof OpenTableKraken);

		snk = SocialKrakenFactory.getInstance("TRIPADVISOR", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof TripAdvisorKraken);

		snk = SocialKrakenFactory.getInstance("ZAGAT", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof ZagatKraken);

		snk = SocialKrakenFactory.getInstance("QYPE", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof QypeKraken);

		snk = SocialKrakenFactory.getInstance("FOURSQUARE", new Customers(), new KeywordDAOMock());
		assertTrue(snk instanceof FoursquareKraken);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentException() throws Exception {
		SocialKrakenFactory.getInstance("FOOBAA", new Customers(), new KeywordDAOMock());
	}

}
