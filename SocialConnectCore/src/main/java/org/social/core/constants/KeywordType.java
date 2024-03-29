package org.social.core.constants;

public enum KeywordType {
    QUERY, HASH, MENTIONED, PAGE;

    public String getName() {
        return name();
    }

    public boolean isKeywordType(String keywordType) {
        return getName().equals(keywordType);
    }
}
