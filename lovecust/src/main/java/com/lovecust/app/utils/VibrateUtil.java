package com.lovecust.app.utils;


import android.content.Context;
import android.os.Vibrator;

import com.lovecust.app.lovecust.AppContext;


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


	public static void vibrate( long[] pattern ) {
		Vibrator vibrator = (Vibrator) AppContext.getContext().getSystemService( Context.VIBRATOR_SERVICE );
		vibrator.vibrate( pattern, -1 );
	}

	public static void vibrateWarning() {
		vibrate( PATTERN_LONG_SHORT );
	}
	public static void vibrateHint() {
		vibrate( PATTERN_SHORT_SHORT );
	}


}
