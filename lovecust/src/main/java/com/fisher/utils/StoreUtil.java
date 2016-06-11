package com.fisher.utils;

import android.util.Log;

public class StoreUtil {
	public static final String sDefaultStoreName = "StoreDefault";

	private String name;

	public static StoreUtil getStore( String name ) {
		if ( name == null || name.equals( "" ) ) name = sDefaultStoreName;
		return new StoreUtil( name );
	}
	public static StoreUtil getStore( ) {
		return new StoreUtil( StoreUtil.sDefaultStoreName );
	}

	private StoreUtil( String name ) {
		this.name = name;
	}


	public String getString( String key ) {
		return null;
	}
	public void setString( String key, String value ) {
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
	public void clear( ) {

	}
	private String log( String msg ) {
		Log.v( this.getClass().getName() + " -->> ", msg + "" );
		return msg;
	}
}
