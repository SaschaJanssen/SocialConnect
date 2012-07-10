package org.social.core.filter.wordlists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilValidate;

public class FoodEng {

	private static Logger logger = LoggerFactory.getLogger(FoodEng.class);

	private static Set<String> wordlist = loadInputData();

	private static Set<String> loadInputData() {
		Set<String> foodwordlist = new HashSet<String>();

		String engFoodList = "";
		try {
			engFoodList = ClassLoader.getSystemResource("wordlists/FoodEng").toURI().getPath();
		} catch (URISyntaxException e) {
			logger.error("", e);
			return null;
		}

		BufferedReader bufferedFileReader = null;
		try {
			bufferedFileReader = new BufferedReader(new FileReader(engFoodList));
			String lineInFile;
			while ((lineInFile = bufferedFileReader.readLine()) != null) {
				if (UtilValidate.isNotEmpty(lineInFile)) {
					foodwordlist.add(lineInFile);
				}
			}
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			try {
				bufferedFileReader.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}

		return foodwordlist;
	}

	public static boolean matchesWordList(String phrase) {
		boolean contains = false;

		for (String word : wordlist) {
			StringBuilder regExBuilder = buildRegEx(word);

			Pattern pattern = Pattern.compile(regExBuilder.toString(), Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(phrase);
			if (matcher.find()) {
				contains = true;
				break;
			}
		}

		return contains;
	}

	/**
	 * Build ([ .!?,]WORD$)|(^WORD|[ ]WORD)[.?!, ]
	 *
	 * @param word
	 * @return
	 */
	private static StringBuilder buildRegEx(String word) {
		StringBuilder regExBuilder = new StringBuilder();
		// last word in string with trailing space
		// ([ .!?,]WORD$)
		regExBuilder.append("([ .!?,]");
		regExBuilder.append(word);
		regExBuilder.append("$)");

		// first word in string
		// |(^WORD
		regExBuilder.append("|(^");
		regExBuilder.append(word);

		// word somewhere in the string with trailing space followed by a
		// .,!,? or a space
		// |[ ]WORD)[.?!, ]
		regExBuilder.append("|[ ]");
		regExBuilder.append(word);
		regExBuilder.append(")[.?!, ]");
		return regExBuilder;
	}
}
