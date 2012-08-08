package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilProperties;

public class FoursquareQuery extends Query {

	private final String searchUrl;
	private final String consumerId;

	private final String prefix = "/tips";

	private final String consumerSecret;
	private final String apiValidationDate;

	private String since;
	private String page;

	public FoursquareQuery(CustomerNetworkKeywords networkKeywords) {
		super(networkKeywords);

		consumerId = UtilProperties.getPropertyValue("conf/foursquare.properties", "client_id");
		consumerSecret = UtilProperties.getPropertyValue("conf/foursquare.properties", "client_secret");
		apiValidationDate = UtilProperties.getPropertyValue("conf/foursquare.properties", "api_validation_date");
		searchUrl = UtilProperties.getPropertyValue("conf/foursquare.properties", "search_url");

		setPage(networkKeywords.getPage());
	}

	private void setPage(String page) {
		this.page = page;
	}

	@Override
	public String getSearchUrl() {
		return this.searchUrl;
	}

	@Override
	public String getLanguage() {
		return "";
	}

	@Override
	public String constructQuery() {
		StringBuilder queryBuilder = new StringBuilder(searchUrl);

		queryBuilder.append(page);
		queryBuilder.append(prefix);
		queryBuilder.append("?");
		queryBuilder.append("sort=");
		queryBuilder.append("recent");
		queryBuilder.append("&");
		queryBuilder.append("v=");
		queryBuilder.append(apiValidationDate);
		queryBuilder.append("&");
		queryBuilder.append("client_secret=");
		queryBuilder.append(consumerSecret);
		queryBuilder.append("&");
		queryBuilder.append("client_id=");
		queryBuilder.append(consumerId);

		return queryBuilder.toString();
	}

	@Override
	public void setSince(String since) {
		this.since = since;
	}

	public String getSince() {
		return since;
	}
}
