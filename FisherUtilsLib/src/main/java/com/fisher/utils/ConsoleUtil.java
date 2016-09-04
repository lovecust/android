package com.fisher.utils;

import android.util.Log;


public class ConsoleUtil {

	public static String console ( String msg ) {
		if ( AppUtil.isDebug() )
			Log.i( "ConsoleUtil -->> ", msg + "" );
		return msg;
	}

	public static String console ( Object obj ) {
		return console( "" + obj );
	}

	// when something important happened, then log it
	public static String warning ( String msg ) {
		if ( AppUtil.isDebug() )
			Log.w( "Warning ->> ", msg );
		return msg;
	}

	public static String error ( String bug ) {
		if ( AppUtil.isDebug() )
			Log.e( "Fatal Error ->> ", bug );
		return bug;
	}
}