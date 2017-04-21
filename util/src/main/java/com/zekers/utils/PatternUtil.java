package com.zekers.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	public final static Pattern AT_PATTERN = Pattern.compile("@([\\u4e00-\\u9fa5\\w\\-]+)");
	public final static Pattern TOPIC_PATTERN = Pattern.compile("#([^#]+)#");
	public final static Pattern PROJECT_PATTERN = Pattern.compile("\\$([^\\$|.]+)\\$");
	public final static Pattern LINK_PATTERN = Pattern.compile("(http(s)?://)([/a-zA-Z_0-9-./?%&=:#;\\(\\)]*)?");
	public final static Pattern LINK_FOR_VISIBLE_PATTERN = Pattern.compile("([a-zA-Z%&=:#;]+\\.[a-zA-Z\\.%&=:#;]+)|((http(s)?://|www\\.|WWW\\.)([/\\w-./?%&=:#;\\(\\)]*)?)");
	public final static Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
	public final static Pattern LINK_FILE_PATTERN = Pattern.compile("\\[file:([\\s\\S]*?)\\]");
	public final static Pattern LINK_AUDIO_PATTERN = Pattern.compile("\\[audio:([\\s\\S]*?)\\]");
	public final static Pattern LINK_VIDEO_PATTERN = Pattern.compile("\\[video:([\\s\\S]*?)\\]");
	public final static Pattern LINK_AT_PATTERN = Pattern.compile("@(\\{[\\s\\S]*?\\})");
	public final static Pattern MOBILE_PATTERN = Pattern.compile("^1[^(1269)]\\d{9}$");
	public final static Pattern IDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
	public final static Pattern IDCard2 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");


	//验证手机
	public static boolean isMobileNO(String mobiles) {
		Matcher m = PatternUtil.MOBILE_PATTERN.matcher(mobiles);
		return m.matches();
	}

	//验证邮箱
	public static boolean isEmail(String email) {
		Matcher m = PatternUtil.EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	//验证数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	//验证身份证
	public static boolean isIdCard(String str) {
		if (str==null)
			return false;
		return (PatternUtil.IDCard1.matcher(str).matches())||(PatternUtil.IDCard2.matcher(str).matches());
	}

	//验证银行卡
	public static boolean isBankCard(String str) {
		if (str==null)
			return false;
		return (str.length()==16)||(str.length()==19);
	}

}