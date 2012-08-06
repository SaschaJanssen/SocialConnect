package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class ZagatQuery extends Query {

	private final String searchUrl = "http://www.zagat.com";
	private final String postFix = "/reviews";
	private String endpoint;
	private String since;

	public ZagatQuery(CustomerNetworkKeywords customerNetworkKeywords) {
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

	@Override
	public String getSearchUrl() {
		return searchUrl;
	}

	public String getSince() {
		return since;
	}

	@Override
	public void setSince(String since) {
		this.since = since;
	}

}
