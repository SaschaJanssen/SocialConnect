package org.social.core.network.crawler;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface BaseCrawler {
    public Document crwal(String url) throws IOException;
}
