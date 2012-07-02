package org.social.util;

public class UtilValidate {
	/** Check whether string s is empty. */
	public static boolean isEmpty(String s) {
		return (s == null) || s.length() == 0;
	}

    /** Check whether string s is NOT empty. */
    public static boolean isNotEmpty(String s) {
        return (s != null) && s.length() > 0;
    }
}
