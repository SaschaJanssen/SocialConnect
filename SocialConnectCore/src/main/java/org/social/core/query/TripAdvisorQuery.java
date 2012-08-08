package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class TripAdvisorQuery extends Query {

	private final String searchUrl = "http://www.tripadvisor.com";

	private String endpoint;
	private String since;

	public TripAdvisorQuery(CustomerNetworkKeywords customerNetworkKeywords) {
		super(customerNetworkKeywords);

		setEndpoint(customerNetworkKeywords.getPage());
	}

	@Override
	public String constructQuery() {
		return this.endpoint;
	}

	private void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String getSearchUrl() {
		return this.searchUrl;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getEndpoint() {
		return endpoint;
	}

	@Override
	public String getLanguage() {
		return null;
	}
}
