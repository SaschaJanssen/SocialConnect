package org.social.data;

import java.util.ArrayList;
import java.util.List;

public class FilteredMessageList {
	private List<MessageData> negativeList = null;
	private List<MessageData> positiveList = null;

	public FilteredMessageList() {
		this.negativeList = new ArrayList<MessageData>();
		this.positiveList = new ArrayList<MessageData>();
	}

	public void addToNegativeList(MessageData negativeData) {
		negativeList.add(negativeData);
	}

	public void addToPositivList(MessageData positivData) {
		positiveList.add(positivData);
	}

	public List<MessageData> getNegativeList() {
		return this.negativeList;
	}

	public List<MessageData> getPositivList() {
		return this.positiveList;
	}

	public int countNegativeMessages() {
		return this.negativeList.size();
	}

	public int countPositivMessages() {
		return this.positiveList.size();
	}

}
