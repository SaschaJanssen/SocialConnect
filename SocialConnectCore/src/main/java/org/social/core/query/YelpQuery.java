package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilValidate;

public class YelpQuery extends Query {

	private final String searchUrl = "https://api.yelp.com/v2/business/";

	private String id;
	private String language;
	private String since;

	public YelpQuery(CustomerNetworkKeywords customerNetworkKeywords) {
		super(customerNetworkKeywords);

		setId(customerNetworkKeywords.getPage());
	}

	@Override
	public String constructQuery() {
		StringBuilder queryBuilder = new StringBuilder(this.searchUrl);
		queryBuilder.append(id);
		if (UtilValidate.isNotEmpty(this.language)) {
			queryBuilder.append("?");
			queryBuilder.append("lang=");
			queryBuilder.append(language);
		}
		return queryBuilder.toString();
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getSearchUrl() {
		return this.searchUrl;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}



}
