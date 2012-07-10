package org.social.core.entity.helper;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Customers;

public class CustomerHelper {

	public List<Customers> getAllCustomersAndKeywords() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Customers> customerList = getCustomers(session);

		session.getTransaction().commit();

		return customerList;
	}

	private List<Customers> getCustomers(Session session) {
		@SuppressWarnings("unchecked")
		List<Customers> customerList = Collections.checkedList(session.createQuery("from Customers").list(),
				Customers.class);
		return customerList;
	}
}
