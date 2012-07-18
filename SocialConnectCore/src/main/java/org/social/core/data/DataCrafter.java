package org.social.core.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.TwitterMentionedFilter;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.filter.wordlists.WordlistFilter;
import org.social.core.util.UtilLucene;
import org.social.core.util.UtilValidate;

public class DataCrafter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	List<Messages> rawData = null;
	FilteredMessageList filteredMessages;

	public DataCrafter(List<Messages> rawList) {
		this.rawData = rawList;
	}

	public FilteredMessageList craft(CustomerNetworkKeywords customerKeywords) {
		this.filteredMessages = new FilteredMessageList();

		// bayesClassifier();
		wordlistFilter();
		mentionedFilter(customerKeywords);

		return this.filteredMessages;
	}

	private void bayesClassifier() {
		Classifier<String, String> classifier = BayesClassifier.getInstance();

		for (Messages msgData : this.rawData) {
			List<String> unClassifiedText = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36), msgData.getMessage());
			String classification = classifier.classify(unClassifiedText).getCategory();

			if (Classification.RELIABLE.getName().equals(classification)) {
				this.filteredMessages.addToPositivList(msgData);
			} else {
				this.filteredMessages.addToNegativeList(msgData);
			}
		}

	}

	private void wordlistFilter() {
		WordlistFilter wordlistFilter = WordlistFilter.getInstance();
		for (Messages msgData : this.rawData) {
			if (wordlistFilter.matchesWordList(msgData.getMessage())) {
				this.filteredMessages.addToPositivList(msgData);
			} else {
				this.filteredMessages.addToNegativeList(msgData);
			}
		}
	}

	private void mentionedFilter(CustomerNetworkKeywords customerKeywords) {

		if (logger.isDebugEnabled()) {
			logger.debug("Create mentioned filter.");
		}

		Set<String> mentionedSet = getMentionsetFromKeywords(customerKeywords);

		TwitterMentionedFilter mentionFilter = new TwitterMentionedFilter(mentionedSet);
		List<Messages> negativeList = this.filteredMessages.getNegativeList();

		List<Messages> messagesToMove = new ArrayList<Messages>();
		for (Messages message : negativeList) {
			if (mentionFilter.mentioned(message.getMessage())) {
				messagesToMove.add(message);
			}
		}

		this.filteredMessages.moveItemFromNegativeToPositiveList(messagesToMove);

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
