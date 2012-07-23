package org.social.core.constants;

public enum Classification {
	RELIABLE, NOT_RELIABLE, NOT_CLASSIFIED, POSITIVE, NEGATIVE, NEUTRAL;

	public String getName() {
		return this.name();
	}

	public boolean isClassification(String networkName) {
		return this.name().equals(networkName);
	}
}
