package com.lh.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 数字处理常用工具类
 * @author 劉 焱
 * @date 2016-3-31
 * @tags
 */
public class NumberUtil {
	
	/**
	 * 将double类型数字保留i位小数返回字符串类型数字[不进行四舍五入]
	 * @param f 待处理double类型数字
	 * @param i 保留的小数位数
	 * @return
	 */
	public static String doubleCutToStr(double f , int i){
		NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(i);  
        return nf.format(f);
	}
	/**
	 * 将double类型数字保留i位小数返回字符串类型数字[四舍五入]
	 * @param f 待处理double类型数字
	 * @param i 保留的小数位数
	 * @return
	 */
	public static String doubleCutToStrRound(double f , int i){
        return String.format("%."+i+"f",  f);
	}
	/**
	 * 将double类型数字保留i位小数返回[四舍五入]
	 * @param f 待处理double类型数字
	 * @param i 保留的小数位数
	 * @return
	 */
	public static double doubleCut(double f , int i){
        return (new BigDecimal(f)).setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(doubleCutToStr(1.555,2));
		System.out.println(doubleCut(1.555,2));
		System.out.println(doubleCutToStrRound(1.555,2));
	}
}
