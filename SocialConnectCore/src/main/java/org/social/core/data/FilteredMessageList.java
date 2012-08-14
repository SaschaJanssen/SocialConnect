package org.social.core.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.social.core.entity.domain.Messages;

public class FilteredMessageList {

    private CopyOnWriteArrayList<Messages> negativeList = null;
    private CopyOnWriteArrayList<Messages> positiveList = null;

    public FilteredMessageList() {
        negativeList = new CopyOnWriteArrayList<Messages>();
        positiveList = new CopyOnWriteArrayList<Messages>();
    }

    public void addAll(FilteredMessageList filteredMessageList) {
        negativeList.addAll(filteredMessageList.getNegativeList());
        addAllToPositiveList(filteredMessageList.getPositivList());
    }

    private void addAllToPositiveList(List<Messages> messages) {
        positiveList.addAll(messages);
    }

    public int size() {
        return countNegativeMessages() + countPositivMessages();
    }

    public List<Messages> getNegativeList() {
        return negativeList;
    }

    public List<Messages> getPositivList() {
        return positiveList;
    }

    public int countNegativeMessages() {
        return negativeList.size();
    }

    public int countPositivMessages() {
        return positiveList.size();
    }

    public void setPositiveList(List<Messages> messages) {
        positiveList.addAll(messages);
    }

    public void setNegativeList(List<Messages> messages) {
        negativeList.addAll(messages);
    }

}
