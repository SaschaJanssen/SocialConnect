package org.social.core.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.TwitterMentionedFilter;
import org.social.core.filter.sentiment.SentimentAnalyser;
import org.social.core.filter.wordlists.WordlistFilter;
import org.social.core.util.UtilValidate;

public class DataCrafter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Messages> rawData = null;

    private List<Messages> positiveList = null;
    private List<Messages> negativeList = null;

    public DataCrafter(List<Messages> rawList) {
        rawData = rawList;

        positiveList = new ArrayList<Messages>();
        negativeList = new ArrayList<Messages>();
    }

    public FilteredMessageList reliabilityAndSentimentCrafter(CustomerNetworkKeywords customerKeywords) {
        wordlistFilter();
        mentionedFilter(customerKeywords);
        sentimentAnalyser();

        FilteredMessageList filteredMessages = new FilteredMessageList();
        filteredMessages.setPositiveList(positiveList);
        filteredMessages.setNegativeList(negativeList);
        return filteredMessages;
    }

    public FilteredMessageList sentimentCrafter() {
        positiveList = rawData;

        sentimentAnalyser();

        FilteredMessageList filteredMessages = new FilteredMessageList();
        filteredMessages.setPositiveList(positiveList);
        return filteredMessages;
    }

    public void sentimentAnalyser() {
        SentimentAnalyser sentimentAnalyser = SentimentAnalyser.getInstance();
        positiveList = sentimentAnalyser.sentiment(positiveList);
    }

    private void wordlistFilter() {
        WordlistFilter wordlistFilter = WordlistFilter.getInstance();
        for (Messages msgData : rawData) {
            if (wordlistFilter.matchesWordList(msgData.getMessage())) {
                addToPositiveList(msgData);
            } else {
                addToNegativeList(msgData);
            }
        }
    }

    private void addToNegativeList(Messages negativeMessage) {
        negativeMessage.setReliabilityId(Classification.NOT_RELIABLE.getName());
        negativeList.add(negativeMessage);
    }

    private void addToPositiveList(Messages positiveMessage) {
        positiveMessage.setReliabilityId(Classification.RELIABLE.getName());
        positiveList.add(positiveMessage);

    }

    private void mentionedFilter(CustomerNetworkKeywords customerKeywords) {

        if (logger.isDebugEnabled()) {
            logger.debug("Create mentioned filter.");
        }

        Set<String> mentionedSet = getMentionsetFromKeywords(customerKeywords);

        TwitterMentionedFilter mentionFilter = new TwitterMentionedFilter(mentionedSet);

        List<Messages> messagesToMove = new ArrayList<Messages>();
        for (Messages message : negativeList) {
            if (mentionFilter.mentioned(message.getMessage())) {
                messagesToMove.add(message);
            }
        }

        moveItemFromNegativeToPositiveList(messagesToMove);

    }

    public void moveItemFromNegativeToPositiveList(List<Messages> messagesToMove) {
        if (logger.isDebugEnabled()) {
            logger.debug("Move Item to positive list.");
        }

        for (Messages message : messagesToMove) {
            if (negativeList.contains(message)) {
                negativeList.remove(message);
            }
            addToPositiveList(message);
        }
    }

    private Set<String> getMentionsetFromKeywords(CustomerNetworkKeywords customerKeywords) {
        Set<String> mentionedSet = new HashSet<String>();
        String tag = customerKeywords.getHashForNetwork();
        if (UtilValidate.isNotEmpty(tag)) {
            mentionedSet.add(tag);
        }
        tag = customerKeywords.getMentionedForNetwork();
        if (UtilValidate.isNotEmpty(tag)) {
            mentionedSet.add(tag);
        }
        return mentionedSet;
    }

}
