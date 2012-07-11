package org.social.core.network;

import java.util.List;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Messages;

public interface SocialNetworkConnection {

	public List<Messages> fetchMessages();

	public CustomerNetworkKeywords getCustomerNetworkKeywords();

}