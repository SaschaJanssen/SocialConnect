package org.social.core.entity.helper;

import org.hibernate.Session;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Messages;

public class MessageDAO {

	public void storeMessages(FilteredMessageList filteredMessageDataList) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		for (Messages messageData : filteredMessageDataList.getPositivList()) {
			session.save(messageData);
		}

		for (Messages messageData : filteredMessageDataList.getNegativeList()) {
			session.save(messageData);
		}

		session.getTransaction().commit();
	}
}
