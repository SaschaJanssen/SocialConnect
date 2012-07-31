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
import org.social.core.network.crawler.JsoupBaseCrwaler;
import org.social.core.network.crawler.OpenTableSocialCrawler;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.query.OpenTableQuery;

public class OpenTableConnection extends SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public OpenTableConnection(Customers customer) {
		super(customer);
		this.customer = customer;

		getCustomersKeywords(Networks.OPENTABLE.getName());
	}

	@Override
	public FilteredMessageList fetchAndCraftMessages() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch information from OpenTable for customer: " + this.customer.getCustomerId());
		}

		OpenTableQuery query = buildQueryFromKeywords();

		SocialCrawler crawler = new OpenTableSocialCrawler(new JsoupBaseCrwaler(), query.getSearchUrl(),
				query.constructQuery());

		List<Messages> resultMessages = new ArrayList<Messages>();
		resultMessages = crawler.crawl(customer);

		return sentimentMessages(resultMessages);
	}

	private OpenTableQuery buildQueryFromKeywords() {
		OpenTableQuery openTableQuery = new OpenTableQuery(super.customerNetworkKeywords);

		Timestamp sinceTs = this.customer.getLastNetworkdAccess();
		if (sinceTs != null) {
			String since = sinceTs.toString();
			openTableQuery.setSince(since);
		}

		return openTableQuery;
	}
}
