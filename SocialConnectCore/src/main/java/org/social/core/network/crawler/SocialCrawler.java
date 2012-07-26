package org.social.core.network.crawler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.util.UtilDateTime;

public class SocialCrawler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String ratingClassName = "div.rating";
	private final String messageDateClassName = "em.smaller";
	private final String userNameLinkClassName = "li.user-name a";
	private final String reviewCommentCssClassName = "p.review_comment";
	private final String reviewDataCssClassName = "div.media-story";
	private final String userDataCssClassName = "div.user-passport";
	private final String selectedPaginationCssClassName = "span.highlight2";
	private final String paginationControlsCssCLassName = "div.pagination_controls";
	private final String reviewContainerCssClassName = "div.media-block-no-margin";

	private final String baseUrl;

	private String endpoint;
	private Document document = null;

	public SocialCrawler(String baseUrl, String endpoint) {
		this.baseUrl = baseUrl;
		this.endpoint = endpoint + "?sort_by=date_desc";
	}

	public List<Messages> crawl(Customers customer) {
		List<Messages> extractedMessagesFilterByDate = new ArrayList<Messages>();
		boolean anyMoreNewMessages = true;

		while (anyMoreNewMessages && endpoint != null && !endpoint.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Next request found: " + endpoint);
			}

			try {
				document = getDocument();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

			Element body = document.body();
			Elements reviewContainer = getReviewDataContainer(body);

			if (logger.isDebugEnabled()) {
				logger.debug("Extracting information from request: " + endpoint);
			}

			List<Messages> extractedMessages = extractReviewDataFromHtml(reviewContainer, document.head(),
					customer.getCustomerId());

			Iterator<Messages> messageIterator = extractedMessages.iterator();
			if (logger.isDebugEnabled()) {
				logger.debug("Check if there are messages which are older than the last customer network acces date.");
			}
			while (messageIterator.hasNext()) {
				Messages message = messageIterator.next();
				Timestamp networkTs = message.getNetworkMessageDate();
				Timestamp lastNetworkAccess = customer.getLastNetworkdAccess();

				if (isMessageYoungerThanLastNetworkAccess(networkTs, lastNetworkAccess)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Found message which is already stored. Remove it from current batch.");
					}

					anyMoreNewMessages = false;
					messageIterator.remove();
				}
			}

			extractedMessagesFilterByDate.addAll(extractedMessages);

			Element currentPaginationSite = getPaginationCurrentSelectedData(body);
			endpoint = getNextPageFromPagination(currentPaginationSite);
		}

		return extractedMessagesFilterByDate;
	}

	public boolean isMessageYoungerThanLastNetworkAccess(Timestamp messageNetworkTs, Timestamp lastNetworkAccess) {
		return (messageNetworkTs != null && lastNetworkAccess != null && lastNetworkAccess.after(messageNetworkTs));
	}

	public Document getDocument() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Start crawling data from: " + baseUrl + endpoint);
		}
		return Jsoup.connect(baseUrl + endpoint).get();
	}

	public Elements getReviewDataContainer(Element body) {
		return body.select(reviewContainerCssClassName);
	}

	public Element getPaginationCurrentSelectedData(Element body) {
		Element pagination = body.select(paginationControlsCssCLassName).first();
		Element currentPaginationSelection = pagination.select(selectedPaginationCssClassName).first();
		return currentPaginationSelection;
	}

	public String getNextPageFromPagination(Element currentPaginationElement) {
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
		return Integer.parseInt(getUserNameFromUserInfo(element));
	}

	public List<Messages> extractReviewDataFromHtml(Elements reviewContainer, Element headerElements, Long customerId) {
		List<Messages> resultList = new ArrayList<Messages>();

		Elements pageUserData = getUserData(reviewContainer);
		Elements pageReviewData = getReviewData(reviewContainer);

		for (int i = 0; i < pageReviewData.size(); i++) {
			Messages returnMessage = new Messages(Networks.YELP.name());

			Element reviewData = pageReviewData.get(i);
			Element userData = pageUserData.get(i);

			returnMessage.setCustomerId(customerId);

			String message = getReviewTextFromComment(reviewData);
			returnMessage.setMessage(message);

			Element userInfo = userData.select(userNameLinkClassName).first();
			String networkUser = getUserNameFromUserInfo(userInfo);
			returnMessage.setNetworkUser(networkUser);

			String networkUserId = getUserIdFromUserInfo(userInfo);
			returnMessage.setNetworkUserId(networkUserId);

			String language = getLanguageFromHeadMetaData(headerElements);
			returnMessage.setLanguage(language);

			// TODO String geoLocation;

			String networkMessageDate = getNetworkMessageDate(reviewData);
			returnMessage.setNetworkMessageDate(UtilDateTime.toTimestamp(networkMessageDate));

			returnMessage.setMessageReceivedDate(UtilDateTime.nowTimestamp());

			String platformUserRating = getNetworkUserRating(reviewData);
			returnMessage.setNetworkUserRating(platformUserRating);

			returnMessage.setReliabilityId(Classification.RELIABLE.getName());

			resultList.add(returnMessage);
		}
		return resultList;
	}

	private String getNetworkUserRating(Element reviewData) {
		String platformUserRating = reviewData.select(ratingClassName).first().getElementsByTag("meta").first()
				.attr("content");
		return platformUserRating;
	}

	private String getNetworkMessageDate(Element reviewData) {
		String networkMessageDate = reviewData.select(messageDateClassName).first().text();
		return networkMessageDate;
	}

	private String getUserNameFromUserInfo(Element userInfo) {
		return userInfo.text();
	}

	private String getLanguageFromHeadMetaData(Element headerElements) {
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

	private String getUserIdFromUserInfo(Element userInfo) {
		String href = userInfo.attr("href");
		String userId = href.substring(href.indexOf("=") + 1);
		String networkUserId = userId;
		return networkUserId;
	}

	private String getReviewTextFromComment(Element reviewData) {
		return reviewData.select(reviewCommentCssClassName).first().text();
	}

	private Elements getReviewData(Elements reviewContainer) {
		return reviewContainer.select(reviewDataCssClassName);
	}

	private Elements getUserData(Elements reviewContainer) {
		return reviewContainer.select(userDataCssClassName);
	}

}
