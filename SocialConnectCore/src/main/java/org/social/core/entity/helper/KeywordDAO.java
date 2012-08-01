package org.social.core.entity.helper;

import java.util.List;
import java.util.Set;

import org.social.core.entity.domain.Keywords;

public interface KeywordDAO {

	/**
	 * Returns a list with keywords for a specific customer.
	 */
	public abstract List<Keywords> getMappedKeywordByCustomerAndNetwork(Long customerId, String networkId);

	public abstract Set<String> getUserNetworks(Long customerId);

}