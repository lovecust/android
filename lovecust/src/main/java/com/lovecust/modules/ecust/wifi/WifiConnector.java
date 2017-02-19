package com.lovecust.modules.ecust.wifi;


import android.app.PendingIntent;
import android.content.Intent;
import android.text.TextUtils;

import com.lovecust.app.R;
import com.lovecust.constants.EcustWifiConstant;
import com.lovecust.network.LovecustNetworkManager;
import com.lovecust.network.NetworkConstant;
import com.lovecust.app.AppContext;
import com.lovecust.app.Setting;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.LogUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.NotificationUtil;
import com.lovecust.utils.NetworkManager;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiConnector {
	private static SettingWifi wifi = SettingWifi.getInstance();
	private static Thread connector;
	private static long stime;


	/**
	 * Check whether device is connected to Ecust
	 * <p>
	 * If connected to Ecust, check connection status and login if device is offline
	 */
	public static void checkConnection() {
		if (!(NetUtil.isConnected && NetUtil.isWifi))
			// Wifi is not connected!
			return;
		if (!(wifi.isAutoConnect() && EcustWifiConstant.ECUST_SSID.equals(NetUtil.ssid)))
			// Wifi auto connection is disabled; Or connected wifi ssid is not Ecust;
			return;
		NotificationUtil.cancel();
		if (null != connector && connector.getState() != Thread.State.TERMINATED)
			return;
		// Only when the old one is not alive or running, we create new connection and connect;
		connector = new Thread() {
			@Override
			public void run() {
				try {
					APILib.Response res = APILib.request(APILib.URL_ECUST_WIFI_CHECK);
					if (EcustWifiConstant.STATUS_NOT_ONLINE.equals(res.response)) {
						loginNow();
					} else {
						// Show notification for online;
						for (int i = 3; i > 0; i--) {
							String content = AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_header) + i + AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_footer);
							flushNotification(AppUtil.getString(R.string.notice_wifi_device_is_online), content);
							Thread.sleep(1000);
						}
						NotificationUtil.cancel();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		connector.start();
	}


	/**
	 * Login now
	 */
	public static void loginNow() {
		try {
			stime = System.currentTimeMillis();
			postNotification(AppUtil.getString(R.string.notice_wifi_device_is_offline), AppUtil.getString(R.string.notice_wifi_logging_in));
			APILib.Response res = APILib.request(EcustWifiConstant.URL_BAIDU);
			String url = parseURL(res.response);
			if (TextUtils.isEmpty(url)) {
				noticeError(AppUtil.getString(R.string.notice_ecust_wifi_server_error_with_code), AppUtil.getString(R.string.notice_click_to_retry));
				BugsUtil.onFatalError(AppUtil.getString(R.string.notice_ecust_wifi_server_error_with_code), res.code + ": " + res.response);
				return;
			}
			res = APILib.request(url);
			if (res.code != HttpURLConnection.HTTP_OK) {
				if (res.code == HttpURLConnection.HTTP_MOVED_TEMP || res.code == HttpURLConnection.HTTP_MOVED_PERM || res.code == HttpURLConnection.HTTP_SEE_OTHER) {
					String direction = res.getConnection().getHeaderField("Location");
					if (WifiLoggingInHelper.getInstance(direction).connect()) {
						NetworkManager.getLovecustNetworkManager()
								.getInternetStatus()
								.subscribe(status -> {
									if (AppContext.mDebug && status.isOkay()) {
										LogUtil.log("fisher.log", "got okay!");
									}
								}, err -> {
									err.printStackTrace();
									String temp = ConsoleUtil.console("Unexpected response: " + err.getMessage());
									BugsUtil.onFatalError("WifiLoggingInHelper.login()-> " + temp);
								});
						double usingTime = (System.currentTimeMillis() - stime) / 1000.0;
						for (int i = 8; i > 0; i--) {
							String content = AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_header) + i + AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_footer);
							flushNotification(String.format(AppUtil.getString(R.string.notice_wifi_logged_in_with_time), usingTime), content);
							Thread.sleep(1000);
						}
						NotificationUtil.cancel();
					} else {
						noticeError(AppUtil.getString(R.string.notice_ecust_wifi_failed_to_login), AppUtil.getString(R.string.notice_click_to_retry));
					}

				} else {
					noticeError(AppUtil.getString(R.string.notice_ecust_wifi_server_error_with_code) + res.code, AppUtil.getString(R.string.notice_click_to_retry));
				}
			} else {
				noticeError(AppUtil.getString(R.string.notice_ecust_wifi_server_error_with_code) + res.code, AppUtil.getString(R.string.notice_click_to_retry));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Parse the redirecting url
	 *
	 * @param text the response body
	 * @return url parsed or null
	 */
	public static String parseURL(String text) {
		Pattern pattern = Pattern.compile("content='(.+?)'");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String temp = matcher.group(1);
			temp = temp.substring(temp.indexOf("url=") + 4);
			LogUtil.log("ecust-wifi.log", "parsed url: " + temp);
			return temp;
		} else {
			LogUtil.log("ecust-wifi.log", "parsed url: find nothing!");
			return null;
		}
	}


	public static void flushNotification(String title, String detail) {
		if (wifi.isSendNotification()) {
			NotificationUtil.sendNotification(title, detail);
		}
	}

	public static void postNotification(String title, String detail) {
		if (wifi.isSendNotification()) {
			NotificationUtil.cancel();
			NotificationUtil.sendNotification(title, detail);
		}
	}

	/**
	 * Show notification to notice user the error and try to connect if permitted
	 *
	 * @param title  Title to be shown;
	 * @param detail Subtitle to be shown;
	 */
	public static void noticeError(String title, String detail) {
		PendingIntent intent = PendingIntent.getBroadcast(
				AppContext.getContext(), 0
				, new Intent(Setting.ACTION_ECUST_WIFI_RECONNECT)
				, PendingIntent.FLAG_UPDATE_CURRENT);
		if (wifi.isSendNotification()) {
			NotificationUtil.cancel();
			NotificationUtil.sendNotification(title, detail, intent);
		}
	}

}
