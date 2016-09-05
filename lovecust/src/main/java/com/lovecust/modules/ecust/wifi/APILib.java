package com.lovecust.modules.ecust.wifi;

import com.lovecust.network.ApiManager;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class APILib {
	public static String URL_ECUST_WIFI_LOGIN = "http://172.20.13.100/cgi-bin/srun_portal";
	public static String URL_ECUST_WIFI_CHECK = "http://172.20.13.100/cgi-bin/rad_user_info";
	public static String URL_SERVER = "http://api.lovecust.com";
	public static String URL_REDIRECT_BAIDU = "http://www.baidu.com/";
//	public static String URL_REDIRECT_BAIDU = "http://www.baidu.com/&arubalp=221e86ec-1a18-4a48-8d6e-c5f4ef09d0";
	public static String URL_CHECK_INTERNET = URL_SERVER + "/core-apis/check-internet.php";

	public static Response request ( String server ) throws IOException {
		return request( server, null );
	}

	public static Response request ( String server, String params ) throws IOException {
		URL url = new URL( server );
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects( false );
		conn.setConnectTimeout( 15000 );
		conn.setReadTimeout( 10000 );
		conn.setConnectTimeout( 15000 );
		// set headers
		// TODO trim headers
		conn.addRequestProperty( "agent", ApiManager.getAgent() );
		conn.addRequestProperty( "lovecust", ApiManager.getHeader() );
		if ( null != params && !"".equals( params ) ) {
			conn.setRequestMethod( "POST" );
			conn.setDoOutput( true );
			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
			writer.write( params );
			writer.flush();
			writer.close();
			os.close();
		}
		ConsoleUtil.console( "HTTPUtil.login(server, params)-> now requesting: " + server );
		int code = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		String response = FileUtil.getString( in );
		return new Response( code, response ).setConnection( conn );
	}


	public static class Response {
		public int code;
		public String response;
		public HttpURLConnection connection;

		public HttpURLConnection getConnection () {
			return connection;
		}

		public Response setConnection ( HttpURLConnection connection ) {
			this.connection = connection;
			return this;
		}

		public Response ( int code, String response ) {
			this.code = code;
			this.response = response;
		}

		@Override
		public String toString () {
			JSONObject json = new JSONObject();
			try {
				json.put( "code", code );
				json.put( "response", response );
			} catch ( JSONException e ) {
				e.printStackTrace();
			}
			return json.toString();
		}
	}


}
