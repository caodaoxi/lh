package com.lh.common.utils;

/**
 * 字符串工具类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class StringUtil {
	private StringUtil() {
	}

	/**
	 * 判断int类型是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Integer i) {
		if (i == null)
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	/**
	 * 去掉字符串后几位字符
	 * @param str
	 * @return
	 */
	public static String subStringFront(String str , int i){
		return str.substring(0,str.length()-i);
	}
	/**
	 * 字符串处理: null或空串返回null, 若不为空则返回trim()的字符串
	 * @param str
	 * @return
	 */
	public static String stringHandle(String str){
		return  isEmpty(str) ? null : str.trim();
	}
	/**
	 * 去掉日期字符串后面的“.0”
	 * @param s
	 * @return
	 */
	public static String subDateLength(String s){
		if(s == null || "".equals(s)){
			return s;
		}else if(s.trim().length() == 0){
			return null;
		}
		String sub = s.endsWith(".0") ? s.substring(0 , s.length()-2) : s;
		return sub;
	}
	/**
	 * 去除字符串首尾两端的双引号
	 * @param str
	 * @return
	 */
	public static String removeSidesQuotes(String str){
		if(str.indexOf("\"")==0) {
			str = str.substring(1,str.length());   //去掉第一个 "
		}
		if(str.lastIndexOf("\"")==(str.length()-1)) {
			str = str.substring(0,str.length()-1);  //去掉最后一个 " 
		}
		return stringHandle(str);
	}
}