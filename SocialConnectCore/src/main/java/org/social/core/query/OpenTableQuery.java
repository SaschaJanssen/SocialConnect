package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class OpenTableQuery extends Query {

	private final String searchUrl = "http://www.opentable.com";
	private final String postFix = "?tab=2";
	private String endpoint;

	public OpenTableQuery(CustomerNetworkKeywords customerNetworkKeywords) {
		super(customerNetworkKeywords);
		setEndpoint(customerNetworkKeywords.getPage());
	}

	@Override
	public String constructQuery() {
		return endpoint + postFix;
	}

	private void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

}
