package org.social.core.entity;

import java.io.File;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilProperties;

public class HibernateUtil {

	private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		String hibernateConfig = UtilProperties.getPropertyValue("conf/social.properties", "hibernate-config");

		try {
			// Create the SessionFactory from hibernate.cfg.xml
			File hibernateConfigurationFile = new File("conf/" + hibernateConfig);
			Configuration configuration = new Configuration().configure(hibernateConfigurationFile);

			Properties configurationProperties = configuration.getProperties();
			ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
					.applySettings(configurationProperties);
			ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
