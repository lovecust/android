package com.lovecust.modules.ecust.wifi;


import com.google.gson.Gson;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.URLUtil;

import java.io.IOException;
import java.util.HashMap;

public class EcustParams {


	private final String redirect;
	public String wlanacname = "";
	public String action = "";
	public String user_ip = "";
	private String ssid = "";
	private String rad_type = "";
	private String nas_ip = "";
	private String mac = "";
	private String is_debug = "";
	private String ac_type = "";
	private String local_auth = "";
	private String vlan = "";
	private String ac_id = "3";

	public static EcustParams getInstance ( String redirect ) {
		return new EcustParams( redirect ).init();
	}

	private EcustParams ( String redirect ) {
		this.redirect = redirect;
	}

	private EcustParams init () {
		String path = redirect.substring( redirect.indexOf( '?' ) + 1 );
		String[] params = path.split( "&" );
		for ( int i = 0; i < params.length; i++ ) {
			if ( "".equals( params[ i ] ) )
				continue;
			String[] arr2 = params[ i ].split( "=" );
			if ( "wlanacname".equals( arr2[ 0 ] ) ) {
				wlanacname = arr2[ 1 ];
			} else if ( "wlanuserip".equals( arr2[ 0 ] ) || "ip".equals( arr2[ 0 ] ) ) {
				user_ip = arr2[ 1 ];
			} else if ( "ssid".equals( arr2[ 0 ] ) ) {
				ssid = arr2[ 1 ];
			} else if ( "vlan".equals( arr2[ 0 ] ) ) {
				vlan = arr2[ 1 ];
			} else if ( "portal_ip".equals( arr2[ 0 ] ) || "switchip".equals( arr2[ 0 ] ) ) {
				nas_ip = arr2[ 1 ];
			} else if ( "client_id".equals( arr2[ 0 ] ) || "mac".equals( arr2[ 0 ] ) ) {
				mac = arr2[ 1 ];
			} else if ( "wbaredirect".equals( arr2[ 0 ] ) || "userurl".equals( arr2[ 0 ] ) || "URL".equals( arr2[ 0 ] ) || "url".equals( arr2[ 0 ] ) ) {
				//jump_to = arr2[1];
			} else if ( "is_debug".equals( arr2[ 0 ] ) ) {
				is_debug = arr2[ 1 ];
			} else if ( "ac_type".equals( arr2[ 0 ] ) ) {
				ac_type = arr2[ 1 ];
			} else if ( "rad_type".equals( arr2[ 0 ] ) ) {
				rad_type = arr2[ 1 ];
			} else if ( "local_auth".equals( arr2[ 0 ] ) ) {
				local_auth = arr2[ 1 ];
			} else if ( "ac_id".equals( arr2[ 0 ] ) ) {
				ac_id = arr2[ 1 ];
			} else if ( "action".equals( arr2[ 0 ] ) ) {

			}
		}
		return this;
	}


	private String getPostString () {
		SettingWifi wifi = SettingWifi.getInstance();
		HashMap< String, String > map = new HashMap<>();
		if ( "".equals( ac_id ) )
			ac_id = "3";
		map.put( "action", "login" );
		// TODO username and password
		if ( wifi.getUsername().endsWith( "@free" ) ) {
			map.put( "username", wifi.getUsername() );
		} else {
			map.put( "username", wifi.getUsername() + "@free" );
		}
		map.put( "password", wifi.getPassword() );
		map.put( "ac_id", ac_id );
		map.put( "type", "1" );
		map.put( "wbaredirect", "" );
//		map.put( "mac", mac );
		map.put( "mac", "undefined" );
		map.put( "user_ip", user_ip );
		map.put( "is_ldap", "1" );
		map.put( "nas_ip", nas_ip );
		ConsoleUtil.console( new Gson().toJson( this ) );
		return URLUtil.getPostDataString( map );
	}


	public boolean connect () throws IOException {
		APILib.Response response = APILib.request( APILib.URL_ECUST_WIFI_LOGIN, getPostString() );
		ConsoleUtil.console( "get response: " + response );
		if ( response.response.contains( "/srun_portal.html?action=login_ok" ) ) {
			return true;
		} else {
			return false;
		}
	}
}
