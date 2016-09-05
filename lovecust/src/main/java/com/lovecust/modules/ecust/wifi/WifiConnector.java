package com.lovecust.modules.ecust.wifi;


import android.app.PendingIntent;
import android.content.Intent;

import com.lovecust.app.R;
import com.lovecust.network.Methods;
import com.lovecust.app.AppContext;
import com.lovecust.app.Setting;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.LogUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.NotificationUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiConnector {
	private static SettingWifi wifi = SettingWifi.getInstance();
	private static Thread connector;
	private static long stime;

	public static void test ( ) {
		try {
//			postNotification( "Connected to Ecust now!", "checking access to Internet!" );
			APILib.Response res = APILib.request( APILib.URL_ECUST_WIFI_CHECK );
			ConsoleUtil.console( res.code + " - " + res.response );
			if ( "not_online".equals( res.response ) ) {
				stime = System.currentTimeMillis();
				login();
			} else {
				ConsoleUtil.console( "Got response: " + res.response );
				for ( int i = 3; i > 0; i-- ) {
					String content = AppUtil.getString( R.string.notice_wifi_notice_disappear_in_n_secs_header ) + i + AppUtil.getString( R.string.notice_wifi_notice_disappear_in_n_secs_footer );
					flushNotification( AppUtil.getString( R.string.notice_wifi_device_is_online ), content );
					Thread.sleep( 1000 );
				}
				NotificationUtil.cancel();
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}

	public static String parseURL ( String text ) {
		Pattern pattern = Pattern.compile( "content='(.+?)'" );
		Matcher matcher = pattern.matcher( text );
		if ( matcher.find() ) {
			String temp = matcher.group( 1 );
			temp = temp.substring( temp.indexOf( "url=" ) + 4 );
			LogUtil.log( "ecust-wifi.log", "parsed url: " + temp );
			return temp;
		} else {
			LogUtil.log( "ecust-wifi.log", "parsed url: find nothing!" );
			ConsoleUtil.console( "find nothing!" );
			return null;
		}
	}


	public static void login ( ) {
		try {
			postNotification( AppUtil.getString( R.string.notice_wifi_device_is_offline ), AppUtil.getString( R.string.notice_wifi_logging_in ) );
			APILib.Response res = APILib.request( APILib.URL_REDIRECT_BAIDU );
			String url = parseURL( res.response );
			if ( null == url || "".equals( url ) ) {
				postError( "Error(-3028): did not detect url expected!", AppUtil.getString( R.string.notice_click_to_retry ) );
				// TODO deal with exception
				BugsUtil.onFatalError( "Url is not detected (-3028)", res.code + ": " + res.response );
				return;
			}
			res = APILib.request( url );
			if ( res.code != HttpURLConnection.HTTP_OK ) {
				if ( res.code == HttpURLConnection.HTTP_MOVED_TEMP || res.code == HttpURLConnection.HTTP_MOVED_PERM || res.code == HttpURLConnection.HTTP_SEE_OTHER ) {
					String direction = res.getConnection().getHeaderField( "Location" );
					String cookies = res.getConnection().getHeaderField( "Set-Cookie" );
					ConsoleUtil.console( "ActivityEcustWifiHome.login()-> direction: " + direction + "; cookies: " + cookies + "; response: " + res.response );
					boolean status = EcustParams.getInstance( direction ).connect();
					APILib.Response response = APILib.request( Methods.server + Methods.APP_INTERNET_STATUS );
					if ( status && response.code == HttpURLConnection.HTTP_OK ) {
						if ( AppContext.mDebug ) {
							if ( !"{\"code\":0}".equals( response.response ) ) {
								String temp = ConsoleUtil.console( "Unexpected response: " + response.response );
								BugsUtil.onFatalError( "EcustParams.login()-> " + temp );
							} else {
								LogUtil.log( "fisher.log", "got okay!" );
							}
						}
						for ( int i = 8; i > 0; i-- ) {
							String content = AppUtil.getString( R.string.notice_wifi_notice_disappear_in_n_secs_header ) + i + AppUtil.getString( R.string.notice_wifi_notice_disappear_in_n_secs_footer );
							flushNotification( AppUtil.getString( R.string.notice_wifi_logged_in ), content );
							Thread.sleep( 1000 );
						}
						NotificationUtil.cancel();
					} else {
						// TODO click to try again
						postError( "failed to log in Ecust Wifi!", AppUtil.getString( R.string.notice_click_to_retry ) );
					}

				} else {
					postError( "ecust error happened, got code: " + res.code, AppUtil.getString( R.string.notice_click_to_retry ) );
				}
			} else {
				postError( "ecust server error, code: " + res.code, AppUtil.getString( R.string.notice_click_to_retry ) );
			}
		} catch ( IOException | InterruptedException e ) {
			e.printStackTrace();
		}

	}

	public static void check ( ) {
		if ( !( NetUtil.isConnected && NetUtil.isWifi ) ) {
			ConsoleUtil.console( "wifi is offline" );
			return;
		}
		ConsoleUtil.console( "connected to wifi" );
		if ( !( wifi.isAutoConnect() && SettingWifi.SSID_ECUST.equals( NetUtil.ssid ) ) ) {
			ConsoleUtil.console( "auto connection is denied or wifi is not ECUST: " + NetUtil.ssid );
			return;
		}
		NotificationUtil.cancel();
		if ( null == connector || connector.getState() == Thread.State.TERMINATED ) {
			connector = new Thread() {
				@Override
				public void run ( ) {
					test();
				}
			};
			connector.start();
		} else {
			ConsoleUtil.console( "WifiConnector.check()-> The old one is still alive or running and hence do not create new Thread" );
		}
	}

	public static void flushNotification ( String title, String detail ) {
		if ( wifi.isSendNotification() ) {
			NotificationUtil.sendNotification( title, detail );
		}
	}

	public static void postNotification ( String title, String detail ) {
		if ( wifi.isSendNotification() ) {
			NotificationUtil.cancel();
			NotificationUtil.sendNotification( title, detail );
		}
	}

	public static void postError ( String title, String detail ) {
		PendingIntent intent = PendingIntent.getBroadcast(
				AppContext.getContext(), 0
				, new Intent( Setting.ACTION_ECUST_WIFI_RECONNECT )
				, PendingIntent.FLAG_UPDATE_CURRENT );
		if ( wifi.isSendNotification() ) {
			NotificationUtil.cancel();
			NotificationUtil.sendNotification( title, detail, intent );
		}
	}

}
