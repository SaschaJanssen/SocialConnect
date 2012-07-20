package org.social.core.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Classification;
import org.social.core.entity.domain.Messages;

public class FilteredMessageList {
	private Logger logger = LoggerFactory.getLogger(getClass());

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

	public void addToNegativeList(Messages negativeData) {
		negativeData.setReliabilityId(Classification.NOT_RELIABLE.getName());
		negativeList.add(negativeData);
	}

	public void addToPositivList(Messages positivData) {
		positivData.setReliabilityId(Classification.RELIABLE.getName());
		positiveList.add(positivData);
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

	public void moveItemFromNegativeToPositiveList(List<Messages> messagesToMove) {
		if (logger.isDebugEnabled()) {
			logger.debug("Move Item to positive list.");
		}

		for (Messages message : messagesToMove) {
			if (this.negativeList.contains(message)) {
				this.negativeList.remove(message);
			}
			this.addToPositivList(message);
		}
	}

}
