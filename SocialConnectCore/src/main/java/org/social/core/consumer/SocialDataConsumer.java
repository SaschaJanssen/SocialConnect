package org.social.core.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.network.FacebookConnection;
import org.social.core.network.TwitterConnection;

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ExecutorService executor;
	private final FilteredMessageList results;

	public SocialDataConsumer() {
		executor = Executors.newCachedThreadPool();
		results = new FilteredMessageList();
	}

	public FilteredMessageList consumeData(Customers customer) {
		List<Thread> threadList = new ArrayList<Thread>();

		Thread fbThread = new Thread(new FacebookThread(customer));
		threadList.add(fbThread);
		executor.execute(fbThread);

		Thread twitterThread = new Thread(new TwitterThread(customer));
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
		private Customers customer;

		public FacebookThread(Customers customer) {
			this.customer = customer;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Facebook thread.");
			}

			FacebookConnection fbConnection = new FacebookConnection(customer);
			List<Messages> receivedMsgList = fbConnection.fetchMessages();

			DataCrafter crafter = new DataCrafter(receivedMsgList);
			FilteredMessageList filteredMessages = crafter.craft(fbConnection.getCustomerNetworkKeywords());
			results.addAll(filteredMessages);
		}
	}

	private class TwitterThread implements Runnable {
		private Customers customer;

		public TwitterThread(Customers customer) {
			this.customer = customer;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start Twitter thread.");
			}

			TwitterConnection twitterConnection = new TwitterConnection(customer);
			List<Messages> receivedMsgList = twitterConnection.fetchMessages();

			DataCrafter crafter = new DataCrafter(receivedMsgList);
			FilteredMessageList filteredMessages = crafter.craft(twitterConnection.getCustomerNetworkKeywords());
			results.addAll(filteredMessages);
		}
	}

}
