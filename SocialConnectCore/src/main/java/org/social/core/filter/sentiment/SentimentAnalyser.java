package org.social.core.filter.sentiment;

import java.util.List;

import org.social.core.constants.Classification;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.util.UtilLucene;

public class SentimentAnalyser {
	private static SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();

	public static SentimentAnalyser getInstance() {
		return sentimentAnalyser;
	}

	public FilteredMessageList sentiment(FilteredMessageList filteredMessageList) {
/*
		for (Messages message : filteredMessageList.getPositivList()) {
			List<String> ngram = UtilLucene.ngramString(message.getMessage());
			System.out.println(ngram);

			message.setSentimentId(Classification.POSITIVE.getName());
		}
*/
		bayesClassifier(filteredMessageList.getPositivList());

		return filteredMessageList;
	}

	private List<Messages> bayesClassifier(List<Messages> reliableMessageList) {
		Classifier<String, String> classifier = BayesClassifier.getInstance();

		for (Messages msgData : reliableMessageList) {
			List<String> unClassifiedText = UtilLucene.ngramString(msgData.getMessage());

			String classification = classifier.classify(unClassifiedText).getCategory();

			if (Classification.POSITIVE.getName().equals(classification)) {
				msgData.setSentimentId(Classification.POSITIVE.getName());
			} else if (Classification.NEGATIVE.getName().equals(classification)) {
				msgData.setSentimentId(Classification.NEGATIVE.getName());
			} else {
				msgData.setSentimentId(Classification.NEUTRAL.getName());
			}
		}
		return reliableMessageList;
	}

}
