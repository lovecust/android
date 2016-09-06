package com.lovecust.constants;

/**
 * Created on 9/5/2016 at 22:40
 * By Fisher
 * <p>
 * Ecust Wifi Constant
 */
public class EcustWifiConstant {
	/**
	 * Baidu to get the recommended redirected url.
	 */
	public static final String URL_BAIDU = "http://www.baidu.com/";
	/**
	 * Login address to post the login data.
	 */
	public static final String URL_ECUST_WIFI_LOGIN = "http://172.20.3.81:801/include/auth_action.php";


	public static final String STATUS_NOT_ONLINE = "not_online";

	/**
	 * If login address returns string contains this char sequence, then Login Succeeded!
	 */
	public static final CharSequence LOGIN_SUCCEEDED = "login_ok";


	/**
	 * Ecust Wifi Authorization Password prefix.
	 */
	public static final String PWD_PREFIX = "{B}";
	/**
	 * Ecust Wifi Authorization User ID postfix.
	 */
	public static final String UID_POSTFIX = "@free";

	/**
	 * Ecust wifi SSID
	 */
	public static final String ECUST_SSID = "\"ECUST\"";
}
