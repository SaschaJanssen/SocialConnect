package org.social.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.consumer.SocialDataConsumer;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.LearningData;
import org.social.core.entity.helper.CustomerDAO;
import org.social.core.entity.helper.KeywordDAOImpl;
import org.social.core.entity.helper.LearningDAO;
import org.social.core.entity.helper.MessageDAO;
import org.social.core.filter.classifier.BaseClassifier;
import org.social.core.filter.classifier.J48Classifier;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilLucene;
import org.social.core.util.UtilProperties;

public class SocialConnect {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private boolean storeLastNetworkAccessToDb;

    public SocialConnect() {
        loadProperties();
    }

    public void start() {

        initializeClassifier();

        CustomerDAO customerDao = new CustomerDAO();
        MessageDAO messageDao = new MessageDAO();

        List<Customers> customers = customerDao.getAllCustomersAndKeywords();
        for (Customers customer : customers) {
            SocialDataConsumer consumer = new SocialDataConsumer(new KeywordDAOImpl());
            FilteredMessageList filteredMessageDataList = consumer.consumeData(customer);
            consumer = null;

            messageDao.storeMessages(filteredMessageDataList);

            if (storeLastNetworkAccessToDb) {
                customerDao.updateCustomerNetworkAccess(customer, UtilDateTime.nowTimestamp());
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished social connect run successfully.");
        }

    }

    private void initializeClassifier() {
        LearningDAO learningDao = new LearningDAO();
        List<LearningData> learningData = learningDao.getLearningData();
        
        BaseClassifier classifier = new J48Classifier();
        classifier.init(learningData);
    }

    private void loadProperties() {
        System.setProperty("https.proxyHost",
                UtilProperties.getPropertyValue("conf/social.properties", "https.proxyHost"));
        System.setProperty("https.proxyPort",
                UtilProperties.getPropertyValue("conf/social.properties", "https.proxyPort"));

        System.setProperty("http.proxyHost",
                UtilProperties.getPropertyValue("conf/social.properties", "http.proxyHost"));
        System.setProperty("http.proxyPort",
                UtilProperties.getPropertyValue("conf/social.properties", "http.proxyPort"));

        System.setProperty("derby.system.home",
                UtilProperties.getPropertyValue("conf/social.properties", "derby.system.home"));

        storeLastNetworkAccessToDb = UtilProperties.getPropertyValueAsBoolean("conf/social.properties",
                "write.user.last.network.access.to.db", true);
    }

}
