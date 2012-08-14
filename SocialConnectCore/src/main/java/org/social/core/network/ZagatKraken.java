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
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.network.crawler.ZagatSocialCrawler;
import org.social.core.query.Query;
import org.social.core.query.ZagatQuery;

public class ZagatKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BaseCrawler crawler;

    public ZagatKraken(Customers customer, KeywordDAO keywordDao, BaseCrawler crawler) {
        super(customer, keywordDao);
        this.customer = customer;
        this.crawler = crawler;

        getCustomersKeywords(Networks.ZAGAT.getName());
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch information from Zagat for customer: " + customer.getCustomerId());
        }

        Query query = buildQueryFromKeywords();

        SocialCrawler otCrawler = new ZagatSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery());

        List<Messages> resultMessages = new ArrayList<Messages>();
        try {
            resultMessages = otCrawler.crawl(customer);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return sentimentMessages(resultMessages);
    }

    private Query buildQueryFromKeywords() {
        Query query = new ZagatQuery(super.customerNetworkKeywords);

        Timestamp sinceTs = customer.getLastNetworkdAccess();
        if (sinceTs != null) {
            String since = sinceTs.toString();
            query.setSince(since);
        }

        return query;
    }
}
