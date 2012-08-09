package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.social.core.constants.Networks;

public class OpenTableSocialCrawler extends SocialCrawler {

    private final String ratingClassName = "span.BVRRRatingNumber";
    private final String messageDateClassName = "span.BVRRAdditionalFielddinedate";
    private final String userNameLinkClassName = "";
    private final String reviewCommentCssClassName = "span.BVRRReviewText";
    private final String reviewDataCssClassName = "div.BVRRReviewDisplayStyle5BodyContent";
    private final String userDataCssClassName = "div.user-passport";
    private final String selectedPaginationCssClassName = "span.BVRRSelectedPageNumber";
    private final String paginationControlsCssClassName = "div.BVRRPageBasedPager";
    private final String reviewContainerCssClassName = "div.BVRRSDisplayContentBody";

    public OpenTableSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        super(crawler, baseUrl, endpoint);
    }

    @Override
    protected String getPaginationControlsCssClassName() {
        return paginationControlsCssClassName;
    }

    @Override
    protected String getSelectedPaginationCssClassName() {
        return selectedPaginationCssClassName;
    }

    @Override
    protected String getUserNameLinkClassName() {
        return userNameLinkClassName;
    }

    @Override
    protected String getRatingClassName() {
        return ratingClassName;
    }

    @Override
    protected String getMessageDateClassName() {
        return messageDateClassName;
    }

    @Override
    protected String getReviewCommentCssClassName() {
        return reviewCommentCssClassName;
    }

    @Override
    protected String getReviewDataCssClassName() {
        return reviewDataCssClassName;
    }

    @Override
    protected String getUserDataCssClassName() {
        return userDataCssClassName;
    }

    @Override
    protected String getReviewContainerCssClassName() {
        return reviewContainerCssClassName;
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
}
