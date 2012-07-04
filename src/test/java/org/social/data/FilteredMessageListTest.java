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

	@Test
	public void testMoveItemFromNegativeToPositivList() throws Exception {
		msgList = new FilteredMessageList();

		MessageData negativeMessageData = new MessageData("FB");
		msgList.addToNegativeList(negativeMessageData);

		assertEquals(0, msgList.countPositivMessages());

		msgList.moveItemFromNegativeToPositiveList(negativeMessageData);

		assertEquals(0, msgList.countNegativeMessages());
		assertEquals(1, msgList.countPositivMessages());
	}

}
