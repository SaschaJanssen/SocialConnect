package org.social.platform;

import java.util.List;

import org.social.data.MessageData;
import org.social.query.Query;

public interface SocialNetworkConnection<T extends Query> {

	public List<MessageData> fetchMessages(T query);

}