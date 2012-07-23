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

public class WordlistFilter {

	private static Logger logger = LoggerFactory.getLogger(WordlistFilter.class);

	private static Set<String> wordlist = loadInputData();
	private static WordlistFilter filterInstance = new WordlistFilter();

	public static WordlistFilter getInstance() {
		return filterInstance;
	}

	private static Set<String> loadInputData() {
		if (logger.isDebugEnabled()) {
			logger.debug("Initialize wordlist filter - loading data");
		}

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

	public boolean matchesWordList(String phrase) {
		boolean contains = false;

		List<String> tokanizedPhrase = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36), phrase);
		for (String token : tokanizedPhrase) {
			contains = wordlist.contains(token);
			if (contains) {
				break;
			}
		}
		return contains;
	}
}
