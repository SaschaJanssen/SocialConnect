package org.social.core.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.CraftedState;
import org.social.core.entity.domain.Messages;

public class FilteredMessageList {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<Messages> negativeList = null;
	private List<Messages> positiveList = null;

	public FilteredMessageList() {
		this.negativeList = new ArrayList<Messages>();
		this.positiveList = new ArrayList<Messages>();
	}

	public void addToNegativeList(Messages negativeData) {
		negativeData.setCraftedStateId(CraftedState.BAD.getName());
		negativeList.add(negativeData);
	}

	public void addToPositivList(Messages positivData) {
		positivData.setCraftedStateId(CraftedState.GOOD.getName());
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
