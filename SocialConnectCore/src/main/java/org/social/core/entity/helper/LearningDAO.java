package org.social.core.entity.helper;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.LearningData;

public class LearningDAO {

	public List<LearningData> getLearningData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<LearningData> learningDataList = getLearningData(session);

		session.getTransaction().commit();

		return learningDataList;
	}

	private List<LearningData> getLearningData(Session session) {
		@SuppressWarnings("unchecked")
		List<LearningData> learningDataList = Collections.checkedList(session.createQuery("from LearningData").list(),
				LearningData.class);
		return learningDataList;
	}
}
