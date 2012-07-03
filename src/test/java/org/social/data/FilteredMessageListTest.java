package org.social.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FilteredMessageListTest {

	FilteredMessageList msgList;

	@Before
	public void setUp() {
		msgList = new FilteredMessageList();
	}

	@Test
	public void testNegativeList() {
		msgList.addToNegativeList(new MessageData("FB"));
		assertEquals(1, msgList.countNegativeMessages());

		MessageData element = msgList.getNegativeList().get(0);
		assertEquals("FB", element.getPlatform());
	}

	@Test
	public void testPositiveList() throws Exception {
		msgList.addToPositivList(new MessageData("TW"));
		assertEquals(1, msgList.countPositivMessages());

		MessageData element = msgList.getPositivList().get(0);
		assertEquals("TW", element.getPlatform());
	}

}
