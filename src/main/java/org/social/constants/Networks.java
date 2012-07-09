package org.social.constants;

public enum Networks {
	FACEBOOK, TWITTER;

	public String getName(){
		return this.name();
	}

	public boolean isNetwork(String networkName) {
		return this.name().equals(networkName);
	}
}
