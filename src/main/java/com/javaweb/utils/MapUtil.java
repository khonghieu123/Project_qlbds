package com.javaweb.utils;

import java.util.Map;

public class MapUtil {
	public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass) {
		Object ojb = params.getOrDefault(key, null);
		
		if(ojb != null) {
			if(tClass.getTypeName().equals("java.lang.Long")) {
				ojb = ojb != "" ? Long.valueOf(ojb.toString()) : null;
			}
			else if(tClass.getTypeName().equals("java.lang.Integer")){
				ojb = ojb != "" ? Integer.valueOf(ojb.toString()) : null;
			}
			else {
				ojb = ojb.toString();
			}
			return tClass.cast(ojb);
		}
		return null;
	}
}
