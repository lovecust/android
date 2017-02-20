package com.lovecust.modules.ecust.wifi;

import android.text.TextUtils;

import com.ecust.wifi.IWifiConnector;
import com.ecust.wifi.WifiLibrary;
import com.ecust.wifi.EcustWifiConstant;
import com.fisher.utils.URLUtil;
import com.fisher.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * `Created` by Fisher at 17:22 on 2017-02-19.
 * <p>
 * Wifi connector.
 */
public class WifiConnector {

	private static SettingWifi wifi = SettingWifi.getInstance();
	private static Thread connector;

	/**
	 * Check whether device is connected to Ecust.
	 * <p>
	 * If connected to Ecust, check connection internetStatus and login if device is offline
	 */
	public static void checkConnection(IWifiConnector listener) {
		// Wifi is not connected!
		if (!NetUtil.isConnected || !NetUtil.isWifi) {return;}
		// Wifi auto connection is disabled; Or connected wifi ssid is not Ecust;
		if (!wifi.isAutoConnect() || !EcustWifiConstant.ECUST_SSID.equals(NetUtil.ssid)) {return;}
		// Only when the old one is not alive or running, we create new connection and connect;
		if (null != connector && connector.getState() != Thread.State.TERMINATED) {return;}
		listener.onCheckInternetStatus();
		connector = new Thread() {
			@Override
			public void run() {
				try {
					if (WifiLibrary.internetStatus()) {
						listener.onDeviceIsOffline();
						loginNow(listener);
					} else {
						listener.onDeviceIsOnline();
					}
				} catch (IOException e) {
					e.printStackTrace();
					listener.onRequestError();
				}
			}
		};
		connector.start();
	}


	/**
	 * Login now
	 */
	public static void loginNow(IWifiConnector listener) {
		try {
			long startTime = System.currentTimeMillis();
			String redirectedUrl = WifiLibrary.getRedirectedURL();
			if (TextUtils.isEmpty(redirectedUrl)) {
				listener.onFailedToGetRedirectedUrl();
				return;
			}
			redirectedUrl = WifiLibrary.followRedirectURL(redirectedUrl);
			if (TextUtils.isEmpty(redirectedUrl)) {
				listener.onFailedToFollowRedirection();
				return;
			}
			String acID = WifiLibrary.getACID(redirectedUrl);
			if (TextUtils.isEmpty(acID)) {
				listener.onFailedToGetACID();
				return;
			}
			JSONObject json = URLUtil.getJSONFromURLEncoded(redirectedUrl);
			if (WifiLibrary.doLogin(wifi.getUsername(), wifi.getPassword(), Integer.parseInt(acID), json.getString("ip"), json.getString("mac"))) {
				long usingTime = (System.currentTimeMillis() - startTime);
				listener.onLoggedIn(usingTime, acID);
			} else {
				listener.onFailedToLogIn();
			}
		} catch (IOException e) {
			e.printStackTrace();
			listener.onRequestError();
		} catch (JSONException e) {
			e.printStackTrace();
			listener.onClientError();
		}
	}

}
