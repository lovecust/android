package com.fisher.utils;

public class ArrayUtil {

	public static String[] toStrings( int[] values ) {
		String[] results = new String[ values.length ];
		for ( int i = 0; i < values.length; i++ ) {
			results[ i ] = values[ i ] + "";
		}
		return results;
	}
}
