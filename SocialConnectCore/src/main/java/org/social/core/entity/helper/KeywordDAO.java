package org.social.core.entity.helper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.social.core.entity.domain.Keywords;

public class KeywordDAO extends AbstractDAO{

	/**
	 * Returns a list with keywords for a specific customer.
	 */
	public List<Keywords> getMappedKeywordByCustomerAndNetwork(Long customerId, String networkId) {
		Session session = super.beginAndGetSession();

		Query keywordQuery = session.createQuery("from Keywords kw where id.customerId=" + customerId + " and " + " id.networkId='" + networkId + "'");
		List<Keywords> keywordsForCustomer = queryForList(keywordQuery);

		super.commitSession(session);
		return keywordsForCustomer;
	}

	private List<Keywords> queryForList(Query keywordQuery) {
		@SuppressWarnings("unchecked")
		List<Keywords> mentionesKeys = Collections.checkedList(keywordQuery.list(), Keywords.class);
		return mentionesKeys;
	}

	private List<String> queryForStringList(Query keywordQuery) {
		@SuppressWarnings("unchecked")
		List<String> mentionesKeys = Collections.checkedList(keywordQuery.list(), String.class);
		return mentionesKeys;
	}

	public Set<String> getUserNetworks(Long customerId) {
		Session session = super.beginAndGetSession();

		Query keywordQuery = session.createQuery("select distinct networkId from Keywords kw where kw.customerId=" + customerId + "");

		List<String> netwroks = queryForStringList(keywordQuery);
		super.commitSession(session);

		Set<String> networksForCustomer = new HashSet<String>();
		networksForCustomer.addAll(netwroks);

		return networksForCustomer;
	}

}
