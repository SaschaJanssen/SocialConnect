package org.social.core.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.network.crawler.OpenTableSocialCrawler;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.query.OpenTableQuery;
import org.social.core.util.UtilValidate;

public class OpenTableConnection extends SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public OpenTableConnection(Customers customer) {
		super(customer);
		this.customer = customer;

		getCustomersKeywords(Networks.OPENTABLE.getName());
	}

	@Override
	public List<Messages> fetchMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch information from OpenTable for customer: " + this.customer.getCustomerId());
		}

		List<Messages> resultList = new ArrayList<Messages>();
		OpenTableQuery query = buildQueryFromKeywords();
		if (UtilValidate.isEmpty(query.getEndpoint())) {
			return resultList;
		}
		SocialCrawler crawler = new OpenTableSocialCrawler(query.getSearchUrl(), query.constructQuery());

		resultList = crawler.crawl(customer);
		return resultList;
	}

	private OpenTableQuery buildQueryFromKeywords() {
		OpenTableQuery openTableQuery = new OpenTableQuery(super.customerNetworkKeywords);

		Timestamp sinceTs = this.customer.getLastNetworkdAccess();
		if(sinceTs != null) {
			String since = sinceTs.toString();
			openTableQuery.setSince(since);
		}

		return openTableQuery;
	}
}
