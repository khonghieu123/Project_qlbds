package com.javaweb.utils;

public class StringUtil {
	public static Boolean checkString(String s) {
		if(s != null && s.length() != 0) {
			return true;
		}
		return false;
	}
}
