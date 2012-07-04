package org.social.data;

import java.util.List;
import java.util.Set;

import org.social.filter.MentionedFilter;
import org.social.filter.wordlists.FoodEng;

public class DataCrafter {

	List<MessageData> rawData = null;
	FilteredMessageList filteredMessages;

	public DataCrafter(List<MessageData> rawList) {
		this.rawData = rawList;
	}

	public FilteredMessageList craft(Set<String> mentionedSet) {
		this.filteredMessages = new FilteredMessageList();


		wordlistFilter();
		mentionedFilter(mentionedSet);

		return this.filteredMessages;
	}

	private void wordlistFilter() {
		for (MessageData msgData : this.rawData) {
			if (FoodEng.matchesWordList(msgData.getMessage())) {
				this.filteredMessages.addToPositivList(msgData);
			} else {
				this.filteredMessages.addToNegativeList(msgData);
			}
		}
	}

	private void mentionedFilter(Set<String> mentionedSet) {

		MentionedFilter mentionFilter = new MentionedFilter(mentionedSet);
		List<MessageData> negativeList = this.filteredMessages.getNegativeList();

		for (MessageData negativeData : negativeList) {

			if (mentionFilter.mentioned(negativeData.getMessage())) {
				this.filteredMessages.moveItemFromNegativeToPositiveList(negativeData);
			}
		}

	}

}
