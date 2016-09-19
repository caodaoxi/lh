/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author jeeplus
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 根据时间戳获取时间字符串
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getDateByTimestamp(long time, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(new Date(time));
		return date;
	}


	/**
	 * 根据给定的字符串时间获得其时间戳
	 * @param createDate
	 * @return
	 * @throws ParseException
	 */
	public static long stringToMillisecond(String time , String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(time).getTime();
	}

	/**
	 * 将给定日期类型转换为给定字符串类型
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String farmat) {
		SimpleDateFormat sdf = new SimpleDateFormat(farmat);
		date = date == null ? new Date() : date;
		return sdf.format(date);
	}
	/**
	 * 根据给定字符串时间和时间格式获取日期格式时间
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate (String date , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	/**
	 * @param date		日期字符串,如:2015-07-24 16:11:06
	 * @param fromFormat		被转换的日期格式 , 如yyyy-MM-dd HH:mm:ss
	 * @param toFormat			转换后的日期格式 , 如yyyyMMdd HHmmss
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(String date , String fromFormat , String toFormat) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sdf2 = new SimpleDateFormat(toFormat);
		return sdf2.format(sdf1.parse(date));
	}
	/**
	 * 根据给定的日期字符串和时间间隔生成相应间隔时间点的字符串
	 * @param startTime	如:2015-07-24 16:11:06
	 * @param startTimeFormat	如:yyyy-MM-dd HH:mm:ss
	 * @param 间隔时间,单位为秒 , 可为负数
	 * @return
	 * @throws ParseException 
	 */
	public static String getIntervalTime(String startTime , String startTimeFormat , long updateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(startTimeFormat);
		return sdf.format(new Date(sdf.parse(startTime).getTime() + updateTime*1000));
	}
	/**
	 * 根据给定的日期和时间间隔生成相应间隔时间点的字符串
	 * @param startTime	如:2015-07-24 16:11:06
	 * @param startTimeFormat	如:yyyy-MM-dd HH:mm:ss
	 * @param 间隔时间,单位为秒 , 可为负数
	 * @return
	 * @throws ParseException 
	 */
	public static String getIntervalTime(Date date , String startTimeFormat , long updateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(startTimeFormat);
		return sdf.format(new Date(date.getTime() + updateTime*1000));
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//		System.out.println(getDateByTimestamp(1473188400000l,MyConst.YYYY_MM_DD_HH_MM_SS));
	}
}
