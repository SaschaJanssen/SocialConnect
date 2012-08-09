package org.social.networks.crawler;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.social.core.network.crawler.BaseCrawler;

public class MockBaseCrawler implements BaseCrawler {

    @Override
    public Document crwal(String url) throws IOException {
        File fi = new File(url);
        return Jsoup.parse(fi, "UTF-8");
    }

}
