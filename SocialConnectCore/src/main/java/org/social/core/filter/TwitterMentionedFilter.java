package org.social.core.filter;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilValidate;

public class TwitterMentionedFilter {

    // special twitter checkin string. When a user check's in at a certain place
    // the messages start's with "I'm at "
    private String imAt = "I'm at ";

    private final Set<String> mentionedSet;

    public TwitterMentionedFilter(CustomerNetworkKeywords customerKeywords) {
        this.mentionedSet = getMentionsetFromKeywords(customerKeywords);
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

    private Set<String> getMentionsetFromKeywords(CustomerNetworkKeywords customerKeywords) {
        Set<String> mentionedSet = new HashSet<String>();

        String tag = customerKeywords.getHashForNetwork();
        if (UtilValidate.isNotEmpty(tag)) {
            mentionedSet.add(tag);
        }

        tag = customerKeywords.getMentionedForNetwork();
        if (UtilValidate.isNotEmpty(tag)) {
            mentionedSet.add(tag);

            String mentionedPrefix = "@";
            if (StringUtils.startsWith(tag, mentionedPrefix)) {
                String newModifiedTag = tag.replace(mentionedPrefix, mentionedPrefix + " ");
                mentionedSet.add(newModifiedTag);
            }
        }
        return mentionedSet;
    }
}
