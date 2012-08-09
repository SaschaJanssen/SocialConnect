package org.social.core.network.connection;

import java.util.List;

import net.sf.json.JSONObject;

import org.social.core.query.Query;

public interface SocialNetworkConnection {
    public List<JSONObject> getRemoteData(Query query);
}
