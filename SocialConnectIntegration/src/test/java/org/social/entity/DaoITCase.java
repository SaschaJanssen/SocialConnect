package org.social.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.SocialITCase;
import org.social.core.constants.Classification;
import org.social.core.constants.Networks;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.HibernateUtil;
import org.social.core.entity.domain.Customers;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.domain.LearningData;
import org.social.core.entity.domain.Messages;
import org.social.core.entity.helper.CustomerDAO;
import org.social.core.entity.helper.KeywordDAO;
import org.social.core.entity.helper.LearningDAO;
import org.social.core.entity.helper.MessageDAO;
import org.social.core.util.UtilDateTime;

public class DaoITCase extends SocialITCase {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SessionFactory sessionFactory;

	public DaoITCase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testDBWrite() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		try {
			Transaction tx = session.beginTransaction();
			create(session);
			tx.commit();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Test
	public void testDB_Read() throws Exception {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		List<Messages> result = session.createQuery("from Messages").list();
		assertTrue(!result.isEmpty());

		if (logger.isDebugEnabled()) {
			for (Messages messag : (List<Messages>) result) {
				logger.debug(messag.toString());
			}
		}

		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	private void create(Session session) {
		Messages n = new Messages(Networks.TWITTER.getName());
		n.setMessage("blaaaaaaaaaaa");
		n.setLanguage("de");
		n.setCustomerId(new Long(1));
		session.save(n);
	}

	@Test
	public void testLearningDao() throws Exception {
		LearningDAO learningDao = new LearningDAO();

		List<LearningData> learningData = new ArrayList<LearningData>();
		LearningData ld = new LearningData();
		ld.setClassificationId(Classification.RELIABLE.getName());
		ld.setClassificationTypeId("RELIABILITY");
		ld.setLearningData("learning data");
		learningData.add(ld);
		learningDao.storeLearningData(learningData);

		learningData = learningDao.getLearningData();
		assertNotNull(learningData);
		assertTrue("The list shouldn't be empty.", learningData.size() > 0);
	}

	@Test
	public void testMessagesDao() throws Exception {
		MessageDAO messageDao = new MessageDAO();

		Messages msg = new Messages(Networks.TWITTER.getName());
		msg.setMessage("Messages");
		msg.setCustomerId(new Long(1));
		List<Messages> lm = new ArrayList<Messages>();
		lm.add(msg);

		FilteredMessageList fml = new FilteredMessageList();
		fml.setPositiveList(lm);

		messageDao.storeMessages(fml);
	}

	@Test
	public void testKeywordDao() throws Exception {
		KeywordDAO keywordDao = new KeywordDAO();

		List<Keywords> kws = keywordDao.getMappedKeywordByCustomerAndNetwork(1L, Networks.TWITTER.getName());

		assertNotNull(kws);
		assertTrue("The list shouldn't be empty.", kws.size() > 0);
	}

	@Test
	public void testCustomerDao() throws Exception {
		CustomerDAO customerDao = new CustomerDAO();

		List<Customers> custList = customerDao.getAllCustomersAndKeywords();

		assertNotNull(custList);
		assertTrue(custList.size() > 0);

		for (Customers customer : custList) {
			customerDao.updateCustomerNetworkAccess(customer, UtilDateTime.nowTimestamp());
		}
	}

}
