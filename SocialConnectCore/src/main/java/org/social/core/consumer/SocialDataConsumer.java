package org.social.core.consumer;

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
import org.social.core.network.SocialNetworkConnection;
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
		Thread fbThread = new Thread(new NetworkConsumeThread(new FacebookConnection(customer)));
		executor.execute(fbThread);

		Thread twitterThread = new Thread(new NetworkConsumeThread(new TwitterConnection(customer)));
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

	private class NetworkConsumeThread implements Runnable {
		private final SocialNetworkConnection networkConnection;

		public NetworkConsumeThread(SocialNetworkConnection networkConnection) {
			this.networkConnection = networkConnection;
		}

		@Override
		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("Start new " + networkConnection.getClass().getName()
						+ " data consumer and filtering thread.");
			}

			List<Messages> receivedMsgList = networkConnection.fetchMessages();

			if (logger.isDebugEnabled()) {
				logger.debug("Found " + receivedMsgList.size() + " Messages from Network: " + networkConnection.getClass().getName());
			}

			DataCrafter crafter = new DataCrafter(receivedMsgList);
			FilteredMessageList filteredMessages = crafter.craft(networkConnection.getCustomerNetworkKeywords());
			results.addAll(filteredMessages);
		}
	}

}
