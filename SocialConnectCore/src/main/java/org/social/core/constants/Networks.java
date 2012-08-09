package org.social.core.constants;

public enum Networks {
    FACEBOOK, TWITTER, YELP, QYPE, OPENTABLE, TRIPADVISOR, ZAGAT, FOURSQUARE;

    public String getName() {
        return name();
    }

    public boolean isNetwork(String networkName) {
        return name().equals(networkName);
    }
}
