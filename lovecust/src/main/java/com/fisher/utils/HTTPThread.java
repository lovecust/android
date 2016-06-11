package com.fisher.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HTTPThread extends Thread {
	public static final int CONNECT_TIMEOUT = 10000;
	public static final int READ_TIMEOUT = 10000;
	public static final String HTTP_CHARSET = "UTF-8";

	private Handler handler;
	private String sURL;

	public static void fnRequestHTTPGet( OnHTTPResponseListener onHTTPResponseListener, String url ) {
		new HTTPThread( onHTTPResponseListener, url ).start();
	}

	private HTTPThread( OnHTTPResponseListener onHTTPResponseListener, String url ) {
		this.handler = new HTTPHandler( onHTTPResponseListener );
		this.sURL = url;
	}

	@Override
	public void run( ) {
		try {
			String response = "";
			URL url = new URL( sURL );
			HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
			conn.setConnectTimeout( CONNECT_TIMEOUT );
			conn.setReadTimeout( READ_TIMEOUT );
			conn.setRequestMethod( "GET" );
			conn.setRequestProperty( "accept", "*/*" );
			conn.setRequestProperty( "connection", "Keep-Alive" );
			if ( conn.getResponseCode() == 200 ) {
				InputStream is = conn.getInputStream();
				response = StreamUtil.getString( is, HTTP_CHARSET );
			} else {
				response = "Failed to load assets";
			}

			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString( HTTPHandler.MESSAGE_KEY, response );
			msg.setData( bundle );
			handler.sendMessage( msg );

		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}

	}

	public class HTTPHandler extends Handler {
		public static final String MESSAGE_KEY = "msg";

		private OnHTTPResponseListener process;

		public HTTPHandler( OnHTTPResponseListener processer ) {
			this.process = processer;
		}

		@Override
		public void handleMessage( Message msg ) {
			Bundle bundle = msg.getData();
			String result = bundle.getString( MESSAGE_KEY );
			process.onHTTPGetResponse( result );
			super.handleMessage( msg );
		}
	}


	public interface OnHTTPResponseListener {
		void onHTTPGetResponse ( String result );
	}
}
