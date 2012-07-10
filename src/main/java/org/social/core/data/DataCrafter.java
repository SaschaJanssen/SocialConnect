package org.social.core.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.social.core.constants.Networks;
import org.social.core.entity.domain.Messages;
import org.social.core.filter.TwitterMentionedFilter;
import org.social.core.filter.wordlists.FoodEng;

public class DataCrafter {

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

		Set<String> mentionedSet = new HashSet<String>();
		mentionedSet.add(customerKeywords.getHashForNetwork(Networks.TWITTER));
		mentionedSet.add(customerKeywords.getMentionedForNetwork(Networks.TWITTER));

		TwitterMentionedFilter mentionFilter = new TwitterMentionedFilter(mentionedSet);
		List<Messages> negativeList = this.filteredMessages.getNegativeList();

		for (Messages negativeData : negativeList) {

			if (mentionFilter.mentioned(negativeData.getMessage())) {
				this.filteredMessages.moveItemFromNegativeToPositiveList(negativeData);
			}
		}

	}

}
