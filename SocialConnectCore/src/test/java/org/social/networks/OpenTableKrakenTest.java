package org.social.networks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.OpenTableKraken;
import org.social.core.network.SocialNetworkKraken;
import org.social.core.network.crawler.BaseCrawler;

public class OpenTableKrakenTest {

    private Customers customer;
    private KeywordDAO keywordDaoMock;
    private BaseCrawler baseCrawler;

    @Before
    public void setUp() throws Exception {
        customer = new Customers();
        customer.setCustomerId(1L);

        keywordDaoMock = new KeywordDAOMock();
        baseCrawler = new MockOpenTableBaseCrawler();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new OpenTableKraken(customer, keywordDaoMock, baseCrawler);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(50, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new OpenTableKraken(customer, keywordDaoMock, baseCrawler);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
