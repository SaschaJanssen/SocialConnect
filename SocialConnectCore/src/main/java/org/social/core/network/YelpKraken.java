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
import org.social.core.network.crawler.JsoupBaseCrwaler;
import org.social.core.network.crawler.YelpSocialCrawler;
import org.social.core.query.YelpQuery;

public class YelpKraken extends SocialNetworkKraken {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public YelpKraken(Customers customer, KeywordDAO keywordDao) {
		super(customer, keywordDao);
		this.customer = customer;

		getCustomersKeywords(Networks.YELP.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch information from YELP for customer: " + this.customer.getCustomerId());
		}

		YelpQuery query = buildQueryFromKeywords();

		YelpSocialCrawler crawler = new YelpSocialCrawler(new JsoupBaseCrwaler(), query.getSearchUrl(),
				query.constructQuery());

		List<Messages> resultMessages = new ArrayList<Messages>();
		resultMessages = crawler.crawl(customer);

		return sentimentMessages(resultMessages);
	}

	private YelpQuery buildQueryFromKeywords() {
		YelpQuery yelpQuery = new YelpQuery(super.customerNetworkKeywords);

		Timestamp sinceTs = this.customer.getLastNetworkdAccess();
		if (sinceTs != null) {
			String since = sinceTs.toString();
			yelpQuery.setSince(since);
		}

		return yelpQuery;
	}
}
