package org.social.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.constants.KeywordType;
import org.social.constants.Networks;
import org.social.entity.domain.Customers;
import org.social.entity.domain.Keywords;
import org.social.entity.domain.Messages;
import org.social.platform.FacebookConnection;
import org.social.platform.TwitterConnection;
import org.social.query.FacebookQuery;
import org.social.query.TwitterQuery;

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CopyOnWriteArrayList<Messages> results;
	private final List<Customers> customersAndKeywords;
	private final ExecutorService executor = Executors.newCachedThreadPool();

	public SocialDataConsumer(List<Customers> customersAndKeywords) {
		this.results = new CopyOnWriteArrayList<Messages>();
		this.customersAndKeywords = customersAndKeywords;
	}

	public List<Messages> consumeData() {
		List<Thread> threadList = new ArrayList<Thread>();

		for (Customers customer : customersAndKeywords) {
			Long customerId = customer.getCustomerId();
			Set<Keywords> custKeywords = customer.getKeywords();

			FacebookQuery fbQuery = new FacebookQuery(customerId);
			fbQuery.setSince("yesterday");

			TwitterQuery twitterQuery = new TwitterQuery(customerId);
			twitterQuery.setLanguage("en");
			twitterQuery.setSince("");
			twitterQuery.setMinus("");

			for (Keywords keyword : custKeywords) {
				String networkId = keyword.getNetworkId();

				if (Networks.FACEBOOK.isNetwork(networkId)) {

					String keywordType = keyword.getKeywordTypeId();
					if (KeywordType.QUERY.isKeywordType(keywordType)) {
						fbQuery.setQuery(keyword.getKeyword());
					} else if (KeywordType.HASH.isKeywordType(keywordType)) {
						// ignored
					} else if (KeywordType.MENTIONED.isKeywordType(keywordType)) {
						// ignored
					}

					keyword.getKeyword();

				} else if (Networks.TWITTER.isNetwork(networkId)) {

					String keywordType = keyword.getKeywordTypeId();
					if (KeywordType.QUERY.isKeywordType(keywordType)) {
						twitterQuery.setQuery(keyword.getKeyword());
					} else if (KeywordType.HASH.isKeywordType(keywordType)) {
						twitterQuery.setHash(keyword.getKeyword());
					} else if (KeywordType.MENTIONED.isKeywordType(keywordType)) {
						twitterQuery.setMentioned(keyword.getKeyword());
					}

				}

			}

			Thread fbThread = new Thread(new FacebookThread(fbQuery));
			threadList.add(fbThread);
			executor.execute(fbThread);

			Thread twitterThread = new Thread(new TwitterThread(twitterQuery));
			threadList.add(twitterThread);
			executor.execute(twitterThread);

		}
		waitForThreadsToFinish();

		if (logger.isDebugEnabled()) {
			logger.debug("Finished all threads and found " + results.size() + " messages.");
		}
		return results;
	}

	private void waitForThreadsToFinish() {
		executor.shutdown();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Wait for threads to finish.");
			}
			executor.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	private class FacebookThread implements Runnable {
		private FacebookQuery query;

		public FacebookThread(FacebookQuery query) {
			this.query = query;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Facebook thread.");
			}

			FacebookConnection fbConnection = new FacebookConnection();
			results.addAll(fbConnection.fetchMessages(query));
		}
	}

	private class TwitterThread implements Runnable {
		private TwitterQuery query;

		public TwitterThread(TwitterQuery query) {
			this.query = query;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Twitter thread.");
			}

			TwitterConnection twitterConnection = new TwitterConnection();
			results.addAll(twitterConnection.fetchMessages(query));
		}
	}

}
