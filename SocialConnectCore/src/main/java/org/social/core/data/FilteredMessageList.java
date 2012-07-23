package org.social.core.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.social.core.entity.domain.Messages;

public class FilteredMessageList {

	private CopyOnWriteArrayList<Messages> negativeList = null;
	private CopyOnWriteArrayList<Messages> positiveList = null;

	public FilteredMessageList() {
		this.negativeList = new CopyOnWriteArrayList<Messages>();
		this.positiveList = new CopyOnWriteArrayList<Messages>();
	}

	public void addAll(FilteredMessageList filteredMessageList) {
		negativeList.addAll(filteredMessageList.getNegativeList());
		positiveList.addAll(filteredMessageList.getPositivList());
	}

	public int size() {
		return countNegativeMessages() + countPositivMessages();
	}

	public List<Messages> getNegativeList() {
		return this.negativeList;
	}

	public List<Messages> getPositivList() {
		return this.positiveList;
	}

	public int countNegativeMessages() {
		return this.negativeList.size();
	}

	public int countPositivMessages() {
		return this.positiveList.size();
	}

	public void setPositiveList(List<Messages> messages) {
		this.positiveList.addAll(messages);
	}

	public void setNegativeList(List<Messages> messages) {
		this.negativeList.addAll(messages);
	}

}
