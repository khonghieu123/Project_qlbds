package com.javaweb.utils;

public class NumberUtil {
	public static Boolean checkNumber(String s) {
		try {
			int tmp = Integer.parseInt(s);
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
