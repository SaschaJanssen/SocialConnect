package org.social.entity;

import java.net.URL;
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
import org.social.entity.domain.Messages;

public class DerbyTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		System.setProperty("derby.system.home", "target/runtime/derby/");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testDB() throws Exception {
		Session session = initHibernate();
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

	private void create(Session session) {
		Messages n = new Messages();
		n.setMessage("blaaaaaaaaaaa");
		n.setLanguage("de");
		n.setNetworkId("TWITTER");
		n.setCustomerId(new Long(1));
		n.setCraftedStateId("NOT_CRAFTED");
		session.save(n);
	}

	public Session initHibernate() throws Exception {
		URL hibernateConfigurationFile = ClassLoader.getSystemResource("hibernate-derby.cfg.xml");
		Configuration configuration = new Configuration().configure(hibernateConfigurationFile);

		logger.info("Connecting hibernate to URL=" + configuration.getProperty("hibernate.connection.url")
				+ " as user=" + configuration.getProperty("hibernate.connection.username"));

		Properties configurationProperties = configuration.getProperties();
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configurationProperties);
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory.getCurrentSession();
	}

}
