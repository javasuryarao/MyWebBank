package com.java.util;

import com.java.common.Constants;

public class StringHelper {

	public static boolean isEmpty(String sampleString)
	{
		return(sampleString == null || sampleString.trim().equals(Constants.EMPTY_STRING));
	}
	public static boolean isNotEmpty(String sampleString)
	{
		return!isEmpty(sampleString);
	}
}
