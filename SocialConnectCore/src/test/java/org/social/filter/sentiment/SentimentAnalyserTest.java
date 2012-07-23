package org.social.filter.sentiment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.filter.sentiment.SentimentAnalyser;
import org.social.core.util.UtilLucene;
import org.social.core.util.UtilValidate;

public class SentimentAnalyserTest {

	private SentimentAnalyser analyser;

	@Before
	public void setUp() {
		Classifier<String, String> classifier = BayesClassifier.getInstance();
		File fi = new File("src/test/resources/sentimentLearningTestData");
		BufferedReader bufferedFileReader = null;
		try {
			bufferedFileReader = new BufferedReader(new FileReader(fi));
			String lineInFile;
			while ((lineInFile = bufferedFileReader.readLine()) != null) {
				if (UtilValidate.isNotEmpty(lineInFile)) {
					String[] split = lineInFile.split("§");

					List<String> t = UtilLucene.ngramString(split[0]);
					classifier.learn(split[1], t);
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

		analyser = SentimentAnalyser.getInstance();
	}

	@Test
	public void testSentiment() {

		Messages m = new Messages();
		m.setMessage("I want a five guys burger and done cajun fries so damn bad");

		List<Messages> fm = new ArrayList<Messages>();
		fm.add(m);

		List<Messages> fml = analyser.sentiment(fm);
		assertEquals(Classification.POSITIVE.getName(), fml.get(0).getSentimentId());
	}

	@Test
	public void testSentimentMultibleMessages() throws Exception {
		File fi = new File("src/test/resources/sentimentTestData");
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

		} finally {
			bufferedFileReader.close();
		}

		List<Messages> fm = new ArrayList<Messages>();

		for (String string : lr) {
			Messages m = new Messages();
			m.setMessage(string);

			fm.add(m);
		}

		analyser.sentiment(fm);

		for (Messages msg : fm) {
			assertFalse(Classification.NOT_CLASSIFIED.isClassification(msg.getSentimentId()));
		}

	}

}
