package org.social.core.entity.helper;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.domain.Customers;

public class CustomerDAO extends AbstractDAO{

	public List<Customers> getAllCustomersAndKeywords() {
		Session session = super.beginAndGetSession();

		List<Customers> customerList = getCustomers(session);

		super.commitSession(session);
		return customerList;
	}

	public void updateCustomerNetworkAccess(Customers customer, Timestamp newTimestamp) {
		Session session = super.beginAndGetSession();

		customer.setLastNetworkdAccess(newTimestamp);
		session.update(customer);

		super.commitSession(session);
	}

	private List<Customers> getCustomers(Session session) {
		@SuppressWarnings("unchecked")
		List<Customers> customerList = Collections.checkedList(session.createQuery("from Customers").list(),
				Customers.class);
		return customerList;
	}
}
