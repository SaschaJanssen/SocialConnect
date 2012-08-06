package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.social.core.constants.Networks;

public class ZagatSocialCrawler extends SocialCrawler {

	private final String ratingClassName = "";

	private final String reviewContainerCssClassName = "div.view-zagat-comments-recent";

	private final String reviewDataCssClassName = "div.comment";
	private final String reviewCommentCssClassName = "div.content p";
	private final String messageDateClassName = "div.submitted";

	private final String userNameLinkClassName = "div.user-display-name";
	private final String userDataCssClassName = "div.user-info";

	private final String paginationControlsCssClassName = "li.pager-next a";
	private final String selectedPaginationCssClassName = "";

	public ZagatSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
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

		Element nextPageLink = body.select(getPaginationControlsCssClassName()).first();
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
}
