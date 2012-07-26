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
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.util.UtilDateTime;

public abstract class SocialCrawler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String baseUrl;

	private String endpoint;
	private Document document = null;

	public SocialCrawler(String baseUrl, String endpoint) {
		this.baseUrl = baseUrl;
		this.endpoint = endpoint;
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

			endpoint = getNextPageFromPagination(body);
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
		return body.select(getReviewContainerCssClassName());
	}

	protected abstract String getReviewContainerCssClassName();

	public Element getPaginationCurrentSelectedData(Element body) {
		Element pagination = body.select(getPaginationControlsCssClassName()).first();
		Element currentPaginationSelection = pagination.select(getSelectedPaginationCssClassName()).first();
		return currentPaginationSelection;
	}

	protected abstract String getPaginationControlsCssClassName();

	protected abstract String getSelectedPaginationCssClassName();

	public abstract String getNextPageFromPagination(Element currentPaginationElement);

	public List<Messages> extractReviewDataFromHtml(Elements reviewContainer, Element headerElements, Long customerId) {
		List<Messages> resultList = new ArrayList<Messages>();

		Elements pageUserData = getUserData(reviewContainer);
		Elements pageReviewData = getReviewData(reviewContainer);

		for (int i = 0; i < pageReviewData.size(); i++) {
			Messages returnMessage = new Messages(getNetworkName());

			Element reviewData = pageReviewData.get(i);
			if (!pageUserData.isEmpty()) {
				Element userData = pageUserData.get(i);
				Element userInfo = userData.select(getUserNameLinkClassName()).first();
				String networkUser = getUserNameFromUserInfo(userInfo);
				returnMessage.setNetworkUser(networkUser);
				String networkUserId = getUserIdFromUserInfo(userInfo);
				returnMessage.setNetworkUserId(networkUserId);
			} else {
				returnMessage.setNetworkUser("n/a");
				returnMessage.setNetworkUserId("n/a");
			}

			returnMessage.setCustomerId(customerId);

			String message = getReviewTextFromComment(reviewData);
			returnMessage.setMessage(message);

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

	protected abstract String getNetworkName();

	protected abstract String getUserNameLinkClassName();

	private String getNetworkUserRating(Element reviewData) {
		Element ratingElement = reviewData.select(getRatingClassName()).first();

		String platformUserRating = extractNetworkUserRatingData(ratingElement);
		return platformUserRating;
	}

	protected abstract String extractNetworkUserRatingData(Element ratingElement);

	protected abstract String getRatingClassName();

	private String getNetworkMessageDate(Element reviewData) {
		String networkMessageDate = reviewData.select(getMessageDateClassName()).first().text();
		return networkMessageDate;
	}

	protected abstract String getMessageDateClassName();

	private String getUserNameFromUserInfo(Element userInfo) {
		return userInfo.text();
	}

	protected abstract String getLanguageFromHeadMetaData(Element headerElements);

	private String getUserIdFromUserInfo(Element userInfo) {
		String href = userInfo.attr("href");
		String userId = href.substring(href.indexOf("=") + 1);
		String networkUserId = userId;
		return networkUserId;
	}

	private String getReviewTextFromComment(Element reviewData) {
		return reviewData.select(getReviewCommentCssClassName()).first().text();
	}

	protected abstract String getReviewCommentCssClassName();

	private Elements getReviewData(Elements reviewContainer) {
		return reviewContainer.select(getReviewDataCssClassName());
	}

	protected abstract String getReviewDataCssClassName();

	private Elements getUserData(Elements reviewContainer) {
		return reviewContainer.select(getUserDataCssClassName());
	}

	protected abstract String getUserDataCssClassName();

}
