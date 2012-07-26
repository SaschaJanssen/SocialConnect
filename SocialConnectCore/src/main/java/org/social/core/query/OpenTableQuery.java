package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class OpenTableQuery extends Query {

	private final String searchUrl = "http://reviews.opentable.com";
	private final String postFix = "/reviews.htm";
	private String endpoint;
	private String since;

	public OpenTableQuery(CustomerNetworkKeywords customerNetworkKeywords) {
		super(customerNetworkKeywords);
		setEndpoint(customerNetworkKeywords.getPage());
	}

	@Override
	public String constructQuery() {
		return endpoint + postFix;
	}

	public String getEndpoint() {
		return endpoint;
	}

	private void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

}
