package org.social.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.util.UtilValidate;

public class FacebookQuery implements Query {

	// private final String searchUrl = "https://graph.facebook.com/search";
	private final String searchUrl = "search";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String direct;
	private String since;
	private String type;
	private String limit = "1500";

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	@Override
	public String constructQuery() {
		String encodedQuery = "";
		try {
			encodedQuery = URLEncoder.encode(this.direct, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Using not encoded query.", e);
			encodedQuery = this.direct;
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
