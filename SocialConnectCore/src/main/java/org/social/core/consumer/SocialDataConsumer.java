package org.social.core.consumer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.Networks;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.filter.sentiment.SentimentAnalyser;
import org.social.core.network.FacebookConnection;
import org.social.core.network.OpenTableConnection;
import org.social.core.network.SocialNetworkConnection;
import org.social.core.network.TwitterConnection;
import org.social.core.network.YelpConnection;

public class SocialDataConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ExecutorService executor;
	private final FilteredMessageList results;

	public SocialDataConsumer() {
		executor = Executors.newCachedThreadPool();
		results = new FilteredMessageList();
	}

	public FilteredMessageList consumeData(Customers customer) {
		KeywordDAO keywordDao = new KeywordDAO();
		Set<String> userNetworks = keywordDao.getUserNetworks(customer.getCustomerId());

		if (userNetworks.contains(Networks.FACEBOOK.getName())) {
			Thread fbThread = new Thread(new NetworkConsumeAndCraftThread(new FacebookConnection(customer)));
			executor.execute(fbThread);
		}

		if (userNetworks.contains(Networks.TWITTER.getName())) {
			Thread twitterThread = new Thread(new NetworkConsumeAndCraftThread(new TwitterConnection(customer)));
			executor.execute(twitterThread);
		}

		if (userNetworks.contains(Networks.YELP.getName())) {
			Thread yelpThread = new Thread(new NetworkConsumeThread(new YelpConnection(customer)));
			executor.execute(yelpThread);
		}

		if (userNetworks.contains(Networks.OPENTABLE.getName())) {
			Thread openTableThread = new Thread(new NetworkConsumeThread(new OpenTableConnection(customer)));
			executor.execute(openTableThread);
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

	private class NetworkConsumeAndCraftThread implements Runnable {
		private final SocialNetworkConnection networkConnection;

		public NetworkConsumeAndCraftThread(SocialNetworkConnection networkConnection) {
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
				logger.debug("Found " + receivedMsgList.size() + " Messages from Network: "
						+ networkConnection.getClass().getName());
			}

			DataCrafter crafter = new DataCrafter(receivedMsgList);
			FilteredMessageList filteredMessages = crafter.craft(networkConnection.getCustomerNetworkKeywords());
			results.addAll(filteredMessages);
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
				logger.debug("Found " + receivedMsgList.size() + " Messages from Network: "
						+ networkConnection.getClass().getName());
			}

			SentimentAnalyser sentimentAnalyser = SentimentAnalyser.getInstance();
			List<Messages> filteredMessages = sentimentAnalyser.sentiment(receivedMsgList);
			results.addAllToPositiveList(filteredMessages);
		}
	}

}
