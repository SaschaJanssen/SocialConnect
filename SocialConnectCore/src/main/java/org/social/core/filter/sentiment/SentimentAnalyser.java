package org.social.core.filter.sentiment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.util.UtilLucene;

public class SentimentAnalyser {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();

	public static SentimentAnalyser getInstance() {
		return sentimentAnalyser;
	}

	public List<Messages> sentiment(List<Messages> filteredMessageList) {
		return bayesClassifier(filteredMessageList);
	}

	private List<Messages> bayesClassifier(List<Messages> reliableMessageList) {
		Classifier<String, String> classifier = BayesClassifier.getInstance();

		for (Messages msgData : reliableMessageList) {
			List<String> unClassifiedText = UtilLucene.ngramString(msgData.getMessage());

			String classification = null;
			try {
				classification = classifier.classify(unClassifiedText).getCategory();
			} catch (IllegalStateException e) {
				logger.warn("Classifier throw the following Exception. Classification will be skiped.");
				break;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Message: " + msgData.getMessage() + " classified as: " + classification);
			}
			if (Classification.POSITIVE.isClassification(classification)) {
				msgData.setSentimentId(Classification.POSITIVE.getName());
			} else if (Classification.NEGATIVE.isClassification(classification)) {
				msgData.setSentimentId(Classification.NEGATIVE.getName());
			} else {
				msgData.setSentimentId(Classification.NEUTRAL.getName());
			}
		}
		return reliableMessageList;
	}
}
