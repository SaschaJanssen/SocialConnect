package org.social.core.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.YelpSocialCrawler;
import org.social.core.query.YelpQuery;
import org.social.core.util.UtilValidate;

public class YelpConnection extends SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public YelpConnection(Customers customer) {
		super(customer);
		this.customer = customer;

		getCustomersKeywords(Networks.YELP.getName());
	}

	@Override
	public List<Messages> fetchMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch information from YELP for customer: " + this.customer.getCustomerId());
		}

		YelpQuery query = buildQueryFromKeywords();

		List<Messages> resultList = new ArrayList<Messages>();
		if (UtilValidate.isEmpty(query.getEndpoint())) {
			return resultList;
		}

		YelpSocialCrawler crawler = new YelpSocialCrawler(query.getSearchUrl(), query.constructQuery());

		resultList = crawler.crawl(customer);
		return resultList;
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
