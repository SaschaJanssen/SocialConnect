package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.social.core.constants.Networks;
import org.social.core.exceptions.ItemNotFoundException;

public class YelpSocialCrawler extends SocialCrawler {

    public YelpSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        super(crawler, baseUrl, endpoint);
    }

    @Override
    protected String extractNetworkUserRatingData(Element ratingElement) {
        return ratingElement.getElementsByTag("meta").first().attr("content");
    }

    @Override
    protected String getLanguageFromHeadMetaData(Element headerElements) {
        Elements metaData = headerElements.getElementsByTag("meta");
        String language = "";
        for (Element meta : metaData) {
            if (meta.hasAttr("http-equiv") && meta.attr("http-equiv").equals("Content-Language")) {
                language = meta.attr("content");
                break;
            }
        }
        return language;
    }

    @Override
    public String getNextPageFromPagination(Element body) throws ItemNotFoundException {
        Element currentPaginationElement = getPaginationCurrentSelectedData(body);
        String nextLink = null;

        Elements siblings = currentPaginationElement.siblingElements();
        int pageNo = convertElementTextToInt(currentPaginationElement);
        for (Element sibling : siblings) {
            int nextPageNo = convertElementTextToInt(sibling);
            if (nextPageNo == (pageNo + 1)) {
                nextLink = sibling.attr("href");
                break;
            }
        }

        return nextLink;

    }

    private int convertElementTextToInt(Element element) {
        return Integer.parseInt(element.text());
    }

    @Override
    protected String getNetworkName() {
        return Networks.YELP.name();
    }

    @Override
    protected String getUserIdFromUserInfo(Element userInfo) {
        String href = userInfo.attr("href");
        return href.substring(href.indexOf("=") + 1);
    }

    @Override
    protected String getUserNameFromUserInfo(Element userInfo) {
        return userInfo.text();
    }

    @Override
    protected String getPropertyFileName() {
        return "conf/yelp.properties";
    }

}
