package org.social.core.network;

import java.util.List;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;

public abstract class SocialNetworkKraken {

    protected CustomerNetworkKeywords customerNetworkKeywords;
    protected Customers customer;

    private KeywordDAO keywordDao;

    protected SocialNetworkKraken(Customers customer, KeywordDAO keywordDao) {
        this.customer = customer;
        this.keywordDao = keywordDao;
    }

    public abstract FilteredMessageList fetchAndCraftMessages();

    public CustomerNetworkKeywords getCustomerNetworkKeywords() {
        return customerNetworkKeywords;
    }

    protected void getCustomersKeywords(String networkName) {
        Long customerId = customer.getCustomerId();

        List<Keywords> keywords = keywordDao.getMappedKeywordByCustomerAndNetwork(customerId, networkName);
        customerNetworkKeywords = new CustomerNetworkKeywords(keywords);
    }

    /**
     * Categories messages after reliability and there sentiment.
     * 
     * @param messagesToCraft
     * @return
     */
    protected FilteredMessageList reliabilityAndSentimentMessages(List<Messages> messagesToCraft) {
        DataCrafter crafter = new DataCrafter(messagesToCraft);
        return crafter.reliabilityAndSentimentCrafter(customerNetworkKeywords);
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