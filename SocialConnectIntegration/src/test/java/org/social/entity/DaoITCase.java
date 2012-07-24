package org.social.entity;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialITCase;
import org.social.core.constants.Networks;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.CustomerDAO;

public class DaoITCase extends SocialITCase {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SessionFactory sessionFactory;

	public DaoITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testDBWrite() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		try {
			Transaction tx = session.beginTransaction();
			create(session);
			tx.commit();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Test
	public void testDB_Read() throws Exception {
		Session session = sessionFactory.openSession();

		List<Messages> result = session.createQuery("from Messages").list();
		assertTrue(!result.isEmpty());

		if (logger.isDebugEnabled()) {
			for (Messages messag : (List<Messages>) result) {
				logger.debug(messag.toString());
			}
		}

		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	private void create(Session session) {
		Messages n = new Messages(Networks.TWITTER.getName());
		n.setMessage("blaaaaaaaaaaa");
		n.setLanguage("de");
		n.setCustomerId(new Long(1));
		session.save(n);
	}

	@Test
	public void testCustomerDaoWrite() throws Exception {
		CustomerDAO customerDao = new CustomerDAO();
		List<Customers> custList = customerDao.getAllCustomersAndKeywords();

		assertNotNull(custList);
		assertTrue(custList.size() > 0);
	}

}
