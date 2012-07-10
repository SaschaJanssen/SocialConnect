package org.social.core.constants;

public enum KeywordType {
	QUERY, HASH, MENTIONED;

	public String getName() {
		return this.name();
	}

	public boolean isKeywordType(String keywordType) {
		return getName().equals(keywordType);
	}
}
