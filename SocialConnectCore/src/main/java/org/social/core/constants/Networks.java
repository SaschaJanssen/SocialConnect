package org.social.core.constants;


public enum Networks {
	FACEBOOK, TWITTER, YELP, QYPE, OPENTABLE, TRIPADVISOR, ZAGAT, FOURSQUARE;

	public String getName() {
		return this.name();
	}

	public boolean isNetwork(String networkName) {
		return this.name().equals(networkName);
	}
}
