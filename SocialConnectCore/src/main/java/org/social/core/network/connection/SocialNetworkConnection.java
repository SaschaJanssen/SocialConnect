package org.social.core.network.connection;

import java.util.List;

import org.social.core.query.Query;

import net.sf.json.JSONObject;

public interface SocialNetworkConnection {
	public List<JSONObject> getRemoteData(Query query);
}
