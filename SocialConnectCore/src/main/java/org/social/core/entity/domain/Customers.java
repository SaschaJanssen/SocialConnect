package org.social.core.entity.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.social.core.util.UtilDateTime;

@Entity
@Table(name = "CUSTOMERS")
public class Customers {

	private Long customerId;
	private Timestamp lastNetworkdAccess;
	private Timestamp createdTs;
	private Timestamp lastUpdatedTs;

	private Set<Keywords> keywords = new HashSet<Keywords>();

	public Customers() {
		this.createdTs = UtilDateTime.nowTimestamp();
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "LAST_NETWORK_ACCESS")
	public Timestamp getLastNetworkdAccess() {
		return lastNetworkdAccess;
	}

	public void setLastNetworkdAccess(Timestamp lastNetworkdAccess) {
		this.lastNetworkdAccess = lastNetworkdAccess;
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

	@OneToMany(targetEntity = Keywords.class, mappedBy = "customerId", fetch = FetchType.LAZY)
	public Set<Keywords> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keywords> keywords) {
		this.keywords = keywords;
	}

}
