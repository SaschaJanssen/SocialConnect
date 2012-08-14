package org.social.core.network.crawler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.exceptions.ItemNotFoundException;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilValidate;

public abstract class SocialCrawler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String baseUrl;
    private final BaseCrawler crawler;

    private String endpoint;
    private Document document = null;

    private boolean anyMoreNewMessages;

    public SocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;

        this.crawler = crawler;
    }

    public List<Messages> crawl(Customers customer) throws ItemNotFoundException {
        List<Messages> extractedMessagesFilterByDate = new ArrayList<Messages>();
        anyMoreNewMessages = true;

        while (anyMoreNewMessages && endpoint != null && !endpoint.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Next request found: " + endpoint);
            }

            try {
                document = getDocument();
            } catch (IOException e) {
                logger.error("", e);
                break;
            }

            Element body = document.body();
            Elements reviewContainer = getReviewDataContainer(body);

            if (logger.isDebugEnabled()) {
                logger.debug("Extracting information from request: " + endpoint);
            }

            List<Messages> extractedMessages = extractReviewDataFromHtml(reviewContainer, document.head(),
                    customer.getCustomerId());

            extractedMessages = filterMessageByDate(extractedMessages, customer.getLastNetworkdAccess());

            extractedMessagesFilterByDate.addAll(extractedMessages);

            endpoint = getNextPageFromPagination(body);
        }

        return extractedMessagesFilterByDate;
    }

    private List<Messages> filterMessageByDate(List<Messages> extractedMessages, Timestamp customerLastNetworkAccess) {
        Iterator<Messages> messageIterator = extractedMessages.iterator();
        if (logger.isDebugEnabled()) {
            logger.debug("Check if there are messages which are older than the last customer network acces date.");
        }
        while (messageIterator.hasNext()) {
            Messages message = messageIterator.next();
            Timestamp networkTs = message.getNetworkMessageDate();

            if (UtilDateTime.isMessageDateBeforeLastNetworkAccess(networkTs, customerLastNetworkAccess)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Found message which is already stored. Remove it from current batch: "
                            + message.toString());
                }

                anyMoreNewMessages = false;
                messageIterator.remove();
            }
        }
        messageIterator = null;

        return extractedMessages;
    }

    public Document getDocument() throws IOException {
        return crawler.crwal(baseUrl + endpoint);
    }

    public Elements getReviewDataContainer(Element body) throws ItemNotFoundException {
        Elements reviewData = selectFromElement(body, getReviewContainerCssClassName());
        return reviewData;
    }

    protected abstract String getReviewContainerCssClassName();

    public Element getPaginationCurrentSelectedData(Element body) throws ItemNotFoundException {
        Elements paginationElements = selectFromElement(body, getPaginationControlsCssClassName());
        Element pagination = paginationElements.first();
        Elements currentSelectedPaginationElements = selectFromElement(pagination, getSelectedPaginationCssClassName());
        return currentSelectedPaginationElements.first();
    }

    protected abstract String getPaginationControlsCssClassName();

    protected abstract String getSelectedPaginationCssClassName();

    public abstract String getNextPageFromPagination(Element currentPaginationElement) throws ItemNotFoundException;

    public List<Messages> extractReviewDataFromHtml(Elements reviewContainer, Element headerElements, Long customerId)
            throws ItemNotFoundException {
        List<Messages> resultList = new ArrayList<Messages>();

        Elements pageUserData = getUserData(reviewContainer);
        Elements pageReviewData = getReviewData(reviewContainer);

        for (int i = 0; i < pageReviewData.size(); i++) {
            Messages returnMessage = new Messages(getNetworkName());

            Element reviewData = pageReviewData.get(i);

            Element userData = pageUserData.get(i);
            Elements selectedUserDate = selectFromElement(userData, getUserNameLinkClassName());
            Element userInfo = selectedUserDate.first();
            String networkUser = getUserNameFromUserInfo(userInfo);
            returnMessage.setNetworkUser(networkUser);

            String networkUserId = getUserIdFromUserInfo(userInfo);
            returnMessage.setNetworkUserId(networkUserId);

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

    private String getNetworkUserRating(Element reviewData) throws ItemNotFoundException {
        String userRating = "n/a";
        String selector = getRatingClassName();

        if (UtilValidate.isNotEmpty(selector)) {
            Elements selectedElements = selectFromElement(reviewData, getRatingClassName());
            Element ratingElement = selectedElements.first();
            userRating = extractNetworkUserRatingData(ratingElement);
        }

        return userRating;
    }

    protected abstract String extractNetworkUserRatingData(Element ratingElement);

    protected abstract String getRatingClassName();

    private String getNetworkMessageDate(Element reviewData) throws ItemNotFoundException {
        Elements elements = selectFromElement(reviewData, getMessageDateClassName());
        String networkMessageDate = elements.first().text();
        return networkMessageDate;
    }

    protected abstract String getMessageDateClassName();

    abstract protected String getUserNameFromUserInfo(Element userInfo);

    protected abstract String getLanguageFromHeadMetaData(Element headerElements);

    abstract protected String getUserIdFromUserInfo(Element userInfo);

    private String getReviewTextFromComment(Element reviewData) throws ItemNotFoundException {
        Elements reviewComments = selectFromElement(reviewData, getReviewCommentCssClassName());
        return reviewComments.first().text();
    }

    protected abstract String getReviewCommentCssClassName();

    private Elements getReviewData(Elements reviewContainer) throws ItemNotFoundException {
        return selectFromElement(reviewContainer, getReviewDataCssClassName());
    }

    protected abstract String getReviewDataCssClassName();

    private Elements getUserData(Elements reviewContainer) throws ItemNotFoundException {
        return selectFromElement(reviewContainer, getUserDataCssClassName());
    }

    protected abstract String getUserDataCssClassName();

    private Elements selectFromElement(Element toSelectFrom, String htmlIdentifierToSelect)
            throws ItemNotFoundException {
        Elements selectedElements = toSelectFrom.select(htmlIdentifierToSelect);

        checkIfAnElementWasFound(htmlIdentifierToSelect, selectedElements);

        return selectedElements;
    }

    private Elements selectFromElement(Elements toSelectFrom, String htmlIdentifierToSelect)
            throws ItemNotFoundException {
        Elements selectedElements = toSelectFrom.select(htmlIdentifierToSelect);

        checkIfAnElementWasFound(htmlIdentifierToSelect, selectedElements);

        return selectedElements;
    }

    private void checkIfAnElementWasFound(String htmlIdentifierToSelect, Elements selectedElements)
            throws ItemNotFoundException {

        if (selectedElements.isEmpty()) {
            throw new ItemNotFoundException("The Element: " + htmlIdentifierToSelect
                    + " could not be found. The Element ID is maybe out of date, check the HTML source from: "
                    + baseUrl);
        }
    }
}
