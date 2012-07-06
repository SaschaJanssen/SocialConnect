package org.social.entity.helper;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.entity.HibernateUtil;
import org.social.entity.domain.Customers;

public class CustomerHelper {

	public List<Customers> getAllCustomers() {
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

	public List<Customers> getAllCustomersAndKeywords() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Customers> customerList = getCustomers(session);
		/*for (Customers customer : customerList) {
			customer.getKeywords();
		}*/

		session.getTransaction().commit();

		return customerList;
	}
}
