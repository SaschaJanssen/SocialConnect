package org.social.core.network.connection;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.query.Query;

public class FacebookConnection extends AbstractConnection implements SocialNetworkConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FacebookConnection() {
        super();
    }

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        String nextPageUrl = query.constructQuery();

        do {
            if (logger.isDebugEnabled()) {
                logger.debug("Facebook GET Request: " + nextPageUrl);
            }

            String response = readDataFromUrl(nextPageUrl, query.getLanguage());

            JSONObject json = null;
            try {
                json = (JSONObject) JSONSerializer.toJSON(response);
            } catch (JSONException je) {
                logger.error(je.getMessage());
                continue;
            }

            if (hasResultError(json)) {
                break;
            }

            if (json.containsKey("data")) {
                resultList.addAll(addToResultList(json.getJSONArray("data")));
            }

            nextPageUrl = getNextRequestUrl(json);
        } while (nextPageUrl != null && !nextPageUrl.isEmpty());

        return resultList;
    }

    private boolean hasResultError(JSONObject json) {
        boolean hasError = false;

        if (json.containsKey("error")) {
            JSONObject error = json.getJSONObject("error");
            logger.error("The following excpetion occurd during the Facebook request: " + error.getString("type") + " - "
                    + error.getString("message"));

            hasError = true;
        }

        return hasError;
    }

    private String getNextRequestUrl(JSONObject json) {
        String nextPageUrl = null;
        if (json.containsKey("paging")) {
            JSONObject paging = json.getJSONObject("paging");
            if (paging.containsKey("next")) {
                nextPageUrl = paging.getString("next");
            }
        }
        return nextPageUrl;
    }

    private List<JSONObject> addToResultList(JSONArray jsonArray) {
        List<JSONObject> resultList = new ArrayList<JSONObject>();

        for (Object object : jsonArray) {
            JSONObject jo = (JSONObject) object;
            resultList.add(jo);
        }
        return resultList;
    }
}
