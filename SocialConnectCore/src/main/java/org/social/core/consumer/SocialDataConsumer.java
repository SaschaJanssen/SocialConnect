package org.social.core.consumer;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.network.SocialKrakenFactory;
import org.social.core.network.SocialNetworkKraken;

public class SocialDataConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExecutorService executor;
    private final FilteredMessageList results;
    private final KeywordDAO keywordDao;

    public SocialDataConsumer(KeywordDAO keywordDao) {
        executor = Executors.newCachedThreadPool();
        results = new FilteredMessageList();
        this.keywordDao = keywordDao;
    }

    public FilteredMessageList consumeData(Customers customer) {
        Set<String> userNetworks = keywordDao.getUserNetworks(customer.getCustomerId());

        for (String network : userNetworks) {
            SocialNetworkKraken socialNetworkCon = SocialKrakenFactory.getInstance(network, customer, keywordDao);

            Thread networkThread = new Thread(new NetworkConsumeThread(socialNetworkCon));
            executor.execute(networkThread);
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

    private class NetworkConsumeThread implements Runnable {
        private final SocialNetworkKraken networkConnection;

        public NetworkConsumeThread(SocialNetworkKraken networkConnection) {
            this.networkConnection = networkConnection;
        }

        @Override
        public void run() {
            if (logger.isDebugEnabled()) {
                logger.debug("Start new " + networkConnection.getClass().getName()
                        + " data consumer and filtering thread.");
            }

            FilteredMessageList filteredMessages = networkConnection.fetchAndCraftMessages();

            if (logger.isDebugEnabled()) {
                logger.debug("Found " + filteredMessages.size() + " Messages from Network: "
                        + networkConnection.getClass().getName());
            }

            results.addAll(filteredMessages);
        }
    }
}
