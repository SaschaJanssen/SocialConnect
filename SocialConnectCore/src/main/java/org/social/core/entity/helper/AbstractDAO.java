package org.social.core.entity.helper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.social.core.entity.HibernateUtil;

public abstract class AbstractDAO {
	protected Session beginAndGetSession() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		return session;
	}

	protected void commitSession(Session startedSession) {
		startedSession.getTransaction().commit();
	}
}
