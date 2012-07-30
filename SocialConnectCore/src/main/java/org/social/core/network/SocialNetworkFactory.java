package org.social.core.network;

import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;

public class SocialNetworkFactory {

	public static SocialNetworkConnection getInstance(String network, Customers customer) throws IllegalArgumentException{

		if (Networks.FACEBOOK.getName().equals(network)) {
			return new FacebookConnection(customer);
		} else if (Networks.TWITTER.getName().equals(network)) {
			return new TwitterConnection(customer);
		} else if (Networks.YELP.getName().equals(network)) {
			return new YelpConnection(customer);
		} else if (Networks.OPENTABLE.getName().equals(network)) {
			return new OpenTableConnection(customer);
		}

		throw new IllegalArgumentException("The Network: " + network
				+ " is not implemented. Data loading is not possible.");
	}
}
