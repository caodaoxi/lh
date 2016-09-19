package com.lh.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类
 * @author liuyan
 * 2015-12-23
 */
public class DateUtil {
	private DateUtil() {
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
	 * 获取指定时间n天前/后的同一时间点 , n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherDate(String date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		Date date2 = StringUtil.isEmpty(date) ? new Date() : sdf.parse(date);
		c.setTime(date2);
		c.add(Calendar.DATE, n);
		return sdf.format(c.getTime());
	}
	/**
	 * 获取指定时间n天前/后的同一时间点 , n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherDate(Date date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.DATE, n);
		return sdf.format(c.getTime());
	}
	/**
	 * 获取指定时间n个月前/后时间点 , n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherMonthDate(String date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		Date date2 = StringUtil.isEmpty(date) ? new Date() : sdf.parse(date);
		c.setTime(date2);
		c.add(Calendar.MONTH, n);
		return sdf.format(c.getTime());
	}
	/**
	 * 获取指定时间n个月前/后时间点, n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherMonthDate(Date date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		return sdf.format(c.getTime());
	}
	/**
	 * 获取指定时间n个年前/后的同一时间点 , n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherYearDate(String date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		Date date2 = StringUtil.isEmpty(date) ? new Date() : sdf.parse(date);
		c.setTime(date2);
		c.add(Calendar.YEAR, n);
		return sdf.format(c.getTime());
	}
	/**
	 * 获取指定时间n个年前/后的同一时间点 , n可为负数
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	public static String getOtherYearDate(Date date, int n , String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.YEAR, n);
		return sdf.format(c.getTime());
	}
	
	/**
	 * 根据一段日期得到每天的起始时间和结束时间yyyy-MM-dd HH:mm:ss
	 * @param startDate 开始日期 yyyy-MM-dd
	 * @param endDate 结束日期 yyyy-MM-dd
	 * @return 每天的起始时间和结束时间(例如：2015-07-01 00:00:00 和 2015-07-01 23:59:59)
	 */
	public static List<Map<String,Object>> getEveryDayStartEndTime(String startDate,String endDate){
		List<Map<String,Object>> dateTimeList = new ArrayList<Map<String,Object>>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			start.setTime(sdf2.parse(startDate));
			end.setTime(sdf2.parse(endDate));
		}catch(Exception e){
			e.printStackTrace();
		}
		//遍历出时间
		while(start.compareTo((end))<=0){
			Map<String,Object> dateTimeMap = new HashMap<String,Object>();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf2.format(start.getTime());
			String finalStart = time.concat(" 00:00:00");//最终获得的每天的开始时间
			String finalEnd = time.concat(" 23:59:59");//最终获得的每天的结束时间
			start.add(Calendar.DAY_OF_MONTH, 1);
			dateTimeMap.put("finalStart", finalStart);
			dateTimeMap.put("finalEnd", finalEnd);
			dateTimeList.add(dateTimeMap);
		}
		return dateTimeList;
	}
	/**
	 * 根据一段日期得到每月的日期字符串
	 * @param startDate 开始日期 yyyy-MM
	 * @param endDate 结束日期 yyyy-MM
	 * @return 每月时间字符串(yyyyMM例如：201504、201505、201506)
	 */
	public static List<String> getEveryMonthStr(String startDate, String endDate, String fromFormat, String toFormat){
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try{
			SimpleDateFormat sdf2 = new SimpleDateFormat(fromFormat);
			start.setTime(sdf2.parse(startDate));
			end.setTime(sdf2.parse(endDate));
		}catch(Exception e){
			e.printStackTrace();
		}
		//遍历出时间
		while(start.compareTo((end))<=0){
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.MONTH, 1);
		}
		return dateTimeList;
	}
	/**
	 * 根据一段日期得到每天的日期字符串
	 * @param startDate 开始日期yyyy-MM-dd
	 * @param endDate 结束日期 yyyy-MM-dd
	 * @param fromFormat 开始和结束日期的格式,需要保持一致
	 * @param toFormat 需要转换的格式 例如(yyyy-MM-dd或者yyyyMMdd等)
	 * @return 每天时间字符串(按照toFormat格式转换yyyyMMdd例如：20150701、20150702、20150703)
	 */
	public static List<String> getEveryDayStr(String startDate,String endDate, String fromFormat,String toFormat){
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try{
			SimpleDateFormat sdf2 = new SimpleDateFormat(fromFormat);
			start.setTime(sdf2.parse(startDate));
			end.setTime(sdf2.parse(endDate));
		}catch(Exception e){
			e.printStackTrace();
		}
		//遍历出时间
		while(start.compareTo((end))<=0){
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dateTimeList;
	}
	public static List<String> getEveryDayStr(String startDate,Date endDate, String fromFormat,String toFormat){
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try{
			SimpleDateFormat sdf2 = new SimpleDateFormat(fromFormat);
			start.setTime(sdf2.parse(startDate));
			end.setTime(endDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		//遍历出时间
		while(start.compareTo((end))<=0){
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dateTimeList;
	}
	/**
	 * 根据一段日期得到--以年为组--每天的日期字符串yyyyMMdd
	 * @param startDate 开始日期 yyyy-MM-dd
	 * @param endDate 结束日期 yyyy-MM-dd
	 * @return 每天时间字符串({2014=[20141228,20141231], 2015=[20150101, 20150102,...})
	 */
	public static Map<String,List<String>> getEveryDayStr4Year(String startDate,String endDate,String fromFormat,String toFormat){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try{
			SimpleDateFormat sdf2 = new SimpleDateFormat(fromFormat);
			start.setTime(sdf2.parse(startDate));
			end.setTime(sdf2.parse(endDate));
		}catch(Exception e){
			e.printStackTrace();
		}
		int tempYear = start.get(Calendar.YEAR);
		//遍历出时间
		while(start.compareTo((end))<=0){
			if(tempYear != start.get(Calendar.YEAR)){
				map.put(tempYear+"", dateTimeList);
				tempYear = start.get(Calendar.YEAR);
				dateTimeList = new ArrayList<String>();
			}
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.DAY_OF_MONTH, 1);
		}
		map.put(tempYear+"", dateTimeList);
		return map;
	}
	/**
	 * 根据当前的日期得到小时数
	 * @param dateStr
	 * @return
	 */
	public static List<String> getEveryHourStr() {
		return Arrays.asList(new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13"
				,"14","15","16","17","18","19","20","21","22","23","24"}); 
	}
	
	/**
	 * 获取当前系统时间与特定时间比较大小, 时间格式为字符串类型"yyyyMMdd", 若当前系统时间大于给定时间点, 则返回true, 反则返回false
	 * @return boolean
	 */
	public static boolean timeCompare(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return  Integer.parseInt(sdf.format(new Date())) > Integer.parseInt(date);
	}
	
	/**
	 * 根据指定指定格式年月字符串获取对应的月份 天数 
	 * @param date
	 * @param format
	 * @return 
	 * @throws ParseException 
	 */
	public static int getDaysByYearMonth(String date, String format) throws ParseException{
		String yearMonth = formatDate(date, format, "yyyyMM");
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		return getDaysByYearMonth(year, month);
	}
	
    /** 
     * 根据指定年月获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }
    /**
     * 根据时间戳获取日期字符串
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
	 * 获取指定时间向前每隔m小时的n个时间点数组
	 * @param date
	 * @param n
	 * @return
	 */
	public static List<String> getEveryHourStr(Date date, int m, int n, String toFormat) {
		long starttime = date.getTime()-(long)m*(n-1)*60*60*1000;
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(new Date(starttime));
		end.setTime(date);
		//遍历出时间
		while(start.compareTo((end))<=0){
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.HOUR_OF_DAY, m);
		}
		return dateTimeList;
	}
	/**
	 * 获取指定时间向前每隔m分钟的n个时间点数组
	 * @param date
	 * @param n
	 * @return
	 */
	public static List<String> getEveryMinuteStr(Date date, int m, int n, String toFormat) {
		long starttime = date.getTime()-(long)m*(n-1)*60*1000;
		List<String> dateTimeList = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(new Date(starttime));
		end.setTime(date);
		//遍历出时间
		while(start.compareTo((end))<=0){
			SimpleDateFormat sdf6 = new SimpleDateFormat(toFormat);
			String dateStr = sdf6.format(start.getTime());
			dateTimeList.add(dateStr);
			start.add(Calendar.MINUTE, m);
		}
		return dateTimeList;
	}
}
