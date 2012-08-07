package org.social.core.network;

import org.social.core.constants.Networks;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.connection.FacebookConnection;
import org.social.core.network.connection.QypeConnection;
import org.social.core.network.connection.TwitterConnection;
import org.social.core.network.crawler.JsoupBaseCrwaler;

public class SocialKrakenFactory {

	public static SocialNetworkKraken getInstance(String network, Customers customer, KeywordDAO keywordDao)
			throws IllegalArgumentException {

		if (Networks.FACEBOOK.getName().equals(network)) {
			return new FacebookKraken(customer, keywordDao, new FacebookConnection());
		} else if (Networks.TWITTER.getName().equals(network)) {
			return new TwitterKraken(customer, keywordDao, new TwitterConnection());
		} else if (Networks.YELP.getName().equals(network)) {
			return new YelpKraken(customer, keywordDao, new JsoupBaseCrwaler());
		} else if (Networks.OPENTABLE.getName().equals(network)) {
			return new OpenTableKraken(customer, keywordDao, new JsoupBaseCrwaler());
		} else if (Networks.TRIPADVISOR.getName().equals(network)) {
			return new TripAdvisorKraken(customer, keywordDao, new JsoupBaseCrwaler());
		} else if (Networks.ZAGAT.getName().equals(network)) {
			return new ZagatKraken(customer, keywordDao, new JsoupBaseCrwaler());
		} else if (Networks.QYPE.getName().equals(network)) {
			return new QypeKraken(customer, keywordDao, new QypeConnection());
		}

		throw new IllegalArgumentException("The Network: " + network
				+ " is not implemented. Data loading is not possible.");
	}
}
