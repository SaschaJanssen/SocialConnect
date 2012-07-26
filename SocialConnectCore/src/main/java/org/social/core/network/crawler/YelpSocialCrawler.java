package org.social.core.network.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.social.core.constants.Networks;

public class YelpSocialCrawler extends SocialCrawler {

	private final String ratingClassName = "div.rating";
	private final String messageDateClassName = "em.smaller";
	private final String userNameLinkClassName = "li.user-name a";
	private final String reviewCommentCssClassName = "p.review_comment";
	private final String reviewDataCssClassName = "div.media-story";
	private final String userDataCssClassName = "div.user-passport";
	private final String selectedPaginationCssClassName = "span.highlight2";
	private final String paginationControlsCssCLassName = "div.pagination_controls";
	private final String reviewContainerCssClassName = "div.media-block-no-margin";

	public YelpSocialCrawler(String baseUrl, String endpoint) {
		super(baseUrl, endpoint);
	}

	@Override
	protected String getPaginationControlsCssClassName() {
		return paginationControlsCssCLassName;
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
	public String getNextPageFromPagination(Element body) {
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

}
