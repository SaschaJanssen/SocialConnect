package org.social.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.LearningData;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilValidate;

public class LoadLearningDataToDb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File fi = new File("src/test/resources/sentimentLearningTestData");
		List<String> lr = new ArrayList<String>();
		BufferedReader bufferedFileReader = null;
		try {
			bufferedFileReader = new BufferedReader(new FileReader(fi));
			String lineInFile;
			while ((lineInFile = bufferedFileReader.readLine()) != null) {
				if (UtilValidate.isNotEmpty(lineInFile)) {
					lr.add(lineInFile);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedFileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		SessionFactory s = HibernateUtil.getSessionFactory();

		Session session = s.getCurrentSession();

		session.beginTransaction();
		for (String string : lr) {
			String[] split = string.split("§");

			LearningData ld = new LearningData();
			ld.setClassificationTypeId("SENTIMENT");
			ld.setLearningData(split[0]);
			ld.setLastUpdatedTs(UtilDateTime.nowTimestamp());
			ld.setClassificationId(split[1]);

			session.save(ld);

		}

		session.getTransaction().commit();

	}

}
