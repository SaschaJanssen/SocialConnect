package org.social.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

		monthsMapping.put("January", 1);
		monthsMapping.put("February", 2);
		monthsMapping.put("March", 3);
		monthsMapping.put("April", 4);
		monthsMapping.put("May", 5);
		monthsMapping.put("June", 6);
		monthsMapping.put("July", 7);
		monthsMapping.put("August", 8);
		monthsMapping.put("September", 9);
		monthsMapping.put("October", 10);
		monthsMapping.put("November", 11);
		monthsMapping.put("December", 12);

		monthsMapping.put("Januar", 1);
		monthsMapping.put("Februar", 2);
		monthsMapping.put("März", 3);
		monthsMapping.put("Maerz", 3);
		monthsMapping.put("April", 4);
		monthsMapping.put("Mai", 5);
		monthsMapping.put("Juni", 6);
		monthsMapping.put("Juli", 7);
		monthsMapping.put("August", 8);
		monthsMapping.put("September", 9);
		monthsMapping.put("Oktober", 10);
		monthsMapping.put("November", 11);
		monthsMapping.put("Dezember", 12);

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
		Timestamp timestamp = null;
		if (UtilValidate.isEmpty(dateString)) {
			return timestamp;
		}

		Calendar calendar;
		if (dateString.matches(".{10}T.{1,13}")) {
			calendar = toCalendarFromFbTime(dateString);
		} else if (dateString.matches("\\w{1,3}, \\d{1,2} \\w{1,3} .*")) {
			calendar = toCalendarFromTwitterTime(dateString);
		} else if (dateString.matches("\\d{1,2}/\\d{1,2}/\\d{4,4}")) {
			calendar = toCalendarFromYelpTime(dateString);
		} else if (dateString.matches("Reviewed .*")) {
			calendar = toCalendarFromTripAdvisorUsTime(dateString);
		} else if (dateString.matches("Bewertet am .*")) {
			calendar = toCalendarFromTripAdvisorDeTime(dateString);
		} else if (dateString.matches("Published .*")) {
			calendar = toCalendarFromZagatTime(dateString);
		} else if (dateString.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{2}:\\d{2}")) {
			calendar = toCalendarFromQypeTime(dateString);
		}else {
			return timestamp;
		}

		timestamp = new Timestamp(calendar.getTimeInMillis());

		return timestamp;
	}

	private static Calendar toCalendarFromQypeTime(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        String dts = dateString.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)","$1$2");

        Calendar calendar = null;
        try {
			Date date = formatter.parse(dts);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calendar;
	}

	private static Calendar toCalendarFromZagatTime(String dateString) {
		dateString = dateString.replace("Published ", "");
		dateString = dateString.replace(",", "");

		String[] dateTime = dateString.split(" ");
		int month = months.get(dateTime[0]);
		int day = Integer.parseInt(dateTime[1]);
		int year = Integer.parseInt(dateTime[2]);

		return toCalendar("0", year, month, day, 23, 59, 59);
	}

	private static Calendar toCalendarFromTripAdvisorDeTime(String dateString) {
		dateString = dateString.replace("Bewertet am ", "");
		dateString = dateString.replace(".", "");

		String[] dateTime = dateString.split(" ");
		int day = Integer.parseInt(dateTime[0]);
		int month = months.get(dateTime[1]);
		int year = Integer.parseInt(dateTime[2]);

		return toCalendar("0", year, month, day, 23, 59, 59);
	}

	private static Calendar toCalendarFromTripAdvisorUsTime(String dateString) {
		dateString = dateString.replace("Reviewed ", "");
		dateString = dateString.replace(",", "");

		String[] dateTime = dateString.split(" ");
		int month = months.get(dateTime[0]);
		int day = Integer.parseInt(dateTime[1]);
		int year = Integer.parseInt(dateTime[2]);

		return toCalendar("0", year, month, day, 23, 59, 59);
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

	public static boolean isMessageYoungerThanLastNetworkAccess(Timestamp messageNetworkTs, Timestamp lastNetworkAccess) {
		return (messageNetworkTs != null && lastNetworkAccess != null && lastNetworkAccess.after(messageNetworkTs));
	}
}
