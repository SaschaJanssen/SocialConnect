package org.social.core.filter.sentiment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.classifier.BaseClassifier;

public class SentimentAnalyser {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();

    public static SentimentAnalyser getInstance() {
        return sentimentAnalyser;
    }

    public List<Messages> sentiment(List<Messages> unfilteredMessageList) {
        BaseClassifier classifier = null;
        try {
            classifier = BaseClassifier.getTrainedInstance();
        } catch (IllegalStateException e) {
            logger.error("The classifier throws the following error. This message will not be classified.", e);
            return unfilteredMessageList;
        }

        List<Messages> filteredMessageList = new ArrayList<Messages>();
        for (Messages msgData : unfilteredMessageList) {

            String classification = classifier.classify(msgData.getMessage());

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
            
            filteredMessageList.add(msgData);
        }

        return filteredMessageList;
    }
}
