package org.social.core.entity.helper;

import org.hibernate.Session;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Messages;

public class MessageDAO extends AbstractDAO {

    public void storeMessages(FilteredMessageList filteredMessageDataList) {
        Session session = super.beginAndGetSession();

        for (Messages messageData : filteredMessageDataList.getPositivList()) {
            session.save(messageData);
        }

        for (Messages messageData : filteredMessageDataList.getNegativeList()) {
            session.save(messageData);
        }

        super.commitSession(session);
    }
}
