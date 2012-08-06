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
import org.social.core.network.crawler.BaseCrawler;
import org.social.core.network.crawler.OpenTableSocialCrawler;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.query.OpenTableQuery;
import org.social.core.query.Query;

public class OpenTableKraken extends SocialNetworkKraken {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BaseCrawler crawler;

	public OpenTableKraken(Customers customer, KeywordDAO keywordDao, BaseCrawler crawler) {
		super(customer, keywordDao);
		this.customer = customer;
		this.crawler = crawler;

		getCustomersKeywords(Networks.OPENTABLE.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch information from OpenTable for customer: " + this.customer.getCustomerId());
		}

		Query query = buildQueryFromKeywords();

		SocialCrawler otCrawler = new OpenTableSocialCrawler(this.crawler, query.getSearchUrl(),
				query.constructQuery());

		List<Messages> resultMessages = new ArrayList<Messages>();
		resultMessages = otCrawler.crawl(customer);

		return sentimentMessages(resultMessages);
	}

	private Query buildQueryFromKeywords() {
		Query openTableQuery = new OpenTableQuery(super.customerNetworkKeywords);

		Timestamp sinceTs = this.customer.getLastNetworkdAccess();
		if (sinceTs != null) {
			String since = sinceTs.toString();
			openTableQuery.setSince(since);
		}

		return openTableQuery;
	}
}
