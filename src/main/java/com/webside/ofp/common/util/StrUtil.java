package com.webside.ofp.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

public class StrUtil {
	/**
	 * <ul>
	 * <li>Description:[字符串是否为NULL值]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public static boolean isNull(String sTemp) {
		if (sTemp == null) {
			return true;
		}
		return false;
	}

	/**
	 * <ul>
	 * Description:[字符串(去首尾空格)是否有值]
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public static boolean hasVal(String sTemp) {
		/** 是否为NULL */
		if (StrUtil.isNull(sTemp)) {
			return false;
		}
		/** 去掉首尾空格后和空字符比较 */
		if ("".equals(sTemp.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * <ul>
	 * <li>Description:[字符串是否无值]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public static boolean noVal(String sTemp) {
		return !StrUtil.hasVal(sTemp);
	}

	/**
	 * <ul>
	 * <li>Description:[字符串是否由0-9这十个字符组成]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sNumber
	 * @return
	 */
	public static boolean isNumber(String sNumber) {
		/** 为NULL直接返回false */
		if (StrUtil.isNull(sNumber)) {
			return false;
		}
		/** 正则表达式验测字符串 */
		if (sNumber.matches("^\\d+$")) {
			return true;
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Description:[比较两个字符串的值是否相等]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sArg0
	 * @param sArg1
	 * @return
	 */
	public static boolean isEquals(String sArg0, String sArg1) {
		if (StrUtil.hasVal(sArg0) && StrUtil.hasVal(sArg1)) {
			return sArg0.equals(sArg1);
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Description:[把checkBox的on转化成true值]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sCheck
	 * @return
	 */
	public static boolean isCheckBoxChecked(String sCheck) {
		if ("ON".equalsIgnoreCase(sCheck)) {
			return true;
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Description:[把NULL转成空格,不为NULL返回原字符串]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public String null2Str(String sTemp) {
		return StrUtil.null2Str(sTemp, "");
	}

	/**
	 * <ul>
	 * <li>Description:[把NULL转成defaultStr,不为NULL返回原字符串]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public static String null2Str(String sTemp, String defaultStr) {
		if (StrUtil.isNull(sTemp)) {
			return defaultStr;
		}
		return sTemp;
	}

	/**
	 * <ul>
	 * <li>Description:[把空字符串转化成NULL]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sTemp
	 * @return
	 */
	public static String str2Null(String sTemp) {
		if (StrUtil.isNull(sTemp) || "".equals(sTemp)) {
			return null;
		}
		return sTemp;
	}

	/**
	 * <ul>
	 * <li>Description:[把整数转化成字符串]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param iInt
	 * @return
	 */
	public static String int2str(int iInt) {
		return String.valueOf(iInt);
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成整数,非数字转化成0]</li>
	 * <li>Created by [Huyvanpull] [Dec 11, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sInt
	 * @return
	 */
	public static int str2int(String sInt) {
		return StrUtil.str2int(sInt, 0);
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成整数]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sInt
	 * @return
	 */
	public static int str2int(String sInt, int defaultInt) {
		try {
			return Integer.parseInt(sInt);
		} catch (Exception ex) {
			return defaultInt;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成Integer型对象]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sInteger
	 * @return
	 */
	public static Integer str2Integer(String sInteger) {
		try {
			return new Integer(sInteger);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成long]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sLong
	 * @return
	 */
	public static long str2long(String sLong) {
		return StrUtil.str2long(sLong, 0L);
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成long]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sLong
	 * @return
	 */
	public static long str2long(String sLong, long defaultValue) {
		try {
			return Long.parseLong(sLong);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成Long型对象]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sLong
	 * @return
	 */
	public static Long str2Long(String sLong) {
		try {
			return new Long(sLong);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成double]</li>
	 * <li>Created by [Huyvanpull] [Dec 11, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sDouble
	 * @return
	 */
	public static double str2double(String sDouble) {
		return StrUtil.str2double(sDouble, 0);
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成double]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sDouble
	 * @return
	 */
	public static double str2double(String sDouble, double defaultValue) {
		try {
			return Double.parseDouble(sDouble);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成Double型对象]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sDouble
	 * @return
	 */
	public static Double str2Double(String sDouble) {
		try {
			return new Double(sDouble);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串true(TRUE)或false(FALSE)]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sBoolean
	 * @return
	 */
	public static boolean str2boolean(String sBoolean) {
		if ("TRUE".equalsIgnoreCase(sBoolean) || "1".equals(sBoolean) || "yes".equalsIgnoreCase(sBoolean)) {
			return true;
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Description:[把字符串转化成Boolean型对象]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sBoolean
	 * @return
	 */
	public static Boolean str2Boolean(String sBoolean) {
		if ("TRUE".equalsIgnoreCase(sBoolean) || "1".equals(sBoolean) || "yes".equalsIgnoreCase(sBoolean)) {
			return new Boolean("true");
		}
		return new Boolean("false");
	}

	/**
	 * <ul>
	 * <li>Description:[把空格转化成&nbsp;]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sString
	 * @return
	 */
	public static String null2nbsp(String sString) {
		return StrUtil.null2Str(sString, "&nbsp;");
	}

	/**
	 * <ul>
	 * <li>Description:[指定编码类型编码字符串,默认按UTF-8格式编码]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param target
	 * @param encodeType
	 * @return
	 */
	public static String encodeUTF8(String target) {
		return StrUtil.encodeUTF8(target, "UTF-8");
	}

	/**
	 * <ul>
	 * <li>Description:[指定编码类型编码字符串,默认按UTF-8格式编码]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param target
	 * @param encodeType
	 * @return
	 */
	public static String encodeUTF8(String target, String encodeType) {
		try {
			return URLEncoder.encode(target, encodeType);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * <ul>
	 * <li>Description:[得到指定格式的数字的字符串格式]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param dNumber
	 * @param format
	 * @return
	 */
	public static String getDecimalFormat(double dNumber, String format) {
		DecimalFormat d = new DecimalFormat(format);
		return d.format(dNumber);
	}

	/**
	 * <ul>
	 * <li>Description:[得到指定格式的字符串格式]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param sNumber
	 * @param format
	 * @return
	 */
	public static String getDecimalFormat(String sNumber, String format) {
		return StrUtil.getDecimalFormat(StrUtil.str2double(sNumber), format);
	}

	/**
	 * <ul>
	 * <li>Description:[把数字字符串按格式输出]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param strDecimal
	 * @param fromate
	 * @return
	 */
	public static String str2DecimalStr(String strDecimal, String fromate) {
		double dDecimal = 0;
		try {
			dDecimal = Double.parseDouble(strDecimal);
		} catch (Exception e) {
		}
		return StrUtil.double2DecimalStr(dDecimal, fromate);
	}

	/**
	 * <ul>
	 * <li>Description:[把double型数据输出为格式字条串]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param dDecimal
	 * @param fromate
	 * @return
	 */
	public static String double2DecimalStr(double dDecimal, String fromate) {
		DecimalFormat df = new DecimalFormat(fromate);
		return df.format(dDecimal);
	}

	/**
	 * <ul>
	 * <li>Description:[把对象转化成字符串,如果为NULL,返回NULL]</li>
	 * <li>Created by [Huyvanpull] [Nov 4, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2str(Object obj) {
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}

	/**
	 * <ul>
	 * <li>Description:[把对象转化成字符串,如果为NULL,返加空字符串]</li>
	 * <li>Created by [Huyvanpull] [Nov 4, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Str(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj);
	}

	/**
	 * <ul>
	 * <li>Description:[从Request的Query中得到值]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param query
	 * @param key
	 * @return
	 */
	public static String getValFromQuery(String query, String key) {
		String result = "";
		if (query == null || key == null) {
			return "";
		}
		String[] keyValues = query.split("[&]");
		String[] keyValue = null;
		for (int i = 0; i < keyValues.length; i++) {
			keyValue = keyValues[i].split("[=]");
			if (keyValue == null || keyValue.length != 2) {
				continue;
			}
			if (keyValue[0].equals(key)) {
				if (keyValue.length > 1) {
					result = keyValue[1];
				}
			}
		}
		return result;
	}

	/**
	 * <ul>
	 * <li>Description:[把Exception对象转化成字符串信息]</li>
	 * <li>Created by [Huyvanpull] [Oct 26, 2009]</li>
	 * <li>Midified by [modifier] [modified time]</li>
	 * <ul>
	 * 
	 * @param exception
	 * @param prifix
	 * @return
	 */
	public static String getExceptionInfo(Exception exception, String prifix) {
		StringBuffer bExceptionInfo = new StringBuffer();
		bExceptionInfo.append(exception.toString());
		bExceptionInfo.append("\n\t");

		StackTraceElement[] stackTraceElements = exception.getStackTrace();
		for (int i = 0; i < stackTraceElements.length; i++) {
			bExceptionInfo.append("[");
			bExceptionInfo.append(prifix);
			bExceptionInfo.append("] ");
			bExceptionInfo.append(stackTraceElements[i].toString());
			bExceptionInfo.append("\n\t");
		}
		return bExceptionInfo.toString();
	}

}
