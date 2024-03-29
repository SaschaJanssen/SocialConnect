package org.social.core.util;

import java.util.Arrays;

public class UtilSimilarity {

    private static double weightThreshold = 0.7D;
    private static int numChars = 4;
    private static double similarityBound = 0.91;

    /**
     * Checks if two strings are similar. They expect to be similar when the
     * jaron winkler algorithm is greater/equal 0.91
     * 
     * @param wordLeft
     * @param wordRight
     * @return
     */
    public static boolean areStringsSimilar(String wordLeft, String wordRight) {
        double similarity = jaroWinklerSimilarity(wordLeft, wordRight);
        if (similarity >= similarityBound) {
            return true;
        } else {
            return false;
        }
    }

    public static double jaroWinklerSimilarity(String wordLeft, String wordRight) {
        int wordLeftLength = wordLeft.length();
        int wordRightLength = wordRight.length();
        if (wordLeftLength == 0) {
            return wordRightLength != 0 ? 0.0D : 1.0D;
        }

        int searchRange = Math.max(0, Math.max(wordLeftLength, wordRightLength) / 2 - 1);
        boolean matched1[] = new boolean[wordLeftLength];
        Arrays.fill(matched1, false);
        boolean matched2[] = new boolean[wordRightLength];
        Arrays.fill(matched2, false);

        int numCommon = 0;
        for (int i = 0; i < wordLeftLength; i++) {
            int start = Math.max(0, i - searchRange);
            int end = Math.min(i + searchRange + 1, wordRightLength);
            int j = start;
            do {
                if (j >= end) {
                    break;
                }
                if (!matched2[j] && wordLeft.charAt(i) == wordRight.charAt(j)) {
                    matched1[i] = true;
                    matched2[j] = true;
                    numCommon++;
                    break;
                }
                j++;
            } while (true);
        }

        if (numCommon == 0) {
            return 0.0D;
        }
        int numHalfTransposed = 0;
        int j = 0;
        for (int i = 0; i < wordLeftLength; i++) {
            if (!matched1[i]) {
                continue;
            }
            for (; !matched2[j]; j++) {
                ;
            }
            if (wordLeft.charAt(i) != wordRight.charAt(j)) {
                numHalfTransposed++;
            }

            j++;
        }

        int numTransposed = numHalfTransposed / 2;
        double numCommonD = numCommon;
        double weight = (numCommonD / wordLeftLength + numCommonD / wordRightLength + (numCommon - numTransposed)
                / numCommonD) / 3D;

        if (weight <= weightThreshold) {
            return weight;
        }

        int max = Math.min(numChars, Math.min(wordLeft.length(), wordRight.length()));

        int pos;
        for (pos = 0; pos < max && wordLeft.charAt(pos) == wordRight.charAt(pos); pos++) {
            ;
        }

        if (pos == 0) {
            return weight;
        } else {
            return weight + 0.10000000000000001D * pos * (1.0D - weight);
        }
    }
}
