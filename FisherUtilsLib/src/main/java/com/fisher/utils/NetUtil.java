package com.fisher.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.google.gson.Gson;



import org.json.JSONException;
import org.json.JSONObject;

public class NetUtil {
	public static String ip = ""; // ip in string form
	public static int ipInt = 0; // ip in integer form
	public static String mac = "";
	public static String ssid = "";
	public static boolean isConnected = false;
	public static boolean isWifi = false;
	// basic service set identifier (BSSID) of the current access point.
	private static String bssid = "";
	// the current frequency in FREQUENCY_UNITS
	private static int frequency = 0;
	// the current link speed in LINK_SPEED_UNITS
	private static int linkSpeed = 0;
	// Each configured network has a unique small integer ID, used to identify the network when performing operations on the supplicant
	private static int netWorkId = 0;
	// the received signal strength indicator of the current 802.11 network, in dBm.
	private static int rssi = 0;
	private static WifiInfo wifiInfo;

	public String toString () {
		return new Gson().toJson( this );
	}

	private static void clear () {
		ip = mac = ssid = bssid = "";
		ipInt = frequency = linkSpeed = netWorkId = rssi = 0;
		wifiInfo = null;
	}

	public static void flush () {
		Context context = AppUtil.getContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		WifiManager wifiManager = (WifiManager) context.getSystemService( Context.WIFI_SERVICE );
		// info will be null if the internet is not available
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		wifiInfo = wifiManager.getConnectionInfo();

		if ( null != info && info.isConnected() ) {
			isConnected = true;
			isWifi = ( info.getType() == ConnectivityManager.TYPE_WIFI );
		} else {
			isConnected = false;
			isWifi = false;
		}

		ipInt = wifiInfo.getIpAddress();
		ip = ( ( ipInt & 0xff ) + "." + ( ipInt >> 8 & 0xff ) + "." + ( ipInt >> 16 & 0xff ) + "." + ( ipInt >> 24 & 0xff ) );
		mac = wifiInfo.getMacAddress();
		ssid = wifiInfo.getSSID();
		bssid = wifiInfo.getBSSID();
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
			frequency = wifiInfo.getFrequency();
		}
		linkSpeed = wifiInfo.getLinkSpeed();
		netWorkId = wifiInfo.getNetworkId();
		rssi = wifiInfo.getRssi();
	}


	public static String toReadableString () {
		JSONObject json = new JSONObject();
		try {
			json.put( "ip", ip );
			json.put( "ipInt", ipInt );
			json.put( "mac", mac );
			json.put( "ssid", ssid );
			json.put( "isConnected", isConnected );
			json.put( "isWifi", isWifi );
			json.put( "bssid", bssid );
			json.put( "frequency", frequency );
			json.put( "linkSpeed", linkSpeed );
			json.put( "netWorkId", netWorkId );
			json.put( "rssi", rssi );
			json.put( "wifiInfo", wifiInfo.toString() );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		return json.toString();
	}


	public static boolean isOfflineAndToast ( ) {
		if(!isConnected)
		ToastUtil.toast(R.string.toast_global_device_offline_check_internet );
		return !isConnected;
	}
}
