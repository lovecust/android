package com.lovecust.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.projection.MediaProjectionManager;
import android.net.ConnectivityManager;


import com.lovecust.constants.AppConstant;
import com.lovecust.network.ApiManager;
import com.lovecust.tests.recorder.AppReceiver;
import com.lovecust.tests.recorder.LogPasteboard;
import com.fisher.utils.AppUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.DensityUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


import org.litepal.LitePalApplication;

public class AppContext extends LitePalApplication {

	// screen state off[false] or on[true]
	public static boolean mStateScreen = true;
	// weather the battery is being charged
	public static boolean mStateBatteryCharging = false;
	// whether the micro phone is plugged in
	public static boolean mStateMicroPhone = false;
	// application start time in millis
	public static long mTimeLaunch = System.currentTimeMillis();
	// get up time and sleep time
	public static long mTimeGetup = 0;
	public static long mTimeSleep = 0;
	// battery level
	public static int mBatteryLevel = 0;
	// the text on the paste board
	public static String mTextPasteBoard = "";
	public static final String mAppVersion = AppConstant.APP_VERSION;
	public static final String mApiVersion = AppConstant.API_VERSION;

	private static AppContext context;
	public static boolean mDebug = AppConstant.DEBUG;
	public static boolean mIsHomeSwipeExit = true;

	public static AppContext getContext ( ) {
		return context;
	}


	private AppReceiver receiver;

	@Override
	public void onCreate ( ) {
		super.onCreate();
		init();
	}

	private void init ( ) {
		context = this;

		AppUtil.init( this );
		AppUtil.setDebug( true );

		LogLevel level = AppConstant.DEBUG ? LogLevel.FULL : LogLevel.NONE;
		Logger
				.init( "Lovecust" )                 // default PRETTYLOGGER or use just init()
				.methodCount( 3 )                 // default 2
				.hideThreadInfo()               // default shown
				.logLevel( level )        // default LogLevel.FULL; Use LogLevel.NONE for the release versions.
				.methodOffset( 2 );                // default 0
//				.logAdapter( new LogAdapter() ); //default AndroidLogAdapter

		AppSetting.getInstance().flushLanguageMode();
		DensityUtils.init( this );
		if ( mDebug ) {
			ConsoleUtil.console( "Fisher-Home App Initialising :@ " + ApiManager.getHeader() );
			LogPasteboard.init();

//			AppUtil.getPhoneId();
			AppUtil.fnGetRunningApps();
			// AppUtil.fnListInstalledAppInfo();
			ConsoleUtil.console( AppUtil.getAndroidInfo() );
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction( Setting.ACTION_ECUST_WIFI_RECONNECT );
		filter.addAction( ConnectivityManager.CONNECTIVITY_ACTION );
		// http://blog.csdn.net/jaycee110905/article/details/8596519
		filter.addAction( Intent.ACTION_AIRPLANE_MODE_CHANGED );

		// battery or power
		filter.addAction( Intent.ACTION_BATTERY_CHANGED );
		filter.addAction( Intent.ACTION_BATTERY_LOW );
		filter.addAction( Intent.ACTION_BATTERY_OKAY );
		filter.addAction( Intent.ACTION_POWER_CONNECTED );
		filter.addAction( Intent.ACTION_POWER_DISCONNECTED );
		filter.addAction( Intent.ACTION_POWER_USAGE_SUMMARY );
		// screen
		filter.addAction( Intent.ACTION_SCREEN_OFF );
		filter.addAction( Intent.ACTION_SCREEN_ON );

		filter.addAction( Intent.ACTION_BOOT_COMPLETED );
		filter.addAction( Intent.ACTION_CLOSE_SYSTEM_DIALOGS );
		filter.addAction( Intent.ACTION_CONFIGURATION_CHANGED );
		filter.addAction( Intent.ACTION_DATE_CHANGED );
		filter.addAction( Intent.ACTION_DATE_CHANGED );
		filter.addAction( Intent.ACTION_HEADSET_PLUG );
		filter.addAction( Intent.ACTION_INPUT_METHOD_CHANGED );
		filter.addAction( Intent.ACTION_LOCALE_CHANGED );
		filter.addAction( Intent.ACTION_MEDIA_BUTTON );
		filter.addAction( Intent.ACTION_INSTALL_PACKAGE );
		filter.addAction( Intent.ACTION_UNINSTALL_PACKAGE );
		filter.addAction( Intent.ACTION_SHUTDOWN );
		filter.addAction( Intent.ACTION_TIMEZONE_CHANGED );
		filter.addAction( Intent.ACTION_TIME_TICK );
		filter.addAction( Intent.ACTION_SET_WALLPAPER );
		filter.addAction( Intent.ACTION_MEDIA_EJECT );
		filter.addAction( Intent.ACTION_CALL_BUTTON );
		receiver = new AppReceiver();
		context.registerReceiver( receiver, filter );
	}

	@Override
	public void onTerminate ( ) {
		context.unregisterReceiver( receiver );
		if ( mDebug ) {
			LogPasteboard.release();
		}
		super.onTerminate();
	}


	private int result;
	private Intent intent;
	private MediaProjectionManager mMediaProjectionManager;

	public int getResult ( ) {
		return result;
	}

	public Intent getIntent ( ) {
		return intent;
	}

	public MediaProjectionManager getMediaProjectionManager ( ) {
		return mMediaProjectionManager;
	}

	public void setResult ( int result1 ) {
		this.result = result1;
	}

	public void setIntent ( Intent intent1 ) {
		this.intent = intent1;
	}

	public void setMediaProjectionManager ( MediaProjectionManager mMediaProjectionManager ) {
		this.mMediaProjectionManager = mMediaProjectionManager;
	}

}
