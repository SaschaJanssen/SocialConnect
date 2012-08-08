package org.social.core.network.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.social.core.util.UtilValidate;

public class TwitterConnection implements SocialNetworkConnection {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpClient httpClient;

	public TwitterConnection() {
		httpClient = new DefaultHttpClient();

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
		List<JSONObject> resultMessages = new ArrayList<JSONObject>();
		String constructedQuery = query.constructQuery();

		while (true) {
			if (logger.isDebugEnabled()) {
				logger.debug("Twitter GET Request: " + constructedQuery);
			}

			String responseBody = readDataFromUrl(constructedQuery);
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

}
