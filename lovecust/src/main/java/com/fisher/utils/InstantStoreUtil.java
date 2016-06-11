package com.fisher.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/*
* only for the rare edittext view to store the last input
* */
public class InstantStoreUtil {
	public static final String INSTANT_STORE_FILE_NAME = "app/InstantStore/default.json";
	private static InstantStoreUtil setting;
	private JSONObject json;

	public static InstantStoreUtil getInstance() {
		if ( null == setting ) {
			String json = FileUtil.readFileWithoutException( FileUtil.getInternalFile( INSTANT_STORE_FILE_NAME ) );
			if ( !"".equals( json ) )
				try {
					setting = new InstantStoreUtil( new JSONObject( json ) );
				} catch ( JSONException e ) {
					e.printStackTrace();
					BugsUtil.onFatalError( "InstantStoreUtil.getStore()-> error to get json object!" );
				}
		}
		if ( null == setting )
			setting = new InstantStoreUtil( new JSONObject() );
		return setting;
	}

	private InstantStoreUtil( JSONObject json ) {
		this.json = json;
	}

	public InstantStoreUtil flush() {
		FileUtil.writeFileWithoutException( FileUtil.getInternalFile( INSTANT_STORE_FILE_NAME ), json.toString() );
		return this;
	}

	public String getString( String key ) {
		try {
			return json.getString( key );
		} catch ( Exception e ) {
			e.printStackTrace();
			return "";
		}
	}
	public InstantStoreUtil putString( String key, String value ) {
		try {
			json.put( key, value );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		flush();
		return this;
	}
	public int getInt( String key ) {
		return 0;
	}
	public void setInt( String key, int value ) {
	}

	public String[] getStrings( String key ) {
		return null;
	}
	public void setStrings( String key, String[] strings ) {
	}

	public boolean contains( String key ) {
		return false;
	}
	public void remove( String key ) {
	}
	public void clear() {

	}
	private String log( String msg ) {
		Log.v( this.getClass().getName() + " -->> ", msg + "" );
		return msg;
	}
}
