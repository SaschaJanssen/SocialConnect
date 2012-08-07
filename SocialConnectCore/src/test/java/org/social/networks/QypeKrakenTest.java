package org.social.networks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.QypeKraken;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.connection.SocialNetworkConnection;

public class QypeKrakenTest {

	private Customers customer;
	private KeywordDAO keywordDaoMock;
	private SocialNetworkConnection connectionMock;

	@Before
	public void setUp() throws Exception {
		customer = new Customers();
		customer.setCustomerId(1L);

		keywordDaoMock = new KeywordDAOMock();
		connectionMock = new QypeConnectionMock();
	}

	@Test
	public void test() {
		SocialNetworkKraken kraken = new QypeKraken(customer, keywordDaoMock, connectionMock);
		FilteredMessageList results = kraken.fetchAndCraftMessages();

		assertNotNull(results);
		assertEquals(1, results.countPositivMessages());
		assertEquals(0, results.countNegativeMessages());
	}

	@Test
	public void testGetNetworkKeyword() throws Exception {
		SocialNetworkKraken kraken = new QypeKraken(customer, keywordDaoMock, connectionMock);
		assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
	}
}
