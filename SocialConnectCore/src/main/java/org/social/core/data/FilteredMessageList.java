package org.social.core.data;

import java.util.ArrayList;
import java.util.List;

import org.social.core.constants.CraftedState;
import org.social.core.entity.domain.Messages;

public class FilteredMessageList {
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

	public void moveItemFromNegativeToPositiveList(Messages negativeItem) {
		if (this.negativeList.contains(negativeItem)) {
			this.negativeList.remove(negativeItem);
		}

		this.addToPositivList(negativeItem);
	}

}
