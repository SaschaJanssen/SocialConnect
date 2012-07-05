package org.social.constants;

public enum CraftedState {
	GOOD("GOOD"), BAD("BAD"), NOT_CRAFTED("NOT_CRAFTED");

	private String name;

	private CraftedState(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
