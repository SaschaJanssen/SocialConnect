package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class YelpQuery extends Query {

	private final String searchUrl = "https://www.yelp.com";

	private String endpoint;
	private String since;

	public YelpQuery(CustomerNetworkKeywords customerNetworkKeywords) {
		super(customerNetworkKeywords);

		setEndpoint(customerNetworkKeywords.getPage());
	}

	@Override
	public String constructQuery() {
		// Not implemented
		return null;
	}

	private void setEndpoint(String id) {
		this.endpoint = id;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public String getSearchUrl() {
		return this.searchUrl;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}



}
