package org.social.core.entity.helper;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Keywords;

public class KeywordHelper {

	/**
	 * Returns a list with keywords for a specific customer.
	 */
	public List<Keywords> getMappedKeywordByCustomerAndNetwork(Long customerId, String networkId) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		Query keywordQuery = session.createQuery("from Keywords kw where id.customerId=" + customerId + " and " + " id.networkId='" + networkId + "'");

		List<Keywords> keywordsForCustomer = queryForList(keywordQuery);
		session.getTransaction().commit();

		return keywordsForCustomer;

	}

	private List<Keywords> queryForList(Query keywordQuery) {
		@SuppressWarnings("unchecked")
		List<Keywords> mentionesKeys = Collections.checkedList(keywordQuery.list(), Keywords.class);
		return mentionesKeys;
	}

}
