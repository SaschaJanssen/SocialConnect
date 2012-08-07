package org.social.core.network.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.query.Query;
import org.social.core.query.QypeQuery;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilValidate;

public class QypeConnection implements SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpClient httpClient;
	private boolean anyMoreNewMessages;

	public QypeConnection() {
		httpClient = new DefaultHttpClient();
		anyMoreNewMessages = true;
		setHttpsProxy();
	}

	private void setHttpsProxy() {

		String host = System.getProperty("https.proxyHost");
		String portString = System.getProperty("https.proxyPort");

		if (UtilValidate.isNotEmpty(host) && UtilValidate.isNotEmpty(portString)) {
			int port = Integer.parseInt(portString);

			HttpHost proxy = new HttpHost(host, port);
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
	}

	@Override
	public List<JSONObject> getRemoteData(Query query) {
		QypeQuery qypeQuery = (QypeQuery) query;

		List<JSONObject> resultList = new ArrayList<JSONObject>();
		String nextPageUrl = query.constructQuery();

		do {
			String response = readDataFromUrl(nextPageUrl);

			JSONObject json = null;
			try {
				json = (JSONObject) JSONSerializer.toJSON(response);
			} catch (JSONException je) {
				logger.error(je.getMessage());
				return resultList;
			}

			if (json.containsKey("status")) {
				JSONObject status = json.getJSONObject("status");
				if (status.containsKey("error")) {
					logger.error("The following error occurd during the Qype request: " + status.getString("error")
							+ " - " + status.getString("code"));
					return resultList;
				}
			}

			if (json.containsKey("results")) {
				resultList.addAll(addToResultList(json.getJSONArray("results"), qypeQuery.getSince()));
			}

			nextPageUrl = getNextRequestUrl(json);
		} while (anyMoreNewMessages && nextPageUrl != null && !nextPageUrl.isEmpty());

		return resultList;
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

	private String readDataFromUrl(String queryUrl) {

		StringBuilder resultStringBuilder = null;
		BufferedReader in = null;
		try {
			HttpUriRequest req = new HttpGet(queryUrl);
			HttpResponse resp = httpClient.execute(req);

			in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			resultStringBuilder = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				resultStringBuilder.append(inputLine);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}

		return resultStringBuilder.toString();

	}

	private List<JSONObject> addToResultList(JSONArray jsonArray, String lastNetworkAccess) {
		List<JSONObject> resultList = new ArrayList<JSONObject>();

		for (Object object : jsonArray) {
			JSONObject jo = (JSONObject) object;
			JSONObject review = jo.getJSONObject("review");

			String created = review.getString("created");
			boolean isMessageYounger = UtilDateTime.isMessageYoungerThanLastNetworkAccess(
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
