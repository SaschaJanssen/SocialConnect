package org.social.core.network.connection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.query.Query;
import org.social.core.util.UtilDateTime;

public class FoursquareConnection extends AbstractConnection implements SocialNetworkConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FoursquareConnection() {
        super();
    }

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        List<JSONObject> resultMessages = new ArrayList<JSONObject>();
        String constructedQuery = query.constructQuery();

        if (logger.isDebugEnabled()) {
            logger.debug("Foursquare GET Request: " + constructedQuery);
        }

        String responseBody = readDataFromUrl(constructedQuery, "");

        JSONObject json = null;
        try {
            json = (JSONObject) JSONSerializer.toJSON(responseBody);
        } catch (JSONException je) {
            logger.error(je.getMessage());
            return resultMessages;
        }

        if (json.containsKey("response")) {
            resultMessages.addAll(extractTips(json.getJSONObject("response"), query.getSince()));
        }

        return resultMessages;
    }

    private List<JSONObject> extractTips(JSONObject jsonObject, String lastUserAccess) {
        List<JSONObject> resultMessages = new ArrayList<JSONObject>();

        Timestamp lastUserAccessTs = UtilDateTime.stirngToTimestamp(lastUserAccess);

        JSONObject tips = jsonObject.getJSONObject("tips");
        JSONArray tipItems = tips.getJSONArray("items");

        for (Object tip : tipItems) {
            JSONObject joTip = (JSONObject) tip;

            String createdAt = joTip.getString("createdAt");
            Timestamp createdAtTs = UtilDateTime.toTimestamp(createdAt);

            boolean isMessageBefore = UtilDateTime.isMessageDateBeforeLastNetworkAccess(createdAtTs, lastUserAccessTs);
            if (isMessageBefore) {
                break;
            }

            resultMessages.add(joTip);
        }

        return resultMessages;
    }
}
