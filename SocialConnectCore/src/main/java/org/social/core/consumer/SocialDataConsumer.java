package org.social.core.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.entity.domain.Messages;
import org.social.core.platform.FacebookConnection;
import org.social.core.platform.TwitterConnection;
import org.social.core.query.FacebookQuery;
import org.social.core.query.TwitterQuery;

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CopyOnWriteArrayList<Messages> results;
	private final ExecutorService executor = Executors.newCachedThreadPool();

	public SocialDataConsumer() {
		this.results = new CopyOnWriteArrayList<Messages>();
	}

	public List<Messages> consumeData(Long customerId, CustomerNetworkKeywords custKeywords) {
		List<Thread> threadList = new ArrayList<Thread>();

		// consume Facebook data
		FacebookQuery fbQuery = new FacebookQuery(customerId);
		fbQuery.setSince(custKeywords.getLastAccessForNetwork(Networks.FACEBOOK));
		fbQuery.setQuery(custKeywords.getQueryForNetwork(Networks.FACEBOOK));

		Thread fbThread = new Thread(new FacebookThread(fbQuery));
		threadList.add(fbThread);
		executor.execute(fbThread);

		// consume Twitter Data
		TwitterQuery twitterQuery = new TwitterQuery(customerId);
		twitterQuery.setLanguage("en");
		twitterQuery.setSince(custKeywords.getLastAccessForNetwork(Networks.TWITTER));
		twitterQuery.setMinus("");
		twitterQuery.setHash(custKeywords.getHashForNetwork(Networks.TWITTER));
		twitterQuery.setQuery(custKeywords.getQueryForNetwork(Networks.TWITTER));
		twitterQuery.setMentioned(custKeywords.getMentionedForNetwork(Networks.TWITTER));

		Thread twitterThread = new Thread(new TwitterThread(twitterQuery));
		threadList.add(twitterThread);
		executor.execute(twitterThread);

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
