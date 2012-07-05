package org.social.data;

import java.util.List;
import java.util.Set;

import org.social.entity.domain.Messages;
import org.social.filter.MentionedFilter;
import org.social.filter.wordlists.FoodEng;

public class DataCrafter {

	List<Messages> rawData = null;
	FilteredMessageList filteredMessages;

	public DataCrafter(List<Messages> rawList) {
		this.rawData = rawList;
	}

	public FilteredMessageList craft(Set<String> mentionedSet) {
		this.filteredMessages = new FilteredMessageList();

		wordlistFilter();
		mentionedFilter(mentionedSet);

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

	private void mentionedFilter(Set<String> mentionedSet) {

		MentionedFilter mentionFilter = new MentionedFilter(mentionedSet);
		List<Messages> negativeList = this.filteredMessages.getNegativeList();

		for (Messages negativeData : negativeList) {

			if (mentionFilter.mentioned(negativeData.getMessage())) {
				this.filteredMessages.moveItemFromNegativeToPositiveList(negativeData);
			}
		}

	}

}
