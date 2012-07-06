package org.social.entity.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "KEYWORDS")
@IdClass(KeywordsId.class)
public class Keywords {
	private String keywordTypeId;
	private String keyword;
	private Long customerId;
	private String networkId;
	private Timestamp createdTs;
	private Timestamp lastUpdatedTs;

	@Id
	@Column(name = "KEYWORD_TYPE_ID")
	public String getKeywordTypeId() {
		return keywordTypeId;
	}

	public void setKeywordTypeId(String keywordTypeId) {
		this.keywordTypeId = keywordTypeId;
	}

	@Column(name = "KEYWORD")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Id
	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Id
	@Column(name = "NETWORK_ID")
	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	@Column(name = "CREATED_TS")
	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	@Column(name = "LAST_UPDATED_TS")
	public Timestamp getLastUpdatedTs() {
		return lastUpdatedTs;
	}

	public void setLastUpdatedTs(Timestamp lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}

}
