package org.social.network.crawler;

import static org.junit.Assert.assertNotNull;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.social.SocialITCase;
import org.social.core.network.crawler.BaseCrawler;
import org.social.core.network.crawler.JsoupBaseCrwaler;

public class CrawlerITCase extends SocialITCase {

	@Test
	public void testJsoupCrawler() throws Exception {
		BaseCrawler crawler = new JsoupBaseCrwaler();
		Document doc = crawler.crwal("https://www.yelp.com/biz/vapiano-new-york-2?sort_by=date_desc");

		assertNotNull(doc);
	}
}
