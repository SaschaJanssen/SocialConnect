package org.social.core.filter.wordlists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilLucene;
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

		List<String> tokanizedPhrase = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36), phrase);
		for (String word : wordlist) {
			contains = tokanizedPhrase.contains(word);
			if (contains) {
				break;
			}
		}
		return contains;
	}
}
