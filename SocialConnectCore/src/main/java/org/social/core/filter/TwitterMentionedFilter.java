package org.social.core.filter;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TwitterMentionedFilter {

	private final Set<String> mentionedSet;

	public TwitterMentionedFilter(Set<String> mentionedSet) {
		this.mentionedSet = mentionedSet;
	}

	public boolean mentioned(String phrase) {
		boolean mentioned = false;
		for (String tag : mentionedSet) {

			String pattern = " " + tag + " ";
			if (StringUtils.containsIgnoreCase(phrase, pattern)) {
				mentioned = true;
				break;
			} else if (StringUtils.containsIgnoreCase(phrase, tag + " ")) {
				mentioned = true;
				break;
			} else if (StringUtils.startsWithIgnoreCase(phrase, tag + " ")) {
				mentioned = true;
				break;
			} else if (StringUtils.endsWithIgnoreCase(phrase, tag)) {
				mentioned = true;
				break;
			}
		}

		return mentioned;
	}
}
