package org.social.entity;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.constants.Networks;
import org.social.entity.domain.Messages;

public class DerbyTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		System.setProperty("derby.system.home", "target/runtime/derby/");

		sessionFactory = initHibernate();
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

		List result = session.createQuery("from Messages").list();
		assertTrue(!result.isEmpty());

		if (logger.isInfoEnabled()) {
			for (Messages messag : (List<Messages>) result) {
				logger.info(messag.toString());
			}
		}

		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	private void create(Session session) {
		Messages n = new Messages(Networks.TWITTER.toString());
		n.setMessage("blaaaaaaaaaaa");
		n.setLanguage("de");
		n.setCustomerId(new Long(1));
		session.save(n);
	}

	public SessionFactory initHibernate() throws Exception {
		URL hibernateConfigurationFile = ClassLoader.getSystemResource("hibernate-derby.cfg.xml");
		Configuration configuration = new Configuration().configure(hibernateConfigurationFile);

		logger.info("Connecting hibernate to URL=" + configuration.getProperty("hibernate.connection.url")
				+ " as user=" + configuration.getProperty("hibernate.connection.username"));

		Properties configurationProperties = configuration.getProperties();
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configurationProperties);
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory;
	}

}
