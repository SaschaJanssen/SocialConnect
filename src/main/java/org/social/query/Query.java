package org.social.query;

public abstract class Query {

	private Long customerId;

	public Query(Long customerId) {
		this.customerId = customerId;
	}

	abstract public String constructQuery();

	public Long getCustomerId() {
		return this.customerId;
	}
}
