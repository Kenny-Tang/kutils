package com.github.tky.kutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates extends org.apache.commons.lang3.time.DateUtils {

	public static final String FMT_YYYY_MM = "yyyy-MM";
	public static final String FMT_YYYYMM = "yyyyMM";
	public static final String FMT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FMT_YYYYMMDD = "yyyyMMdd";
	public static final String FMT_HH_MM_SS = "HH:mm:ss";
	public static final String FMT_HHMMSS = "HHmmss";
	public static final String FMT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FMT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FMT_YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 将一个字符串的日期按照指定的pattern转换成Date对象。
	 * 
	 * @param dateStr 字符串的日期
	 * @param pattern 默认 {@link Dates.FMT_YYYY_MM_DD}
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		if (dateStr == null || dateStr.equals("")) {
			throw new RuntimeException("dateStr can not be empty.");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = FMT_YYYY_MM_DD;
		}
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(dateStr + " do not matches " + pattern, e);
		}
	}

	/**
	 * 格式化时间
	 * 
	 * @param date       要格式化的时间,可以写null,format(null,"")，默认为当前时间。
	 * @param formatType 格式化的类型，若为""，则默认为如 "yyyy-MM-dd" M 年中的月份, w 年中的周数 ,D 年中的周数 ,S
	 *                   毫秒数
	 * @return 格式化后的字符串类型时间
	 */
	public static String format(Date date, String formatType) {

		if (date == null) {
			date = new Date();
		}
		if (formatType == null || "".equals(formatType)) {
			formatType = "yyyy-MM-dd";
		}
		SimpleDateFormat myformat = new SimpleDateFormat(formatType);
		String formatDate = myformat.format(date);

		return formatDate;
	}

	/**
	 * 获取下一个月的第一天
	 * 
	 * @param date
	 * @return 下一个月的第一天.
	 */
	public static Date firstDayOfNextMonth(Date date) {
		Date tmp = truncate(date, Calendar.MONTH);
		Date resultDate = addMonths(tmp, 1);
		return resultDate;
	}

}
