package org.social.filter;

import java.util.Set;

public class TwitterMentionedFilter {

	private final Set<String> mentionedSet;

	public TwitterMentionedFilter(Set<String> mentionedSet) {
		this.mentionedSet = mentionedSet;
	}

	public boolean mentioned(String phrase) {
		boolean mentioned = false;
		for (String tag : mentionedSet) {

			String pattern = " " + tag + " ";
			if (phrase.contains(pattern)) {
				mentioned = true;
				break;
			} else if (phrase.contains(tag + " ")) {
				mentioned = true;
				break;
			} else if (phrase.startsWith(tag + " ")) {
				mentioned = true;
				break;
			} else if (phrase.endsWith(tag)) {
				mentioned = true;
				break;
			}
		}

		return mentioned;
	}
}
