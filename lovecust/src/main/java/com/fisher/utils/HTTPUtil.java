package com.fisher.utils;


import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPUtil {
	public static Response request ( String server, String params ) throws IOException {
		URL url = new URL( server );
		HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
		conn.setInstanceFollowRedirects( false );
		conn.setConnectTimeout( 15000 );
		conn.setReadTimeout( 10000 );
		conn.setConnectTimeout( 15000 );
		// set headers
		conn.setRequestProperty( "accept", "*/*" );
		conn.setRequestProperty( "connection", "Keep-Alive" );
		conn.setRequestProperty( "charset", "utf-8" );
		if ( null != params && !"".equals( params ) ) {
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
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

		public HttpURLConnection getConnection ( ) {
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
		public String toString ( ) {
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


	public static void download ( String url, File file, OnDownloadCompleteListener listener ) {
		new AsyncDownload( url, file, listener ).start();
	}


	public interface OnDownloadCompleteListener {
		void onCompleted ( File file );

		void onFailed ( );
	}

	static class AsyncDownload extends Thread {
		private final String url;
		private final File file;
		private final AsyncHandler handler;

		public AsyncDownload ( String url, File file, OnDownloadCompleteListener listener ) {
			this.url = url;
			this.file = file;
			handler = new AsyncHandler( listener );
		}

		@Override
		public void run ( ) {
			try {
				HttpURLConnection conn = ( HttpURLConnection ) new URL( url ).openConnection();
				conn.setConnectTimeout( 5000 );
				conn.setReadTimeout( 10000 );
				conn.setConnectTimeout( 15000 );
				conn.setRequestProperty( "accept", "*/*" );
				conn.setRequestProperty( "connection", "Keep-Alive" );
				conn.setRequestProperty( "charset", "utf-8" );
				ConsoleUtil.console( "HTTPUtil -> now requesting: " + url );
				int code = conn.getResponseCode();
				if ( HttpURLConnection.HTTP_OK == code ) {
					InputStream in = conn.getInputStream();
					FileUtil.writeFile( file, in );
					handler.onResponse( file );
				} else {
					ConsoleUtil.warning( "Failed to download file with statusCode: " + code );
					handler.onError();
				}
			} catch ( IOException e ) {
				e.printStackTrace();
				handler.onError();
			}
		}
	}

	static class AsyncHandler extends Handler {
		private static final int MESSAGE_CODE_ERROR = -1;
		private static final int MESSAGE_CODE_OK = 1;
		private OnDownloadCompleteListener listener;
		private File file;

		public AsyncHandler ( OnDownloadCompleteListener listener ) {
			this.listener = listener;
		}

		public void onResponse ( File file ) {
			this.file = file;
			sendEmptyMessage( MESSAGE_CODE_OK );
		}

		public void onError ( ) {
			sendEmptyMessage( MESSAGE_CODE_ERROR );
		}

		@Override
		public void handleMessage ( Message msg ) {
			if ( null == listener ) {
				ConsoleUtil.warning( "APIHandler.handleMessage()-> feedback is null, when call async api!" );
				return;
			}
			if ( 1 == msg.what )
				listener.onCompleted( file );
			else
				listener.onFailed();
			super.handleMessage( msg );
		}
	}


}
