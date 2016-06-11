package com.lovecust.app.test;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.lovecust.app.lovecust.AppContext;
import com.lovecust.app.R;
import com.fisher.utils.CPUUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.TimeUtil;

import java.util.Arrays;


public class ActivityDeviceHome extends AppCompatActivity {

	private TextView tInfo;
	private String sBattery = "Battery Info\n";
	private String sUnexpectedActions = "Unexpected Actions\n";
	private String sActions = "Action\n";
	private String sScreenStatus = "Screen Status: " + false;
	private String sSensor = "Sensor Info\n";


	private MyReceiver myReceiver;
	private SensorManager sensorManager;
	private Sensor sensor;
	private SensorEventListener sensorEventListener;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_test_device_home );
		init();
	}
	private void init() {
		tInfo = (TextView) findViewById( R.id.info );
		fnInitSensor( this );
		myReceiver = new MyReceiver();
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_BATTERY_LOW ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_BATTERY_OKAY ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_SCREEN_OFF ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_SCREEN_ON ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_BOOT_COMPLETED ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_MEDIA_EJECT ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_MEDIA_BUTTON ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_MEDIA_REMOVED ) );
		registerReceiver( myReceiver, new IntentFilter( Intent.ACTION_MEDIA_UNMOUNTED ) );

		btnUpdateInfo( null );
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver( myReceiver );
		super.onDestroy();
	}


	public void btnUpdateInfo( View view ) {
		String sInfo = "";
		sInfo += fnGetConfigurationInfo() + "\n\n";
		sInfo += fnGetNetStatusInfo() + "\n\n";
		sInfo += fnGetWifiStatusInfo() + "\n\n";
		sInfo += fnGetScreenInfo() + "\n\n";
		sInfo += fnGetDisplayInfo() + "\n\n";
		sInfo += fnGetSystemInfo() + "\n\n";
		sInfo += fnGetApplicationInfo() + "\n\n";

		sInfo += sBattery + "\n\n";
		sInfo += sScreenStatus + "\n\n";
		sInfo += sActions + "\n\n";
		sInfo += sUnexpectedActions + "\n\n";

		sInfo += sSensor + "\n\n";

		tInfo.setText( sInfo );
		fnGetData();
	}


	private String fnGetNetStatusInfo() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if ( null == networkInfo )
			return "";
		boolean connected = networkInfo.isConnected();
		String sNetworkInfo = networkInfo.toString();
		String extraInfo = networkInfo.getExtraInfo();
		String typeName = networkInfo.getTypeName();
		String subtypeName = networkInfo.getSubtypeName();
		String networkStatus = networkInfo.getState().toString();
		String sInfo = "Net Status:\n";
		sInfo += "connected: " + connected + ";\n";
		sInfo += "sNetworkInfo: " + sNetworkInfo + ";\n";
		sInfo += "extraInfo: " + extraInfo + ";\n";
		sInfo += "typeName: " + typeName + ";\n";
		sInfo += "subtypeName: " + subtypeName + ";\n";
		sInfo += "networkStatus: " + networkStatus + ";\n";
		return sInfo;
	}

	private String fnGetWifiStatusInfo() {
		String sInfo = "Wifi Status:\n";
		sInfo += "ipAddress: " + NetUtil.ip + ";\n";
		sInfo += "ip: " + NetUtil.ipInt + ";\n";
		sInfo += "macAddress: " + NetUtil.mac + ";\n";
		sInfo += "ssid: " + NetUtil.ssid + ";\n";
		sInfo += "connectionInfo: " + NetUtil.toReadableString() + ";\n";
		return sInfo;
	}

	@TargetApi( Build.VERSION_CODES.LOLLIPOP )
	private String fnGetScreenInfo() {
		WindowManager windowManager = getWindowManager();
		Display defaultDisplay = windowManager.getDefaultDisplay();
		float[] supportedRefreshRates = defaultDisplay.getSupportedRefreshRates();
		int height = defaultDisplay.getHeight();
		int width = defaultDisplay.getWidth();
		String name = defaultDisplay.getName();
		int state = defaultDisplay.getState();
		String sInfo = "Screen Info\n";
		sInfo += "defaultDisplay: " + defaultDisplay + ";\n";
		sInfo += "width: " + width + ";\n";
		sInfo += "height: " + height + ";\n";
		sInfo += "supportedRefreshRates: " + supportedRefreshRates + ";\n";
		sInfo += "name: " + name + ";\n";
		sInfo += "state: " + state + ";\n";
		return sInfo;
	}

	private String fnGetDisplayInfo() {
		Resources resources = getResources();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		float xdpi = displayMetrics.xdpi;
		float ydpi = displayMetrics.ydpi;
		float density = displayMetrics.density;
		int densityDpi = displayMetrics.densityDpi;
		int widthPixels = displayMetrics.widthPixels;
		int heightPixels = displayMetrics.heightPixels;
		float scaledDensity = displayMetrics.scaledDensity;
		String sInfo = "Display Info\n";
		sInfo += "xdpi: " + xdpi + ";\n";
		sInfo += "ydpi: " + ydpi + ";\n";
		sInfo += "density: " + density + ";\n";
		sInfo += "densityDpi: " + densityDpi + ";\n";
		sInfo += "widthPixels: " + widthPixels + ";\n";
		sInfo += "heightPixels: " + heightPixels + ";\n";
		sInfo += "scaledDensity: " + scaledDensity + ";\n";
		return sInfo;
	}
	private String fnGetConfigurationInfo() {
		try {
			Resources resources = getResources();
			Configuration configuration = resources.getConfiguration();

			int densityDpi = configuration.densityDpi;
			float fontScale = configuration.fontScale;
			int orientation = configuration.orientation;
			int keyboard = configuration.keyboard;
			int keyboardHidden = configuration.keyboardHidden;
			int mcc = configuration.mcc;
			int mnc = configuration.mnc;
			int navigationHidden = configuration.navigationHidden;
			int navigation = configuration.navigation;
			int touchscreen = configuration.touchscreen;
			int smallestScreenWidthDp = configuration.smallestScreenWidthDp;
			String sInfo = "Configuration Info\n";
			sInfo += "densityDpi: " + densityDpi + ";\n";
			sInfo += "fontScale: " + fontScale + ";\n";
			sInfo += "orientation: " + orientation + ";\n";
			sInfo += "keyboard: " + keyboard + ";\n";
			sInfo += "keyboardHidden: " + keyboardHidden + ";\n";
			sInfo += "mcc: " + mcc + ";\n";
			sInfo += "mnc: " + mnc + ";\n";
			sInfo += "navigationHidden: " + navigationHidden + ";\n";
			sInfo += "navigation: " + navigation + ";\n";
			sInfo += "touchscreen: " + touchscreen + ";\n";
			sInfo += "smallestScreenWidthDp: " + smallestScreenWidthDp + ";\n";
			return sInfo;
		} catch ( Exception e ) {
			e.printStackTrace();
			return e.toString();
		}
	}

	private String fnGetSystemInfo() {
		try {
			long elapsedRealtime = SystemClock.elapsedRealtime();
			String cpuName = CPUUtil.getCpuName();
			String minCpuFreq = CPUUtil.getMinCpuFreq();
			String maxCpuFreq = CPUUtil.getMaxCpuFreq();
			String curCpuFreq = CPUUtil.getCurCpuFreq();
			String sInfo = "System Info\n";
			sInfo += "elapsedRealtime: " + elapsedRealtime + ";\n";
			sInfo += "cpuName: " + cpuName + ";\n";
			sInfo += "CpuFreq: [" + minCpuFreq + " - " + maxCpuFreq + "];\n";
			sInfo += "curCpuFreq: " + curCpuFreq + ";\n";
			return sInfo;
		} catch ( Exception e ) {
			e.printStackTrace();
			return "System Info\n";
		}
	}

	private String fnGetApplicationInfo() {
		try {
			long lCurTime = System.currentTimeMillis();
			long lStartTime = AppContext.mTimeLaunch;
			String sStartTime = TimeUtil.fnFormatTime( lStartTime );
			String sCurTime = TimeUtil.fnFormatTime( lCurTime );
			String sGapTime = TimeUtil.fnFormatIntervalTime( lCurTime - lStartTime );
			String sInfo = "Application Info\n";
			sInfo += "Start At: " + sStartTime + ";\n";
			sInfo += "Current Time: " + sCurTime + ";\n";
			sInfo += "Run Time: " + sGapTime + ";\n";
			sInfo += "Getup Time: " + TimeUtil.fnFormatTime( AppContext.mTimeGetup ) + ";\n";
			sInfo += "LastNightSleep Time: " + TimeUtil.fnFormatTime( AppContext.mTimeSleep ) + ";\n";
			sInfo += "mStateMicroPhone: " + AppContext.mStateMicroPhone + ";\n";
			sInfo += "mStateBatteryCharging: " + AppContext.mStateBatteryCharging + ";\n";
			sInfo += "mStateScreen: " + AppContext.mStateScreen + ";\n";
			sInfo += "mBatteryLevel: " + AppContext.mBatteryLevel + ";\n";
			sInfo += "mTextPasteBoard: <<<<<<<<<<<<<<\n" + AppContext.mTextPasteBoard + "\n>>>>>>>>>>>>>>>>>>>>>\n";
			return sInfo;
		} catch ( Exception e ) {
			e.printStackTrace();
			return "System Info\n";
		}
	}


	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive( Context context, Intent intent ) {
			toast( "Get Action: " + intent.getAction() );
			log( "Get Action: " + intent.getAction() );

//			sActions = "Action\n";
			sActions += intent.getAction() + ";\n";

			switch ( intent.getAction() ) {
				case Intent.ACTION_BATTERY_CHANGED:
					int rawlevel = intent.getIntExtra( "level", -1 );
					int scale = intent.getIntExtra( "scale", -1 );
					int status = intent.getIntExtra( "status", -1 );
					int health = intent.getIntExtra( "health", -1 );
					sBattery = "Battery Info\n";
					sBattery += "level: " + rawlevel + ";\n";
					sBattery += "scale: " + scale + ";\n";
					sBattery += "status: " + status + ";\n";
					sBattery += "health: " + health + ";\n";
					break;
				case Intent.ACTION_SCREEN_ON:
					sScreenStatus = "Screen Status\n";
					sScreenStatus += "Screen Status: " + true + ";\n";
					break;
				case Intent.ACTION_SCREEN_OFF:
					sScreenStatus = "Screen Status\n";
					sScreenStatus += "Screen Status: " + false + ";\n";
					break;
				default:
//					sUnexpectedActions="Unexpected Actions\n";
					sUnexpectedActions += intent.getAction() + ";\n";
					toast( sUnexpectedActions );
					break;
			}

			btnUpdateInfo( null );
		}
	}

	private void fnInitSensor( Context context ) {
		sensorManager = (SensorManager) context.getSystemService( SENSOR_SERVICE );
		sensor = sensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
		sensorEventListener = new SensorEventListener() {
			@Override
			public void onSensorChanged( SensorEvent event ) {
				sSensor += "-->>: " + Arrays.toString( event.values ) + "\n";
//				btnUpdateInfo( null );
			}
			@Override
			public void onAccuracyChanged( Sensor sensor, int accuracy ) {

			}
		};
	}
	private void fnGetData() {
		sSensor = "Sensor Info\n";
		sensorManager.registerListener( sensorEventListener, sensor, Sensor.TYPE_ACCELEROMETER );
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep( 2000 );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
				sensorManager.unregisterListener( sensorEventListener, sensor );
			}
		}.start();
	}


	private String toast( String msg ) {
		Toast.makeText( this, msg, Toast.LENGTH_SHORT ).show();
		return msg;
	}
	private String log( String msg ) {
		Log.v( this.getClass().getName() + " -->> ", msg + "" );
		return msg;
	}

}
