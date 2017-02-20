package com.lovecust.modules.ecust.wifi;

import android.app.PendingIntent;
import android.content.Intent;

import com.ecust.wifi.IWifiConnector;
import com.fisher.utils.AppUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.NotificationUtil;
import com.lovecust.app.AppContext;
import com.lovecust.app.R;
import com.lovecust.app.Setting;
import com.lovecust.utils.NetworkManager;

/**
 * Created by fisher at 2:58 PM on 2/19/17.
 * <p>
 * Event Listener.
 */

public class SimpleWifiListener extends IWifiConnector {

	private static SettingWifi wifi = SettingWifi.getInstance();
	private static SimpleWifiListener listener;

	public static SimpleWifiListener getInstance() {
		if (null == listener) {listener = new SimpleWifiListener();}
		return listener;
	}

	@Override
	public void onCheckInternetStatus() {
		NotificationUtil.cancel();
	}

	@Override
	public void onDeviceIsOnline() {
		// Show notification for online;
		for (int i = 3; i > 0; i--) {
			String content = AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_header) + i + AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_footer);
			flushNotification(AppUtil.getString(R.string.notice_wifi_device_is_online), content);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		NotificationUtil.cancel();
	}

	@Override
	public void onDeviceIsOffline() {
		postNotification(AppUtil.getString(R.string.notice_wifi_device_is_offline), AppUtil.getString(R.string.notice_wifi_logging_in));
	}

	@Override
	public void onFailedToGetRedirectedUrl() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_error_failed_to_get_redirected_url), AppUtil.getString(R.string.notice_click_to_retry));
	}

	@Override
	public void onFailedToFollowRedirection() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_error_failed_to_follow_redirection), AppUtil.getString(R.string.notice_click_to_retry));
	}

	@Override
	public void onFailedToGetACID() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_error_failed_to_get_AC_ID), AppUtil.getString(R.string.notice_click_to_retry));
	}

	@Override
	public void onLoggedIn(Long usingTime, String acID) {
		NetworkManager.getLovecustNetworkManager()
				.getInternetStatus(usingTime, acID)
				.subscribe(status -> {
					if (!status.isOkay()) {
						ConsoleUtil.error(AppUtil.getString(R.string.notice_ecust_wifi_failed_to_login));
					}
				}, Throwable::printStackTrace);
		for (int i = 8; i > 0; i--) {
			String content = AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_header) + i + AppUtil.getString(R.string.notice_wifi_notice_disappear_in_n_secs_footer);
			flushNotification(String.format(AppUtil.getString(R.string.notice_wifi_logged_in_with_time), usingTime / 1000.0), content);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		NotificationUtil.cancel();
	}

	@Override
	public void onFailedToLogIn() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_failed_to_login), AppUtil.getString(R.string.notice_click_to_retry));
	}

	@Override
	public void onRequestError() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_error_request_error), AppUtil.getString(R.string.notice_click_to_retry));
	}

	@Override
	public void onClientError() {
		noticeError(AppUtil.getString(R.string.notice_ecust_wifi_error_client_error), AppUtil.getString(R.string.notice_click_to_retry));
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
