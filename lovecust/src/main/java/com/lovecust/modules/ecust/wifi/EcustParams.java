package com.lovecust.modules.ecust.wifi;


import android.util.Base64;

import com.fisher.utils.constants.FileConstant;
import com.google.gson.Gson;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.URLUtil;
import com.lovecust.constants.EcustWifiConstant;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class EcustParams {

	private final String redirect;
	public String user_ip = "";
	private String nas_ip = "";
	private String user_mac = "";
	private int ac_id = 16;

	public static EcustParams getInstance ( String redirect ) {
		return new EcustParams( redirect ).init();
	}

	private EcustParams ( String redirect ) {
		this.redirect = redirect;
	}

	private EcustParams init ( ) {
		String path = redirect.substring( redirect.indexOf( '?' ) + 1 );
		String[] params = path.split( "&" );
		for ( String param : params ) {
			if ( "".equals( param ) )
				continue;
			String[] arr2 = param.split( "=" );
			switch ( arr2[ 0 ] ) {
				case "ip":
					user_ip = arr2[ 1 ];
					break;
			}
		}
		return this;
	}


	private String getPostString ( ) throws UnsupportedEncodingException {
		SettingWifi wifi = SettingWifi.getInstance();
		HashMap< String, String > map = new HashMap<>();
		map.put( "action", "login" );
		if ( wifi.getUsername().endsWith( EcustWifiConstant.UID_POSTFIX ) ) {
			map.put( "username", wifi.getUsername() );
		} else {
			map.put( "username", wifi.getUsername() + EcustWifiConstant.UID_POSTFIX );
		}
		map.put( "password", ConsoleUtil.console( EcustWifiConstant.PWD_PREFIX + new String( Base64.encode( wifi.getPassword().getBytes( FileConstant.CHARSET_UTF_8 ), Base64.DEFAULT ), FileConstant.CHARSET_UTF_8 ) ) );
		map.put( "ac_id", String.valueOf( ac_id ) );
		map.put( "user_ip", user_ip );
		map.put( "nas_ip", nas_ip );
		map.put( "mac", user_mac );
		map.put( "ajax", String.valueOf( 1 ) );
		Logger.i( new Gson().toJson( this ) );
		return URLUtil.getPostDataString( map );
	}


	public boolean connect ( ) throws IOException {
		APILib.Response response = APILib.request( EcustWifiConstant.URL_ECUST_WIFI_LOGIN, getPostString() );
		return response.response.contains( EcustWifiConstant.LOGIN_SUCCEEDED );
	}
}
