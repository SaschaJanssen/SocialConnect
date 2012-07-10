package org.social.core.platform;

import java.util.List;

import org.social.core.entity.domain.Messages;
import org.social.core.query.Query;

public interface SocialNetworkConnection<T extends Query> {

	public List<Messages> fetchMessages(T query);

}