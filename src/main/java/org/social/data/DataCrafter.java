package org.social.data;

import java.util.List;

import org.social.filter.wordlists.FoodEng;

public class DataCrafter {

	List<MessageData> rawData = null;
	FilteredMessageList filteredMessages;

	public DataCrafter(List<MessageData> rawList) {
		this.rawData = rawList;
	}

	public FilteredMessageList craft() {
		this.filteredMessages = new FilteredMessageList();


		wordlistFilter();
		//mentionedFilter();

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

	private void mentionedFilter() {
		List<MessageData> tmpList = this.filteredMessages.getNegativeList();
		// TODO
		for (MessageData messageData : tmpList) {
		}

	}

}
