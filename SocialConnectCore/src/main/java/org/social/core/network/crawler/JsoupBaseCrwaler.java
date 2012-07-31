package org.social.core.network.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupBaseCrwaler implements BaseCrawler{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Document crwal(String url) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Start crawling data from: " + url);
		}

		return Jsoup.connect(url).timeout(5000).get();
	}

}
