package org.social.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.social.constants.CraftedState;
import org.social.entity.domain.Messages;

public class FilteredMessageListTest {

	FilteredMessageList msgList;

	@Before
	public void setUp() {
		msgList = new FilteredMessageList();
	}

	@Test
	public void testNegativeList() {

		msgList.addToNegativeList(new Messages("FB"));
		assertEquals(1, msgList.countNegativeMessages());


		Messages element = msgList.getNegativeList().get(0);
		assertEquals("FB", element.getNetworkId());
		assertEquals(CraftedState.BAD.getName(), element.getCraftedStateId());
	}

	@Test
	public void testPositiveList() throws Exception {
		msgList.addToPositivList(new Messages("TW"));
		assertEquals(1, msgList.countPositivMessages());

		Messages element = msgList.getPositivList().get(0);
		assertEquals("TW", element.getNetworkId());
		assertEquals(CraftedState.GOOD.getName(), element.getCraftedStateId());
	}

	@Test
	public void testMoveItemFromNegativeToPositivList() throws Exception {
		msgList = new FilteredMessageList();

		Messages negativeMessageData = new Messages("FB");
		msgList.addToNegativeList(negativeMessageData);

		assertEquals(0, msgList.countPositivMessages());

		msgList.moveItemFromNegativeToPositiveList(negativeMessageData);

		assertEquals(0, msgList.countNegativeMessages());
		assertEquals(1, msgList.countPositivMessages());
	}

}
