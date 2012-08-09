package org.social.core.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.QypeQuery;
import org.social.core.util.UtilDateTime;

public class QypeKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SocialNetworkConnection connection;

    public QypeKraken(Customers customer, KeywordDAO keywordDao, SocialNetworkConnection fbConnection) {
        super(customer, keywordDao);
        connection = fbConnection;
        getCustomersKeywords(Networks.QYPE.getName());
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch posts from Qype for customer: " + super.customer.getCustomerId());
        }

        QypeQuery query = buildQueryFromKeywords();

        List<JSONObject> searchResult = connection.getRemoteData(query);
        List<Messages> resultMessages = extractMessageData(searchResult);

        FilteredMessageList filteredResultMessages = super.sentimentMessages(resultMessages);

        return filteredResultMessages;
    }

    private QypeQuery buildQueryFromKeywords() {
        QypeQuery query = new QypeQuery(super.customerNetworkKeywords);

        Timestamp lastNetworkAccess = super.customer.getLastNetworkdAccess();

        if (lastNetworkAccess != null) {
            query.setSince(lastNetworkAccess.toString());

        }
        query.setLanguage("en");

        return query;
    }

    private List<Messages> extractMessageData(List<JSONObject> searchResult) {

        List<Messages> results = new ArrayList<Messages>();

        for (JSONObject object : searchResult) {
            Messages messageData = new Messages(Networks.QYPE.getName());

            messageData.setCustomerId(super.customer.getCustomerId());

            JSONArray links = object.getJSONArray("links");
            for (Object link : links) {
                JSONObject joLink = (JSONObject) link;
                if (joLink.containsKey("rel") && "http://schemas.qype.com/user".equals(joLink.getString("rel"))) {
                    String title = joLink.getString("title");
                    String href = joLink.getString("href");
                    String networkUserId = href.substring(href.lastIndexOf("/") + 1);

                    messageData.setNetworkUser(title);
                    messageData.setNetworkUserId(networkUserId);
                    break;
                }
            }

            String messageDate = object.getString("created");
            messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(messageDate));

            messageData.setMessage(object.getString("content_text"));
            messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());

            messageData.setLanguage(object.getString("language"));

            messageData.setReliabilityId(Classification.RELIABLE.getName());
            messageData.setNetworkUserRating(object.getString("rating"));
            results.add(messageData);
        }

        return results;
    }
}
