package org.social.network.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.network.crawler.YelpSocialCrawler;

public class SocialYelpCrawlerTest extends SocialITCase {

	SocialCrawler jsoupCrawler;
	private Document doc = null;

	@Before
	public void setUp() throws Exception {
		jsoupCrawler = new YelpSocialCrawler("https://www.yelp.com", "/biz/vapiano-new-york-2?sort_by=date_desc");
		doc = jsoupCrawler.getDocument();
	}

	@Test
	public void testGetDocument() throws Exception {
		assertNotNull(doc);
		assertEquals("Vapiano - Greenwich Village - New York, NY", doc.title());
	}

	@Test
	public void testGetContainerOfReviewData() throws Exception {
		Element body = doc.body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		assertNotNull(reviewContainer);
		assertTrue(reviewContainer.size() > 1);
		assertTrue(reviewContainer.get(0).className().contains("media-block-no-margin"));
	}

	@Test
	public void testGetSitePagination() throws Exception {
		Element body = doc.body();
		Element paginationElement = jsoupCrawler.getPaginationCurrentSelectedData(body);

		assertNotNull(paginationElement);
		assertEquals("highlight2", paginationElement.className());
		assertEquals("1", paginationElement.text());
	}

	@Test
	public void testGetNextPageLinkFromPagination() throws Exception {
		Element body = doc.body();

		String nextLink = jsoupCrawler.getNextPageFromPagination(body);

		assertNotNull(nextLink);
		assertEquals("/biz/vapiano-new-york-2?sort_by=date_desc&start=40", nextLink);
	}

	@Test
	public void testExtractReviewData() throws Exception {
		Element body = doc.body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		List<Messages> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, doc.head(), 1L);

		assertTrue(result.size() >= 40);

		// assertTrue(result.get(0).getMessage().startsWith("Even though Vapiano is one of my favorite places"));
		// assertEquals("Dorie L.", result.get(0).getNetworkUser());
		// assertEquals("H9QzuPZn_wWlx0NYStSO6Q", result.get(0).getNetworkUserId());
		assertEquals("en", result.get(0).getLanguage());
		assertEquals("YELP", result.get(0).getNetworkId());
		assertEquals(1, result.get(0).getCustomerId().longValue());
		// assertEquals("4.0", result.get(0).getNetworkUserRating());
	}

}
