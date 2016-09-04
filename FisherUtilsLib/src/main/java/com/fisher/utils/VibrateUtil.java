package com.fisher.utils;


import android.content.Context;
import android.os.Vibrator;



/*
* permission
* <uses-permission android:name="android.permission.VIBRATE"/>
* */
public class VibrateUtil {

	public static final long[] PATTERN_SHORT = { 100, 100 };
	public static final long[] PATTERN_SHORT_NORMAL = { 100, 100, 200, 200 };
	public static final long[] PATTERN_SHORT_SHORT = { 100, 100, 100, 100 };
	public static final long[] PATTERN_SHORT_SHORT_SHORT = { 100, 100, 100, 100, 100, 100 };

	public static final long[] PATTERN_NORMAL = { 200, 200 };
	public static final long[] PATTERN_NORMAL_SHORT = { 200, 200, 100, 100 };
	public static final long[] PATTERN_NORMAL_SHORT_PAUSE_SHORT_SHORT = { 200, 200, 100, 100, 1000, 100, 100, 100 };
	public static final long[] PATTERN_NORMAL_NORMAL = { 200, 200, 200, 200 };

	public static final long[] PATTERN_LONG = { 400, 400 };
	public static final long[] PATTERN_LONG_SHORT = { 200, 400, 100, 100 };
	public static final long[] PATTERN_LONG_LONG = { 200, 400, 300, 400 };
	public static final long[] PATTERN_LONG_SHORT_PAUSE_SHORT_SHORT = { 200, 400, 100, 100, 1000, 100, 100, 100 };


	public static void vibrate ( long[] pattern ) {
		try {
			Vibrator vibrator = ( Vibrator ) AppUtil.getContext().getSystemService( Context.VIBRATOR_SERVICE );
			vibrator.vibrate( pattern, -1 );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void vibrateWarning ( ) {
		vibrate( PATTERN_LONG_SHORT );
	}

	public static void vibrateHint ( ) {
		vibrate( PATTERN_SHORT_SHORT );
	}


}
