package org.social.core.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.TwitterMentionedFilter;
import org.social.core.filter.wordlists.FoodEng;
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

		wordlistFilter();
		mentionedFilter(customerKeywords);

		return this.filteredMessages;
	}

	private void wordlistFilter() {
		for (Messages msgData : this.rawData) {
			if (FoodEng.matchesWordList(msgData.getMessage())) {
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

		Set<String> mentionedSet = new HashSet<String>();
		String tag = customerKeywords.getHashForNetwork();
		if (UtilValidate.isNotEmpty(tag)) {
			mentionedSet.add(tag);
		}
		tag = customerKeywords.getMentionedForNetwork();
		if (UtilValidate.isNotEmpty(tag)) {
			mentionedSet.add(tag);
		}

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

}
