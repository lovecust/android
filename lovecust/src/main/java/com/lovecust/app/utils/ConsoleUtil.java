package com.lovecust.app.utils;

import android.util.Log;

import com.lovecust.app.lovecust.AppContext;

public class ConsoleUtil {

	public static String console ( String msg ) {
		if ( AppContext.mDebug )
			Log.i( "ConsoleUtil -->> ", msg + "" );
		return msg;
	}

	public static String console ( Object obj ) {
		return console( "" + obj );
	}

	// when something important happened, then log it
	public static String warning ( String msg ) {
		if ( AppContext.mDebug )
			Log.w( "Warning ->> ", msg );
		return msg;
	}

	public static String error ( String bug ) {
		if ( AppContext.mDebug )
			Log.e( "Fatal Error ->> ", bug );
		return bug;
	}
}