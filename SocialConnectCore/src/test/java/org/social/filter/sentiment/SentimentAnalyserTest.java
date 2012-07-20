package org.social.filter.sentiment;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.Classification;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.filter.sentiment.SentimentAnalyser;
import org.social.core.util.UtilLucene;

public class SentimentAnalyserTest {

	@Before
	public void setUp() {
		Classifier<String, String> classifier = BayesClassifier.getInstance();
		List<String> t = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36),
				"I want a five guys burger and done cajun fries so damn bad");
		classifier.learn(Classification.NEGATIVE.getName(), t);
	}

	@Test
	public void testSentiment() {
		SentimentAnalyser analyser = SentimentAnalyser.getInstance();

		Messages m = new Messages();
		m.setMessage("I want a five guys burger and done cajun fries so damn bad");

		FilteredMessageList fm = new FilteredMessageList();
		fm.addToPositivList(m);

		FilteredMessageList fml = analyser.sentiment(fm);
		assertEquals(Classification.NEGATIVE.getName(), fml.getPositivList().get(0).getSentimentId());
	}

}
