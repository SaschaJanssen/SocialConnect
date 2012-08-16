package org.social.core.network.connection;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.query.Query;

public class TwitterConnection extends AbstractConnection implements SocialNetworkConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TwitterConnection() {
        super();
    }

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        List<JSONObject> resultMessages = new ArrayList<JSONObject>();
        String constructedQuery = query.constructQuery();

        while (true) {
            if (logger.isDebugEnabled()) {
                logger.debug("Twitter GET Request: " + constructedQuery);
            }

            String responseBody = readDataFromUrl(constructedQuery, "");
            if (responseBody.isEmpty()) {
                break;
            }

            JSONObject json = null;
            try {
                json = (JSONObject) JSONSerializer.toJSON(responseBody);
            } catch (JSONException je) {
                logger.error(je.getMessage());
                continue;
            }

            if (json.containsKey("results")) {
                resultMessages.add(json);
            }

            if (json.containsKey("next_page")) {
                constructedQuery = query.getSearchUrl() + json.getString("next_page");
            } else {
                break;
            }
        }
        return resultMessages;
    }
}
