package org.social.networks.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.network.crawler.YelpSocialCrawler;

public class YelpSocialCrawlerTest {

    SocialCrawler yelpCrawler = null;

    @Before
    public void setUp() throws Exception {
        yelpCrawler = new YelpSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
                "YelpVapianoTest_WithoutPagination.html");
    }

    @Test
    public void testGetDocument() throws Exception {
        assertNotNull(yelpCrawler.getDocument());
        assertEquals("Vapiano - Greenwich Village - New York, NY", yelpCrawler.getDocument().title());
    }

    @Test
    public void testGetContainerOfReviewData() throws Exception {
        Element body = yelpCrawler.getDocument().body();
        Elements reviewContainer = yelpCrawler.getReviewDataContainer(body);

        assertNotNull(reviewContainer);
        assertTrue(reviewContainer.size() > 1);
        assertTrue(reviewContainer.get(0).className().contains("media-block-no-margin"));
    }

    @Test
    public void testGetSitePagination() throws Exception {
        Element body = yelpCrawler.getDocument().body();
        Element paginationElement = yelpCrawler.getPaginationCurrentSelectedData(body);

        assertNotNull(paginationElement);
        assertEquals("highlight2", paginationElement.className());
        assertEquals("1", paginationElement.text());
    }

    @Test
    public void testGetNextPageLinkFromPagination() throws Exception {
        SocialCrawler secondYelpCrawler = new YelpSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
                "YelpVapianoTest_WithPagination.html");

        Element body = secondYelpCrawler.getDocument().body();

        String nextLink = secondYelpCrawler.getNextPageFromPagination(body);

        assertNotNull("The result should not be null.", nextLink);
        assertEquals("/biz/vapiano-new-york-2?sort_by=date_desc&start=40", nextLink);
    }

    @Test
    public void testParseDocument() {
        Customers customer = new Customers();
        customer.setCustomerId(1L);

        List<Messages> result = yelpCrawler.crawl(customer);

        assertTrue(result.get(0).getMessage().startsWith("Even though Vapiano is one of my favorite places"));
        assertEquals("Dorie L.", result.get(0).getNetworkUser());
        assertEquals("H9QzuPZn_wWlx0NYStSO6Q", result.get(0).getNetworkUserId());
        assertEquals("en", result.get(0).getLanguage());
        assertEquals("YELP", result.get(0).getNetworkId());
        assertEquals(1, result.get(0).getCustomerId().longValue());
        assertEquals("4.0", result.get(0).getNetworkUserRating());
    }
}
