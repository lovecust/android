package com.fisher.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public class DensityUtils {

	// dp * mDensity = px
	public static float mDensity;
	// so * mScaledDensity = px
	public static float mScaledDensity;

	public static int mScreenWidth;
	public static int mScreenHeight;
	public static int mSystemTitlebarHeight;
	public static int mApplicationHeight;
	public static int mTitlebarHeight = 100;

	public static void init( Context context ) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		mDensity = metrics.density;
		mScaledDensity = metrics.scaledDensity;
		mScreenWidth = metrics.widthPixels;
		mScreenHeight = metrics.heightPixels;


	}
	public static int fnDp2px( Context context, float dpVal ) {
		return ( int ) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics() );
	}
	public static int sp2px( Context context, float spVal ) {
		return ( int ) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics() );
	}
	public static float fnPx2dp( float px ) {
		return ( px / mDensity );
	}
	public static float fnPx2sp( float px ) {
		return ( px / mScaledDensity );
	}

}