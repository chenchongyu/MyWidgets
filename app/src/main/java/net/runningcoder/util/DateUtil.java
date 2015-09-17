/**   
 * ��һ�仰�������ļ���ʲô.
 * @title DateUtil.java
 * @package com.sinsoft.android.util
 * @author shimiso  
 * @update 2012-6-26 ����9:57:56  
 */
package net.runningcoder.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author shimiso
 */

public class DateUtil {

	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date str2Date(String str) {
		return str2Date(str, null);
	}

	public static Date str2Date(String str, String format) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

	}

	public static Calendar str2Calendar(String str) {
		return str2Calendar(str, null);

	}

	public static Calendar str2Calendar(String str, String format) {

		Date date = str2Date(str, format);
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;

	}

	public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
		return date2Str(c, null);
	}

	public static String date2Str(Calendar c, String format) {
		if (c == null) {
			return null;
		}
		return date2Str(c.getTime(), format);
	}

	public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
		return date2Str(d, null);
	}

	public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
		if (d == null) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(d);
		return s;
	}

	public static String getCurDateStr() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
	}

	/**
	 * ��õ�ǰ���ڵ��ַ��ʽ
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurDateStr(String format) {
		Calendar c = Calendar.getInstance();
		return date2Str(c, format);
	}

	// ��ʽ����
	public static String getMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

	}

	// ��ʽ����
	public static String getDay(long time) {

		return new SimpleDateFormat("yyyy-MM-dd").format(time);

	}

	// ��ʽ������
	public static String getSMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

	}
	
	/**
	 * ��ʽ��ʱ��
	 * 
	 * @param formatTime
	 * @return
	 */
	public static String formatTime(String formatTime) {
		try {
			Date date = null;
			try {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd-HH-mm-ss");
				date = df.parse(formatTime);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return customDate(date.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat dateFormat1 = new SimpleDateFormat(
			"yyyy年MM月dd日 HH:mm");
	private final static SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
	private final static SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM月dd日");

	public static String customDate(long million) {
		StringBuffer dateString = new StringBuffer("");
		Date startDate = new Date(million);

		Date now = new Date();
		if (startDate.getTime() < now.getTime()) {
			int calBetweenDay = 0;
			try {
				calBetweenDay = daysBetween(startDate, now);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (calBetweenDay == 0) {
				dateString.append("���� ").append(dateFormat2.format(startDate));
			} else if (calBetweenDay == 1) {
				dateString.append("���� ").append(dateFormat2.format(startDate));
			} else if (calBetweenDay == 2) {
				dateString.append("ǰ�� ").append(dateFormat2.format(startDate));
			} else if (calBetweenDay > 2 && calBetweenDay <= 7) {
				dateString.append(calBetweenDay + "��ǰ "
						+ dateFormat2.format(startDate));
			} else if (calBetweenDay > 7
					&& now.getYear() == startDate.getYear()) {
				dateString.append(dateFormat3.format(startDate)).append(" ")
						.append(dateFormat2.format(startDate));
			} else {
				dateString.append(dateFormat1.format(startDate));
			}
		} else {
			dateString.append(dateFormat1.format(startDate));
		}

		return dateString.toString();
	}
	
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();

		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	

	/**
	 * 将毫秒转成时间
	 */
	public static String getTimeMillisToDate(long l) {
		Timestamp d = new Timestamp(l);
		return d.toString().substring(0, 19);
	}

	/**
	 * 当前时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp crunttime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @return String ex:2006-07-07
	 */
	public static String getCurrentDate() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 10);
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @return String ex:2006-07-07 22:10:10
	 */
	public static String getCurrentDateTime() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 19);
	}

	public static String getWeekDay() {
		Calendar date = Calendar.getInstance();
		date.setTime(crunttime());
		return new SimpleDateFormat("EEEE").format(date.getTime());
	}

	/**
	 * 获取指定时间的字符串,只到日期
	 * 
	 * @param t
	 *            Timestamp
	 * @return String ex:2006-07-07
	 */
	public static String getStrDate(Timestamp t) {
		return t.toString().substring(0, 10);
	}

	/**
	 * 获取指定时间的字符串
	 * 
	 * @param t
	 *            Timestamp
	 * @return String ex:2006-07-07 22:10:10
	 */
	public static String getStrDateTime(Timestamp t) {
		return t.toString().substring(0, 19);
	}

	/**
	 * 获得当前日期的前段日期
	 * 
	 * @param days
	 * @return String
	 */
	public static String getStrIntervalDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Integer.parseInt(days));
		String strBeforeDays = sdf.format(cal.getTime());
		return strBeforeDays;
	}

	/**
	 * 格式化时间
	 * 
	 * @param dt
	 *            String -> yyyy-MM-dd hh:mm:ss
	 * @return java.util.Date.Date -> yyyy-MM-dd hh:mm:ss
	 */
	public static Date parseDateTime(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (dt.length() > 10) {
				jDt = sdf.parse(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	/**
	 * 格式化时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String -> yyyy-MM-dd HH:mm:ss
	 */
	public static String parseDateTime(Date date) {
		String s = null;
		if (date != null) {
			try {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s = f.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * 格式化日期
	 * 
	 * @param dt
	 *            String -> yyyy-MM-dd
	 * @return java.util.Date.Date -> yyyy-MM-dd
	 */
	public static Date parseDate(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (dt.length() >= 8) {
				jDt = sdf.parse(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	/**
	 * 格式化时间yyyy-MM-dd
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String -> yyyy-MM-dd
	 */
	public static String parseDate(Date date) {
		String s = null;
		try {
			if (date != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				s = f.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getLongDateFromShortDate(String dt) {
		String strDT = dt;
		try {
			if (strDT != null && strDT.length() <= 10) {
				strDT = dt.trim() + " 00:00:00";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDT;
	}

	/**
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getShortDateToHHMM(String dt) {
		String jDt = dt;
		try {
			if (jDt != null && jDt.length() <= 10) {
				jDt = jDt + " 00:00";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			jDt = sdf.parse(jDt).toLocaleString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	/**
	 * 
	 * @param dateStr
	 * @return String
	 */
	public static String formatDateToHHMM(String dateStr) {
		String resultDate = null;
		try {
			if (dateStr.length() > 10) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
				Date date = sdf.parse(dateStr);
				resultDate = sdf.format(date);
			} else
				resultDate = dateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}
	/**
	 * 
	 * @param dateStr
	 * @return String
	 */
	public static String formatDate(String dateStr) {
		String resultDate = null;
		try {
			if (dateStr.length() >= 10) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(dateStr);
				resultDate = sdf.format(date);
			} else
				resultDate = dateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 返回日期 格式:2006-07-05
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp date(String str) {
		Timestamp tp = null;
		if (str.length() <= 10) {
			String[] string = str.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			tp = new Timestamp(one, two, three, 0, 0, 0, 0);
		}
		return tp;
	}

	// 获取指定日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
	public static String getNextDay(String strDate, int day) {
		if (strDate != null && !strDate.equals("")) {
			Calendar cal1 = Calendar.getInstance();
			String[] string = strDate.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			cal1.setTime(new Date(one, two, three));
			cal1.add(Calendar.DAY_OF_MONTH, day);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(cal1.getTime());
		} else {
			return null;
		}
	}

	// 获取指定日期之后的日期字符串 如 2007-02-28 后一年 就是 2008-02-29 （含闰年）
	public static String getNextYear(String strDate, int year) {
		Calendar cal1 = Calendar.getInstance();
		String[] string = strDate.trim().split("-");
		int one = Integer.parseInt(string[0]) - 1900;
		int two = Integer.parseInt(string[1]) - 1;
		int three = Integer.parseInt(string[2]);
		cal1.setTime(new Date(one, two, three));
		cal1.add(Calendar.YEAR, year);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	/**
	 * 返回时间和日期 格式:2006-07-05 22:10:10
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp datetime(String str) {
		Timestamp tp = null;
		if (str != null && str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			int time3 = Integer.parseInt(time[2]);
			tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
		}
		return tp;
	}

	/**
	 * 返回日期和时间(没有秒) 格式:2006-07-05 22:10
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp datetimeHm(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		}
		return tp;
	}

	/**
	 * 获得当前系统日期与本周一相差的天数
	 * 
	 * @return int
	 */
	private static int getMondayPlus() {
		Calendar calendar = Calendar.getInstance();
		// 获得今天是一周的第几天，正常顺序是星期日是第一天，星期一是第二天......
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 星期日是第一天
		return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
	}

	/**
	 * 获得距当前时间所在某星期的周一的日期 例： 0-本周周一日期 -1-上周周一日期 1-下周周一日期
	 * 
	 * @param week
	 *            int
	 * @return java.util.Date
	 */
	public static Date getMondayOfWeek(int week) {
		int mondayPlus = getMondayPlus(); // 相距周一的天数差
		GregorianCalendar current = new GregorianCalendar();
		current.add(GregorianCalendar.DATE, mondayPlus + 7 * week);
		return current.getTime();
	}

	/**
	 * 获得某日前后的某一天
	 * 
	 * @param date
	 *            java.util.Date
	 * @param day
	 *            int
	 * @return java.util.Date
	 */
	public static Date getDay(Date date, int day) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(GregorianCalendar.DATE, day);
		return c.getTime();
	}

	/**
	 * 获得距当前周的前后某一周的日期
	 * 
	 * @param week
	 *            int
	 * @return String[]
	 */
	public static String[] getDaysOfWeek(int week) {
		String[] days = new String[7];
		Date monday = getMondayOfWeek(week); // 获得距本周前或后的某周周一
		Timestamp t = new Timestamp(monday.getTime());
		days[0] = getStrDate(t);
		for (int i = 1; i < 7; i++) {
			t = new Timestamp(getDay(monday, i).getTime());
			days[i] = getStrDate(t);
		}
		return days;
	}

	/***
	 * MCC的UTC时间转换，MCC的UTC不是到毫秒的
	 * 
	 * @param utc
	 * @return java.util.Date
	 */
	public static Date mccUTC2Date(long utc) {
		Date d = new Date();
		d.setTime(utc * 1000); // 转成毫秒
		return d;
	}

	// 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		if (strtodate == null) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			strtodate = formatter.parse(strDate, pos);
		}
		return strtodate;
	}

	// 将 yyyy-MM-dd HH:mm 格式字符串转换为时间
	public static Date strToDateTime(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		if (strtodate == null) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strtodate = formatter.parse(strDate, pos);
		}
		return strtodate;
	}

	// 根据输入的字符串返回日期字符串 2006-07-07 22:10 2006-07-07
	public static String getStrDate(String str) {
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			return string[0];
		} else {
			return getCurrentDate();
		}
	}

	// 获取当前时间的字符串 2006-07-07 22:10:10 2006-07-07_221010
	public static String getStrDateTime() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 19).replace(":", "").replace(" ", "_");
	}

	// 根据日期字符串，返回今天，昨天或日期
	public static String getDayOrDate(String str) {
		if (str != null && !str.equals("")) {
			if (getNextDay(str, 0).equals(getCurrentDate())) {
				str = "今天";
			} else if (getNextDay(str, 1).equals(getCurrentDate())) {
				str = "昨天";
			}
		}
		return str;
	}

	// 返回当前日期所在星期，2对应星期一
	public static int getMonOfWeek() {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		return cal1.get(Calendar.DAY_OF_WEEK);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}

	/**
	 * 获取当前日期之前的日期字符串 如 2007-04-15 前5月 就是 2006-11-15
	 */
	public static String getPreviousMonth(int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());

	}

	public static String getStrYear(int year) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -year);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(cal1.getTime()) + "年份";
	}

	/**
	 * 比较两个日期前后 可以大于或等于
	 * 
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareTwoDays(String starDate, String endDate) {
		Calendar cal_start = Calendar.getInstance();
		Calendar cal_end = Calendar.getInstance();
		cal_start.setTime(parseDate(starDate));
		cal_end.setTime(parseDate(endDate));
		return cal_end.after(cal_start);
	}

	public static int getDaysBetween(Calendar d1,
			Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR)
				- d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	// 得到两个日期之间的年
	public static int dateDiffYear(String starDate, String endDate) {
		int result = 0;
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(parseDate(starDate));
		d2.setTime(parseDate(endDate));

		// 日期大小翻转
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int yy = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
		int mm = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
		if (mm < 0) {
			result = yy - 1;
		}
		if (mm > 0) {
			result = yy;
		}
		if (mm == 0) {
			if ((d2.getTimeInMillis() - d1.getTimeInMillis()) >= 0) {
				result = yy;
			} else {
				result = yy - 1;
			}
		}
		return result;
	}

	// 获取年龄
	public static int getAgeByBirth(String starDate) {
		return dateDiffYear(starDate, getCurrentDate());
	}

	
	public static Date getDateFromIDCard(String cardnum) {
		String birth = cardnum.substring(6, 14);
		return str2Date(birth);
	}
}
