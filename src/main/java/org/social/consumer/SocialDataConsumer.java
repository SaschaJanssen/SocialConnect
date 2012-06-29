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

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CopyOnWriteArrayList<MessageData> results;
	private ExecutorService executor = Executors.newCachedThreadPool();

	public SocialDataConsumer() {
		results = new CopyOnWriteArrayList<MessageData>();
	}

	public List<MessageData> consumeData() {
		List<Thread> threadList = new ArrayList<Thread>();

		// TODO create parameter object and interface

		Thread fbThread = new Thread(new FacebookThread("Vapiano", "yesterday"));
		threadList.add(fbThread);
		executor.execute(fbThread);

		Thread twitterThread = new Thread(new TwitterThread("Vapiano", "de", "2012-06-27"));
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
		private String query;
		private String since;

		public FacebookThread(String query, String since) {
			this.query = query;
			this.since = since;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Facebook thread.");
			}
			FacebookConnection fbConnection = new FacebookConnection();
			results.addAll(fbConnection.fetchPost(query, since));
		}
	}

	private class TwitterThread implements Runnable {
		private String query;
		private String language;
		private String since;

		public TwitterThread(String query, String language, String since) {
			this.query = query;
			this.language = language;
			this.since = since;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Twitter thread.");
			}
			TwitterConnection twitterConnection = new TwitterConnection();
			results.addAll(twitterConnection.fetchTweets(query, language, since));
		}
	}

}
