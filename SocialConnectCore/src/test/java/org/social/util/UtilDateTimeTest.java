package org.social.util;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.social.core.util.UtilDateTime;

public class UtilDateTimeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvertToTimestamp_TwitterTimeFormat() throws Exception {
		// 2011-05-10T18:35:38+0000
		String tsString = "2011-05-10T18:35:38+0000";
		Timestamp ts = UtilDateTime.toTimestamp(tsString);
		assertEquals("2011-05-10 18:35:38.0", ts.toString());
	}

	@Test
	public void testConvertToTimestamp_FbTimeFormat() throws Exception {
		// Wed, 19 Jan 2011 20:21:46 +0000
		String tsString = "Wed, 19 Jan 2011 20:21:46 +0000";
		Timestamp ts = UtilDateTime.toTimestamp(tsString);
		assertEquals("2011-01-19 20:21:46.0", ts.toString());
	}

	@Test
	public void testConvertToTimestamp_YelpTimeFormat() throws Exception {
		String tsString = "7/8/2012";
		Timestamp ts = UtilDateTime.toTimestamp(tsString);
		assertEquals("2012-07-08 23:59:59.0", ts.toString());

		tsString = "12/20/2012";
		ts = UtilDateTime.toTimestamp(tsString);
		assertEquals("2012-12-20 23:59:59.0", ts.toString());
	}

	@Test
	public void testConvertTimestampToTwitterTime() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String ts = UtilDateTime.connvertTimestampToTwitterTime(timestamp);
		assertEquals("2012-07-10", ts);
	}

	@Test
	public void testConvertTimestampToFbTime() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, 07 - 1, 10, 12, 54, 06);
		calendar.set(Calendar.MILLISECOND, new Integer(966));

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String ts = UtilDateTime.connvertTimestampToFacebookTime(timestamp);

		Long unixTs =timestamp.getTime() / 1000L;
		assertEquals(unixTs.toString(), ts);
	}
}
