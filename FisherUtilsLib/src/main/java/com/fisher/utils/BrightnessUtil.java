package com.fisher.utils;


import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;




public class BrightnessUtil {

	public static int getScreenBrightnessMode() {
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt( AppUtil.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return screenMode;
	}

	public static int getScreenBrightness() {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt( AppUtil.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS );
		} catch ( Settings.SettingNotFoundException e ) {
			e.printStackTrace();
		}
		return screenBrightness;
	}

	/*
	* @param mode [ Settings.System.SCREEN_BRIGHTNESS_MANUAL | Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ]
	* */
	public static void setScreenBrightnessMode( int mode ) {
		Settings.System.putInt( AppUtil.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode );
	}
	/*
	* set the global brightness
	* @param brightness [ 0->255 (darkest->brightest) ]
	* */
	public static void setScreenBrightness( int brightness ) {
		Settings.System.putInt( AppUtil.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness );
	}

	/*
	* set the current activity brightness
	* @param brightness [ 0->255 (darkest->brightest) ]
	* */
	public static void setScreenBrightness( Activity activity, int brightness ) {
		WindowManager.LayoutParams local = activity.getWindow().getAttributes();
		float value = brightness / 255.0f;
		local.screenBrightness = value;
		activity.getWindow().setAttributes( local );
	}

}
