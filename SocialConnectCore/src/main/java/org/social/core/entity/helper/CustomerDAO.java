package org.social.core.entity.helper;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Customers;

public class CustomerDAO {

	public List<Customers> getAllCustomersAndKeywords() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Customers> customerList = getCustomers(session);

		session.getTransaction().commit();

		return customerList;
	}

	public void updateCustomerNetworkAccess(Customers customer, Timestamp newTimestamp) {
		customer.setLastNetworkdAccess(newTimestamp);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.update(customer);

		session.getTransaction().commit();
	}

	private List<Customers> getCustomers(Session session) {
		@SuppressWarnings("unchecked")
		List<Customers> customerList = Collections.checkedList(session.createQuery("from Customers").list(),
				Customers.class);
		return customerList;
	}
}
