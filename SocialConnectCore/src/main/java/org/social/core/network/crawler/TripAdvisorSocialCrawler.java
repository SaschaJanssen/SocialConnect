package org.social.core.network.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.social.core.constants.Networks;

public class TripAdvisorSocialCrawler extends SocialCrawler {

	private final String ratingClassName = "span.rate";
	private final String messageDateClassName = "span.ratingDate";
	private final String userNameLinkClassName = "span.expand_inline";
	private final String reviewCommentCssClassName = "p.partial_entry";
	private final String reviewDataCssClassName = "div.basic_review";
	private final String userDataCssClassName = "div.member_info";
	private final String selectedPaginationCssClassName = "span.pageDisplay";
	private final String paginationControlsCssClassName = "div.pgLinks";
	private final String reviewContainerCssClassName = "div.review_collection";

	private String[] splitedBaseUrls = null;

	public TripAdvisorSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
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
		String rating = ratingElement.select("img[alt]").attr("alt");
		return rating.split(" ")[0];
	}

	@Override
	protected String getLanguageFromHeadMetaData(Element headerElements) {
		Element select = headerElements.parent().select("select#filterLang").first();
		Element selectedLanguage = select.select("option[selected]").first();

		return selectedLanguage.attr("value");
	}

	@Override
	public String getNextPageFromPagination(Element body) {
		String nextPage = null;

		Element pagination = body.select(getPaginationControlsCssClassName()).first();
		Element nextPageLink = pagination.select("span.sprite-pageNext").first();

		if (nextPageLink != null) {
			String currentPage = pagination.select(getSelectedPaginationCssClassName()).first().text();
			int currentPageNo = Integer.parseInt(currentPage);
			int nextPageIndicator = currentPageNo * 10;

			if (this.splitedBaseUrls == null) {
				splitBaseString(body);
			}

			StringBuilder nextLinkBuilder = new StringBuilder();
			nextLinkBuilder.append(this.splitedBaseUrls[0]);
			nextLinkBuilder.append("-");
			nextLinkBuilder.append(this.splitedBaseUrls[1]);
			nextLinkBuilder.append("-");
			nextLinkBuilder.append(this.splitedBaseUrls[2]);
			nextLinkBuilder.append("-");
			nextLinkBuilder.append("or" + nextPageIndicator);
			nextLinkBuilder.append("-");
			nextLinkBuilder.append(this.splitedBaseUrls[3]);

			nextPage = nextLinkBuilder.toString();
		}

		return nextPage;
	}

	public void splitBaseString(Element body) {
		splitedBaseUrls = new String[4];

		Element flagLinkElement = body.select("a[href].flag_link").first();
		String flagLinkHref = flagLinkElement.attr("href");
		int startFlag = StringUtils.indexOf(flagLinkHref, "/Restaurant_");
		flagLinkHref = flagLinkHref.substring(startFlag);

		Matcher matcher = Pattern.compile("/Restaurant_Review").matcher(flagLinkHref);
		if (matcher.find()) {
			splitedBaseUrls[0] = matcher.group();
		}

		matcher = Pattern.compile("[a-z]{1}[0-9]+-[a-z]{1}[0-9]+").matcher(flagLinkHref);
		if (matcher.find()) {
			splitedBaseUrls[1] = matcher.group();
		}

		matcher = Pattern.compile("Reviews").matcher(flagLinkHref);
		if (matcher.find()) {
			splitedBaseUrls[2] = matcher.group();
		}

		int qualifierStart = StringUtils.indexOf(flagLinkHref, "Reviews-");
		splitedBaseUrls[3] = flagLinkHref.substring(qualifierStart + 8);
	}

	@Override
	protected String getNetworkName() {
		return Networks.TRIPADVISOR.name();
	}

	protected String getUserIdFromUserInfo(Element userInfo) {
		return "n/a";
	}
}
