package org.social.core.entity.helper;

import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Messages;
import org.social.core.util.UtilDateTime;

public class MessageHelper {

	public void storeMessages(List<Messages> messageDataList) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		for (Messages messageData : messageDataList) {
			session.save(messageData);
		}

		session.getTransaction().commit();
	}

	public void updateMessages(List<Messages> messageDataList) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		for (Messages messageData : messageDataList) {
			messageData.setLastUpdatedTs(UtilDateTime.nowTimestamp());
			session.update(messageData);
		}

		session.getTransaction().commit();

	}
}
