package org.social.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.data.MessageData;
import org.social.platform.FacebookConnection;
import org.social.platform.TwitterConnection;
import org.social.query.FacebookQuery;
import org.social.query.TwitterQuery;

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CopyOnWriteArrayList<MessageData> results;
	private ExecutorService executor = Executors.newCachedThreadPool();

	public SocialDataConsumer() {
		results = new CopyOnWriteArrayList<MessageData>();
	}

	public List<MessageData> consumeData() {
		List<Thread> threadList = new ArrayList<Thread>();

		FacebookQuery fbQuery = new FacebookQuery();
		fbQuery.setDirect("Wolfgangs");
		fbQuery.setSince("yesterday");

		Thread fbThread = new Thread(new FacebookThread(fbQuery));
		threadList.add(fbThread);
		executor.execute(fbThread);

		TwitterQuery twitterQuery = new TwitterQuery();
		twitterQuery.setDirect("Wolfgangs");
		twitterQuery.setHash("#WOLFGANGSSTEAKH");
		twitterQuery.setMentioned("@WOLFGANGSSTEAKH");
		twitterQuery.setLanguage("en");
		twitterQuery.setSince("");
		twitterQuery.setMinus("");

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
