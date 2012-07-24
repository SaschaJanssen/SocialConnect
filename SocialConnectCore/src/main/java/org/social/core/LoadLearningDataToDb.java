package org.social.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.entity.domain.LearningData;
import org.social.core.entity.helper.LearningDAO;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilValidate;

public class LoadLearningDataToDb {

	private static Logger logger = LoggerFactory.getLogger(LoadLearningDataToDb.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadLearningDataToDb loader = new LoadLearningDataToDb();
		loader.loadSentimentData();
	}

	private void loadSentimentData() {
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
			logger.error("", e);
		} finally {
			try {
				if (bufferedFileReader != null) {
					bufferedFileReader.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}

		List<LearningData> learningData = new ArrayList<LearningData>();
		for (String string : lr) {
			String[] split = string.split("§");

			LearningData ld = new LearningData();
			ld.setClassificationTypeId("SENTIMENT");
			ld.setLearningData(split[0]);
			ld.setLastUpdatedTs(UtilDateTime.nowTimestamp());
			ld.setClassificationId(split[1]);

			learningData.add(ld);
		}

		LearningDAO learningDao = new LearningDAO();
		learningDao.storeLearningData(learningData);
	}

}
