package org.social.platform;

import java.util.List;

import org.social.entity.domain.Messages;
import org.social.query.Query;

public interface SocialNetworkConnection<T extends Query> {

	public List<Messages> fetchMessages(T query);

}