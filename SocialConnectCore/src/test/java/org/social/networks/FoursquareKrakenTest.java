package org.social.networks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.FoursquareKraken;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.connection.SocialNetworkConnection;

public class FoursquareKrakenTest {

    private Customers customer;
    private KeywordDAO keywordDaoMock;
    private SocialNetworkConnection connectionMock;

    @Before
    public void setUp() throws Exception {
        customer = new Customers();
        customer.setCustomerId(1L);

        keywordDaoMock = new KeywordDAOMock();
        connectionMock = new FoursquareConnectionMock();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new FoursquareKraken(customer, keywordDaoMock, connectionMock);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(1, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());

        Messages msg = results.getPositivList().get(0);

        assertEquals("Mikey", msg.getNetworkUser());
        assertEquals("33226339", msg.getNetworkUserId());
        assertEquals("Burgers are great, try the Cajun style fries, the diet coke vanella and pib sodas are good too",
                msg.getMessage());
        assertEquals("RELIABLE", msg.getReliabilityId());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new FoursquareKraken(customer, keywordDaoMock, connectionMock);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
