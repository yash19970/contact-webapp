package com.contact.contactwebapp.util.objectMapper;

import java.util.Date;

public class DataMapper {

	public static Float convertToFloat(Object obj) {
		return obj == null ? null : Float.valueOf(obj.toString());
	}

	public static Integer convertToInteger(Object obj) {
		return obj == null ? null : Integer.valueOf(obj.toString());
	}

	public static Date convertToDate(Object obj) {
		return obj == null ? null : (Date) obj;
	}

	public static String convertToString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	public static Long convertToLong(Object obj) {
		return obj == null ? null : Long.valueOf(obj.toString());
	}

	public static Byte convertToByte(Object obj) {
		return obj == null ? null : Byte.valueOf(obj.toString());
	}

	public static Short convertToShort(Object obj) {
		return obj == null ? null : Short.valueOf(obj.toString());
	}

	public static String convertObjArrToString(Object[] objArr) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : objArr) {
			sb.append(JsonParser.objectToJson(obj) + " , ");
		}
		return sb.toString();
	}

}
