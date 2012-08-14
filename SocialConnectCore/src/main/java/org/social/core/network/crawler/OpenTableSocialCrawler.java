package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.social.core.constants.Networks;

public class OpenTableSocialCrawler extends SocialCrawler {

    public OpenTableSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        super(crawler, baseUrl, endpoint);
    }

    @Override
    protected String extractNetworkUserRatingData(Element ratingElement) {
        return ratingElement.text();
    }

    @Override
    protected String getLanguageFromHeadMetaData(Element headerElements) {
        return headerElements.parent().attr("lang");
    }

    @Override
    public String getNextPageFromPagination(Element body) {
        String nextPage = null;

        Element nextPageLink = body.select("span.BVRRNextPage a").first();
        if (nextPageLink != null) {
            nextPage = nextPageLink.attr("href");
            nextPage = nextPage.substring(nextPage.indexOf(".com") + 4);
        }
        return nextPage;
    }

    @Override
    protected String getNetworkName() {
        return Networks.OPENTABLE.name();
    }

    @Override
    protected String getUserIdFromUserInfo(Element userInfo) {
        return "n/a";
    }

    @Override
    protected String getUserNameFromUserInfo(Element userInfo) {
        return "n/a";
    }

    @Override
    protected String getPropertyFileName() {
        return "conf/openTable.properties";
    }
}
