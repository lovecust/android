package com.fisher.utils;


import java.lang.reflect.Field;

public class ClassUtil {


	public static void getFields ( Class aClass ) {
		Field[] fields = aClass.getDeclaredFields();
		for ( int i = 0; i < fields.length; i++ ) {
			ConsoleUtil.console( fields[ i ] + ": " + fields[ i ].getType() + " -> " + fields[ i ].getName() );
			try {
				ConsoleUtil.console( fields[ i ].getName() + "->" + fields[ i ].get( null ) );
			} catch ( IllegalAccessException e ) {
				e.printStackTrace();
			}
		}
	}


}
