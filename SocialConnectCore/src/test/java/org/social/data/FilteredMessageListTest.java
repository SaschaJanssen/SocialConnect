package org.social.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.constants.CraftedState;
import org.social.core.data.FilteredMessageList;
import org.social.core.entity.domain.Messages;

public class FilteredMessageListTest {

	private FilteredMessageList msgList;

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
		List<Messages> messagesToMove = new ArrayList<Messages>();

		Messages negativeMessageData = new Messages("FB");
		msgList.addToNegativeList(negativeMessageData);
		messagesToMove.add(negativeMessageData);

		negativeMessageData = new Messages("TW");
		msgList.addToNegativeList(negativeMessageData);
		messagesToMove.add(negativeMessageData);

		negativeMessageData = new Messages("FB");
		msgList.addToNegativeList(negativeMessageData);

		assertEquals(0, msgList.countPositivMessages());

		msgList.moveItemFromNegativeToPositiveList(messagesToMove);

		assertEquals(1, msgList.countNegativeMessages());
		assertEquals(2, msgList.countPositivMessages());
	}

}
