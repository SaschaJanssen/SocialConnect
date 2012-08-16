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
import org.social.core.query.QypeQuery;
import org.social.core.util.UtilDateTime;

public class QypeConnection extends AbstractConnection implements SocialNetworkConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean anyMoreNewMessages;

    public QypeConnection() {
        super();
        anyMoreNewMessages = true;
    }

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        QypeQuery qypeQuery = (QypeQuery) query;

        List<JSONObject> resultList = new ArrayList<JSONObject>();
        String nextPageUrl = query.constructQuery();

        do {
            if (logger.isDebugEnabled()) {
                logger.debug("Qype GET Request: " + nextPageUrl);
            }

            String response = readDataFromUrl(nextPageUrl, "");

            JSONObject json = null;
            try {
                json = (JSONObject) JSONSerializer.toJSON(response);
            } catch (JSONException je) {
                logger.error(je.getMessage());
                return resultList;
            }


            if(hasResultError(json)) {
                break;
            }

            if (json.containsKey("results")) {
                resultList.addAll(addToResultList(json.getJSONArray("results"), qypeQuery.getSince()));
            }

            nextPageUrl = getNextRequestUrl(json);
        } while (anyMoreNewMessages && nextPageUrl != null && !nextPageUrl.isEmpty());

        return resultList;
    }

    private boolean hasResultError(JSONObject json) {
        boolean hasError = false;

        if (json.containsKey("status")) {
            JSONObject status = json.getJSONObject("status");
            if (status.containsKey("error")) {
                logger.error("The following exception occurd during the Qype request: " + status.getString("error")
                        + " - " + status.getString("code"));
                hasError = true;
            }
        }

        return hasError;
    }

    private String getNextRequestUrl(JSONObject json) {
        String nextPageUrl = null;
        if (json.containsKey("links")) {
            JSONArray paging = json.getJSONArray("links");
            for (Object object : paging) {
                JSONObject jo = (JSONObject) object;
                if (jo.containsKey("rel") && "next".equals(jo.getString("rel"))) {
                    nextPageUrl = jo.getString("rel");
                    return nextPageUrl;
                }

            }
        }
        return nextPageUrl;
    }

    private List<JSONObject> addToResultList(JSONArray jsonArray, String lastNetworkAccess) {
        List<JSONObject> resultList = new ArrayList<JSONObject>();

        for (Object object : jsonArray) {
            JSONObject jo = (JSONObject) object;
            JSONObject review = jo.getJSONObject("review");

            String created = review.getString("created");
            boolean isMessageYounger = UtilDateTime.isMessageDateBeforeLastNetworkAccess(
                    UtilDateTime.toTimestamp(created), Timestamp.valueOf(lastNetworkAccess));
            if (isMessageYounger) {
                anyMoreNewMessages = false;
                return resultList;
            }

            resultList.add(review);
        }
        return resultList;
    }
}
