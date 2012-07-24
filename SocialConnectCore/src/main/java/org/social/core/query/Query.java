package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public abstract class Query {

	private final CustomerNetworkKeywords networkKeywords;

	protected Query(CustomerNetworkKeywords networkKeywords) {
		this.networkKeywords = networkKeywords;
	}

	abstract public String constructQuery();
}
