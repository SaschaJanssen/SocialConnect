package org.social.networks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.FacebookKraken;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.connection.SocialNetworkConnection;

public class FacebookKrakenTest {

    private Customers customer;
    private KeywordDAO keywordDaoMock;
    private SocialNetworkConnection fbConnectionMock;

    @Before
    public void setUp() throws Exception {
        customer = new Customers();
        customer.setCustomerId(1L);

        keywordDaoMock = new KeywordDAOMock();
        fbConnectionMock = new FacebookConnectionMock();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new FacebookKraken(customer, keywordDaoMock, fbConnectionMock);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(1, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new FacebookKraken(customer, keywordDaoMock, fbConnectionMock);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
