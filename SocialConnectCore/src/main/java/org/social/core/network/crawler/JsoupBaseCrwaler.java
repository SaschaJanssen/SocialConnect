package org.social.core.network.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupBaseCrwaler implements BaseCrawler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Document crwal(String url) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Start crawling data from: " + url);
		}

		Exception ex = null;
		int maxTriesToGetRemoteData = 4;
		int tries = 0;
		while (tries < maxTriesToGetRemoteData) {
			try {
				return Jsoup.connect(url).timeout(5000).get();
			} catch (IOException e) {
				ex = e;
				if (logger.isWarnEnabled()) {
					logger.warn("Got a" + e.getMessage() + " Exception, try a again to fetch data from remote address.");
				}
				tries++;
			}
		}

		throw new IOException("After " + maxTriesToGetRemoteData + " runs, gave up on fatching data from remote url " + url, ex);
	}

}
