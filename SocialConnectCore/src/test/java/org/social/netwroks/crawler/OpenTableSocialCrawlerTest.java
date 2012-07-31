package org.social.netwroks.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.OpenTableSocialCrawler;
import org.social.core.network.crawler.SocialCrawler;

public class OpenTableSocialCrawlerTest {

	SocialCrawler jsoupCrawler;

	@Before
	public void setUp() throws Exception {
		jsoupCrawler = new OpenTableSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
				"OpenTableWolfgangsTest_WithoutPagination.html");
	}

	@Test
	public void testGetDocument() throws Exception {
		assertNotNull(jsoupCrawler.getDocument());
		assertEquals("Wolfgang's Steak House - 54th Street Reviews - Rated by OpenTable Diners", jsoupCrawler.getDocument().title());
	}

	@Test
	public void testGetContainerOfReviewData() throws Exception {
		Element body = jsoupCrawler.getDocument().body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		assertNotNull(reviewContainer);
		assertTrue(reviewContainer.size() > 0);
		assertEquals("BVRRSDisplayContentBody", reviewContainer.get(0).className());
	}

	@Test
	public void testGetNextPageLinkFromPagination() throws Exception {
		SocialCrawler secondJsoupCrawler = new OpenTableSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
				"OpenTableWolfgangsTest_WithPagination.html");

		Element body = secondJsoupCrawler.getDocument().body();

		String nextLink = secondJsoupCrawler.getNextPageFromPagination(body);

		assertNotNull(nextLink);
		assertEquals("/0938/41533/reviews.htm?page=2", nextLink);
	}

	@Test
	public void testExtractReviewData() throws Exception {
		Element body = jsoupCrawler.getDocument().body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		List<Messages> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, jsoupCrawler.getDocument().head(), 1L);

		assertTrue(result.size() >= 10);

		// assertTrue(result.get(0).getMessage().startsWith("Even though Vapiano is one of my favorite places"));
		// assertEquals("Dorie L.", result.get(0).getNetworkUser());
		// assertEquals("H9QzuPZn_wWlx0NYStSO6Q",
		// result.get(0).getNetworkUserId());
		assertEquals("en-US", result.get(0).getLanguage());
		assertEquals("OPENTABLE", result.get(0).getNetworkId());
		assertEquals(1, result.get(0).getCustomerId().longValue());
		// assertEquals("4.0", result.get(0).getNetworkUserRating());
	}

}
