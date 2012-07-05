package org.social.constants;

public enum Networks {
	FACEBOOK("FACEBOOK"), TWITTER("TWITTER");

	private String name;
	private Networks(String name) {
		this.name = name;
	}

	public String toString(){
		return this.name;
	}
}
