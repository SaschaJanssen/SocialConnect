package org.social.core.filter;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.social.core.util.UtilValidate;

public class TwitterMentionedFilter {

	// special twitter checkin string. When a user check's in at a certain place
	// the messages start's with "I'm at "
	private String imAt = "I'm at ";
	private String mentionedPrefix = "@";

	private final Set<String> mentionedSet;

	public TwitterMentionedFilter(Set<String> mentionedSet) {
		this.mentionedSet = mentionedSet;

		String newModifiedTag = null;
		for (String tag : this.mentionedSet) {
			if (StringUtils.startsWith(tag, mentionedPrefix)) {
				newModifiedTag = tag.replace(mentionedPrefix, mentionedPrefix + " ");
			}
		}

		if (UtilValidate.isNotEmpty(newModifiedTag)) {
			this.mentionedSet.add(newModifiedTag);
		}
	}

	public boolean mentioned(String phrase) {
		boolean mentioned = false;

		if (StringUtils.startsWithIgnoreCase(phrase, imAt)) {
			mentioned = true;
			return mentioned;
		}

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
