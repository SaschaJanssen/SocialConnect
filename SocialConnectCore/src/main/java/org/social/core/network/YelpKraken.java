package org.social.core.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.exceptions.ItemNotFoundException;
import org.social.core.network.crawler.BaseCrawler;
import org.social.core.network.crawler.YelpSocialCrawler;
import org.social.core.query.Query;
import org.social.core.query.YelpQuery;

public class YelpKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaseCrawler crawler;

    public YelpKraken(Customers customer, KeywordDAO keywordDao, BaseCrawler crawler) {
        super(customer, keywordDao);
        this.customer = customer;
        this.crawler = crawler;

        getCustomersKeywords(Networks.YELP.getName());
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch information from YELP for customer: " + customer.getCustomerId());
        }

        Query query = buildQueryFromKeywords();

        YelpSocialCrawler yelpCrawler = new YelpSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery());

        List<Messages> resultMessages = new ArrayList<Messages>();
        try {
            resultMessages = yelpCrawler.crawl(customer);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return sentimentMessages(resultMessages);
    }

    private Query buildQueryFromKeywords() {
        Query yelpQuery = new YelpQuery(super.customerNetworkKeywords);

        Timestamp sinceTs = customer.getLastNetworkdAccess();
        if (sinceTs != null) {
            String since = sinceTs.toString();
            yelpQuery.setSince(since);
        }

        return yelpQuery;
    }
}
