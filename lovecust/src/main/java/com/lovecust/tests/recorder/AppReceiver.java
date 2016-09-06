package com.lovecust.tests.recorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lovecust.modules.ecust.wifi.WifiConnector;
import com.lovecust.app.AppContext;
import com.lovecust.app.Setting;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.NetUtil;


public class AppReceiver extends BroadcastReceiver {


	@Override
	public void onReceive ( Context context, Intent intent ) {
		switch ( intent.getAction() ) {
			case ConnectivityManager.CONNECTIVITY_ACTION:
			case Setting.ACTION_ECUST_WIFI_RECONNECT:
				NetUtil.flush();
				ConsoleUtil.console( NetUtil.toReadableString() );
				WifiConnector.checkConnection();
				break;
			case Intent.ACTION_BATTERY_CHANGED:
				AppContext.mBatteryLevel = intent.getIntExtra( "scale", -1 );
				break;
			case Intent.ACTION_TIME_TICK:
				break;

			case Intent.ACTION_SCREEN_ON:
				AppContext.mStateScreen = true;
				break;
			case Intent.ACTION_SCREEN_OFF:
				AppContext.mStateScreen = false;
				break;
			case Intent.ACTION_POWER_CONNECTED:
				AppContext.mStateBatteryCharging = true;
				break;
			case Intent.ACTION_POWER_DISCONNECTED:
				AppContext.mStateBatteryCharging = false;
				break;
			case Intent.ACTION_HEADSET_PLUG:
				int state = intent.getIntExtra( "state", -1 );
				if ( state == 0 ) {
					AppContext.mStateMicroPhone = false;
				} else if ( state == 1 ) {
					AppContext.mStateMicroPhone = true;
				}
				break;
			default:
				break;
		}
	}


}
