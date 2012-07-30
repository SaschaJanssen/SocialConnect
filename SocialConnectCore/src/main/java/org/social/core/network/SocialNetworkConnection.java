package org.social.core.network;

import java.util.List;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;

public abstract class SocialNetworkConnection {

	protected CustomerNetworkKeywords customerNetworkKeywords;
	protected Customers customer;

	protected SocialNetworkConnection(Customers customer) {
		this.customer = customer;
	}

	public abstract FilteredMessageList fetchAndCraftMessages();

	public CustomerNetworkKeywords getCustomerNetworkKeywords() {
		return this.customerNetworkKeywords;
	}

	protected void getCustomersKeywords(String networkName) {
		KeywordDAO helper = new KeywordDAO();

		Long customerId = this.customer.getCustomerId();

		List<Keywords> keywords = helper.getMappedKeywordByCustomerAndNetwork(customerId, networkName);
		this.customerNetworkKeywords = new CustomerNetworkKeywords(keywords);
	}

	/**
	 * Categories messages after reliability and there sentiment.
	 *
	 * @param messagesToCraft
	 * @return
	 */
	protected FilteredMessageList reliabilityAndSentimentMessages(List<Messages> messagesToCraft) {
		DataCrafter crafter = new DataCrafter(messagesToCraft);
		return crafter.reliabilityAndSentimentCrafter(this.customerNetworkKeywords);
	}

	/**
	 * Categories messages only after there sentiment.
	 *
	 * @param messagesToCraft
	 * @return
	 */
	protected FilteredMessageList sentimentMessages(List<Messages> messagesToCraft) {
		DataCrafter crafter = new DataCrafter(messagesToCraft);
		return crafter.sentimentCrafter();
	}
}