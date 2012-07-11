package org.social.core.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilValidate;

public class FacebookQuery extends Query {

	// private final String searchUrl = "https://graph.facebook.com/search";
	private final String searchUrl = "search";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String query;
	private String since;
	private String type;
	private String limit = "1500";

	public FacebookQuery(CustomerNetworkKeywords networkKeywords) {
		super(networkKeywords);

		setQuery(networkKeywords.getQueryForNetwork());
	}

	private void setQuery(String query) {
		this.query = query;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSearchUrl() {
		return this.searchUrl;
	}

	@Override
	public String constructQuery() {
		String encodedQuery = "";
		try {
			encodedQuery = URLEncoder.encode(this.query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Using not encoded query.", e);
			encodedQuery = this.query;
		}

		StringBuilder queryBuilder = new StringBuilder(searchUrl);
		queryBuilder.append("?");
		queryBuilder.append("q=");
		queryBuilder.append(encodedQuery);
		queryBuilder.append("&type=");
		queryBuilder.append(UtilValidate.isNotEmpty(this.type) ? this.type : "post");
		queryBuilder.append("&since=");
		queryBuilder.append(since);
		queryBuilder.append("&limit=");
		queryBuilder.append(this.limit);

		return queryBuilder.toString();
	}

}
