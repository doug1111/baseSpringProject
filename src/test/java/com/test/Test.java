package com.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试用例
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class Test {

	static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");//@.+?[\\s:]

	private static final Pattern REFERER_PATTERN = Pattern.compile("@([^@^\\s:]+)([\\s:,;]?)");

	public static void main(String[] args) {
		String msg = "@测试信息 测试@测试测试 ";
		Matcher matchr = REFERER_PATTERN.matcher(msg);
		while (matchr.find()) {
			String origion_str = matchr.group();
			System.out.println("解析到的---->" + origion_str);
			msg = msg.replaceAll(origion_str, "!@#{id:1}!@# ");
			String str = origion_str.substring(1, origion_str.length()).trim();
			char ch = str.charAt(str.length() - 1);
			if (ch == ':' || ch == ',' || ch == ';')
				str = str.substring(0, str.length() - 1);
			System.out.println("用户名---->" + str);
		}
		System.out.println(msg);
	}
}