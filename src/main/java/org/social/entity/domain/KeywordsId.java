package org.social.entity.domain;

import java.io.Serializable;

public class KeywordsId implements Serializable {

	private static final long serialVersionUID = 8926212557890357866L;

	private String keywordTypeId;
	private Long customerId;
	private String networkId;

	KeywordsId() {
	}

	public KeywordsId(String keywordTypeId, Long customerId, String networkId) {
		this.keywordTypeId = keywordTypeId;
		this.customerId = customerId;
		this.networkId = networkId;
	}

	public String getKeywordTypeId() {
		return keywordTypeId;
	}

	public void setKeywordTypeId(String keywordTypeId) {
		this.keywordTypeId = keywordTypeId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((keywordTypeId == null) ? 0 : keywordTypeId.hashCode());
		result = prime * result + ((networkId == null) ? 0 : networkId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeywordsId other = (KeywordsId) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (keywordTypeId == null) {
			if (other.keywordTypeId != null)
				return false;
		} else if (!keywordTypeId.equals(other.keywordTypeId))
			return false;
		if (networkId == null) {
			if (other.networkId != null)
				return false;
		} else if (!networkId.equals(other.networkId))
			return false;
		return true;
	}

}
