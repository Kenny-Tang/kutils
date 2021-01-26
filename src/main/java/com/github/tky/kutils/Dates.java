package com.github.tky.kutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	 * @param pattern
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
	 * 取出某个日期的多少个月之前或之后的日期 例如：addDateTime(new Date(),-2,1)两年前 </br>
	 * addDateTime(new Date(),-2,Calendar.MONTH)两月前，addDateTime(new
	 * Date(),-2,Calendar.DAY_OF_MONTH) 两天前
	 * 
	 * @param date  要调整的时间
	 * @param count 调整的数量
	 * @param model 调整的域，Calendar.YEAR :年， Calendar.MONTH :月
	 *              ，Calendar.DAY_OF_MONTH:日
	 * @return 增加指定日期间距后的时间
	 */
	public static Date addDateTime(Date date, int count, int model) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		if (model == Calendar.YEAR) {
			cal.add(Calendar.YEAR, count);
		}
		if (model == Calendar.MONTH) {
			cal.add(Calendar.MONTH, count);
		}
		if (model == Calendar.DAY_OF_MONTH) {
			cal.add(Calendar.DAY_OF_MONTH, count);
		}
		return cal.getTime();
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
