package org.social.constants;

public enum Networks {
	FACEBOOK, TWITTER;

	public String toString(){
		return this.name();
	}

	public boolean isNetwork(String networkName) {
		return this.name().equals(networkName);
	}
}
