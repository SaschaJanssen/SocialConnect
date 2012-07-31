package org.social.core.network.connection;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.social.core.query.Query;
import org.social.core.util.UtilProperties;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;

public class FacebookConnection implements SocialNetworkConnection {

	private FacebookClient fbClient = null;
	private String MY_ACCESS_TOKEN = null;

	public FacebookConnection() {
		loadProperties();
		fbClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
	}

	@Override
	public List<JSONObject> getRemoteData(Query query) {
		List<JSONObject> resultList = new ArrayList<JSONObject>();

		Connection<JsonObject> searchResult = fbClient.fetchConnection(query.constructQuery(), JsonObject.class);
		resultList.addAll(addToResultList(searchResult));
		String nextPageUrl = searchResult.getNextPageUrl();

		while (nextPageUrl != null && !nextPageUrl.isEmpty()) {
			searchResult = fbClient.fetchConnectionPage(nextPageUrl, JsonObject.class);
			resultList.addAll(addToResultList(searchResult));

			nextPageUrl = searchResult.getNextPageUrl();
		}

		return resultList;
	}

	private List<JSONObject> addToResultList(Connection<JsonObject> searchResult) {
		List<JSONObject> resultList = new ArrayList<JSONObject>();
		for (JsonObject object : searchResult.getData()) {
			JSONObject jo = (JSONObject) JSONSerializer.toJSON(object.toString());
			resultList.add(jo);
		}
		return resultList;
	}

	private void loadProperties() {
		MY_ACCESS_TOKEN = UtilProperties.getPropertyValue("conf/fb.properties", "token");
	}
}
