package org.social.core.entity.helper;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.social.core.entity.domain.LearningData;

public class LearningDAO extends AbstractDAO {

	public List<LearningData> getLearningData() {
		Session session = super.beginAndGetSession();

		List<LearningData> learningDataList = getLearningData(session);

		super.commitSession(session);

		return learningDataList;
	}

	private List<LearningData> getLearningData(Session session) {
		@SuppressWarnings("unchecked")
		List<LearningData> learningDataList = Collections.checkedList(session.createQuery("from LearningData").list(),
				LearningData.class);
		return learningDataList;
	}

	public void storeLearningData(List<LearningData> learningData) {
		Session session = super.beginAndGetSession();

		for (LearningData data : learningData) {
			session.save(data);
		}

		super.commitSession(session);
	}
}
