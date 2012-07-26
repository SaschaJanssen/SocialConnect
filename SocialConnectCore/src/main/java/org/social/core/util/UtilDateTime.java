package org.social.core.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UtilDateTime {

	private static Map<String, Integer> months = createMonthMapping();

	private static Map<String, Integer> createMonthMapping() {
		Map<String, Integer> monthsMapping = new HashMap<String, Integer>();
		monthsMapping.put("Jan", 1);
		monthsMapping.put("Feb", 2);
		monthsMapping.put("Mar", 3);
		monthsMapping.put("Apr", 4);
		monthsMapping.put("May", 5);
		monthsMapping.put("Jun", 6);
		monthsMapping.put("Jul", 7);
		monthsMapping.put("Aug", 8);
		monthsMapping.put("Sep", 9);
		monthsMapping.put("Oct", 10);
		monthsMapping.put("Nov", 11);
		monthsMapping.put("Dec", 12);

		return monthsMapping;
	};

	/**
	 * Return a Timestamp for right now
	 *
	 * @return Timestamp for right now
	 */
	public static java.sql.Timestamp nowTimestamp() {
		return getTimestamp(System.currentTimeMillis());
	}

	/**
	 * Convert a millisecond value to a Timestamp.
	 *
	 * @param time
	 *            millsecond value
	 * @return Timestamp
	 */
	private static java.sql.Timestamp getTimestamp(long time) {
		return new java.sql.Timestamp(time);
	}

	/**
	 * Converts a date string with the format 2011-05-10T18:35:38+0000 and Wed,
	 * 19 Jan 2011 20:21:46 +0000 to a new timestamp.
	 *
	 * @param dateString
	 * @return
	 */
	public static java.sql.Timestamp toTimestamp(String dateString) {

		Calendar calendar;
		if (dateString.matches(".{10}T.{1,13}")) {
			calendar = toCalendarFromFbTime(dateString);
		} else if (dateString.matches("\\w{1,3}, \\d{1,2} \\w{1,3} .*")) {
			calendar = toCalendarFromTwitterTime(dateString);
		} else if (dateString.matches("\\d{1,2}/\\d{1,2}/\\d{4,4}")) {
			calendar = toCalendarFromYelpTime(dateString);
		} else {
			return null;
		}

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		return timestamp;
	}

	private static Calendar toCalendarFromYelpTime(String dateString) {
		String[] dateTime = dateString.split("/");
		int month = Integer.parseInt(dateTime[0]);
		int day = Integer.parseInt(dateTime[1]);
		int year = Integer.parseInt(dateTime[2]);

		return toCalendar("0", year, month, day, 23, 59, 59);
	}

	// create Calendar from date time string Wed, 19 Jan 2011 20:21:46 +0000
	private static Calendar toCalendarFromTwitterTime(String dateString) {

		String[] dateTime = dateString.split(" ");
		int day = Integer.parseInt(dateTime[1]);
		String m = dateTime[2].trim();
		int month = months.get(m);
		int year = Integer.parseInt(dateTime[3]);

		String[] splitedTime = dateTime[4].split(":");
		int hour = new Integer(splitedTime[0]);
		int minutes = new Integer(splitedTime[1]);
		int seconds = new Integer(splitedTime[2]);

		String milisec = dateTime[5].trim();
		if (milisec.startsWith("+")) {
			milisec = milisec.substring(milisec.indexOf("+") + 1);
		}

		return toCalendar(milisec, year, month, day, hour, minutes, seconds);
	}

	// create Calendar from date time string 2011-05-10T18:35:38+0000
	private static Calendar toCalendarFromFbTime(String dateString) {
		String date = dateString.substring(0, dateString.indexOf("T"));
		String time = dateString.substring(dateString.indexOf("T") + 1, dateString.indexOf("+"));
		String milisec = dateString.substring(dateString.indexOf("+") + 1);

		String[] splitedDate = date.split("-");
		int year = new Integer(splitedDate[0]);
		int month = new Integer(splitedDate[1]);
		int day = new Integer(splitedDate[2]);

		String[] splitedTime = time.split(":");
		int hour = new Integer(splitedTime[0]);
		int minutes = new Integer(splitedTime[1]);
		int seconds = new Integer(splitedTime[2]);

		return toCalendar(milisec, year, month, day, hour, minutes, seconds);
	}

	private static Calendar toCalendar(String milisec, int year, int month, int day, int hour, int minutes, int seconds) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, day, hour, minutes, seconds);
		calendar.set(Calendar.MILLISECOND, new Integer(milisec));
		return calendar;
	}

	public static String connvertTimestampToTwitterTime(Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		String[] splitedTs = timestamp.toString().split(" ");
		return splitedTs[0];
	}

	public static String connvertTimestampToFacebookTime(Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		Long unixTimestamp = timestamp.getTime() / 1000L;
		return unixTimestamp.toString();
	}
}
