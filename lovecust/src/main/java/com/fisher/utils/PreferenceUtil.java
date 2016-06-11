package com.fisher.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;

// TODO used as the parent class for single instance
public class PreferenceUtil {

	public static PreferenceUtil setting;


	public static < T extends PreferenceUtil > T getInstance ( Class< T > t, File file ) {
		if ( null != setting )
			return (T) setting;
		String json = FileUtil.readFileWithoutException( file );
		if ( !"".equals( json ) ) {
			try {
				setting = new Gson().fromJson( json, t );
			} catch ( JsonSyntaxException e ) {
				e.printStackTrace();
				BugsUtil.onFatalError( "PreferenceUtil.flush()-> json string format failed[ configure edited ]!" );
			}
		}
		if ( null == setting ) {
			try {
				setting = t.newInstance();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return (T) setting;
	}

	public static File getInternalFile ( String filename ) {
		return FileUtil.getInternalFile( filename );
	}

	public static void flush ( File file ) {
		FileUtil.writeFileWithoutException( file, setting.toString() );
	}

	@Override
	public String toString () {
		return new Gson().toJson( this );
	}


}
