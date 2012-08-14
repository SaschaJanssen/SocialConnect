package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.social.core.constants.Networks;

public class ZagatSocialCrawler extends SocialCrawler {

    public ZagatSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
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

        Element nextPageLink = body.select(paginationControlsCssClassName).first();
        if (nextPageLink != null) {
            nextPage = nextPageLink.attr("href");
        }
        return nextPage;
    }

    @Override
    protected String getNetworkName() {
        return Networks.ZAGAT.name();
    }

    @Override
    protected String getUserIdFromUserInfo(Element userInfo) {
        String href = userInfo.child(0).attr("href");
        String[] splited = href.split("/");

        return splited[2];
    }

    @Override
    protected String getUserNameFromUserInfo(Element userInfo) {
        return userInfo.text();
    }

    @Override
    protected String getPropertyFileName() {
        return "conf/zagat.properties";
    }
}
