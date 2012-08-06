package org.social.networks.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.network.crawler.ZagatSocialCrawler;

public class ZagatSocialCrawlerTest {

	SocialCrawler jsoupCrawler;

	@Before
	public void setUp() throws Exception {
		// http://www.zagat.com/r/n/five-guys-queens-3/reviews
		jsoupCrawler = new ZagatSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
				"ZagatFivaGuysTest_WithoutPagination.html");
	}

	@Test
	public void testGetDocument() throws Exception {
		assertNotNull(jsoupCrawler.getDocument());
		assertEquals("Five Guys | Zagat", jsoupCrawler.getDocument().title());
	}

	@Test
	public void testGetContainerOfReviewData() throws Exception {
		Element body = jsoupCrawler.getDocument().body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		assertNotNull(reviewContainer);
		assertTrue(reviewContainer.size() > 0);
		assertEquals(
				"view view-zagat-comments-recent view-id-zagat_comments_recent view-display-id-page view-dom-id-1 view-zagat-comments-recent view-id-zagat_comments_recent view-display-id-page view-dom-id-1",
				reviewContainer.get(0).className());
	}

	@Test
	public void testGetNextPageLinkFromPagination() throws Exception {
		SocialCrawler secondJsoupCrawler = new ZagatSocialCrawler(new MockBaseCrawler(), "src/test/resources/",
				"ZagatFivaGuysTest_WithPagination.html");

		Element body = secondJsoupCrawler.getDocument().body();

		String nextLink = secondJsoupCrawler.getNextPageFromPagination(body);

		assertNotNull(nextLink);
		assertEquals("/r/n/five-guys-queens-3/reviews?page=1", nextLink);
	}

	@Test
	public void testExtractReviewData() throws Exception {
		Element body = jsoupCrawler.getDocument().body();
		Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body);

		List<Messages> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, jsoupCrawler.getDocument()
				.head(), 3L);

		assertTrue(result.size() >= 10);

		assertTrue(result.get(0).getMessage().startsWith("Amazingly juicy and nice burgers and"));
		assertEquals("Ray Y", result.get(0).getNetworkUser());
		assertEquals("4355100", result.get(0).getNetworkUserId());
		assertEquals("en", result.get(0).getLanguage());
		assertEquals("ZAGAT", result.get(0).getNetworkId());
		assertEquals(3, result.get(0).getCustomerId().longValue());
		assertEquals("n/a", result.get(0).getNetworkUserRating());
	}

}
