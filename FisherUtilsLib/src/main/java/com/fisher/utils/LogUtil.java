package com.fisher.utils;


import android.support.annotation.StringRes;
import android.util.Log;

import com.fisher.utils.constants.FileConstant;

import java.util.HashMap;

/**
 * Created by Fisher before 2016-09-06 09:41
 * <p>
 * Custom LogUtil
 * Log something when in debug mode.
 */
public class LogUtil {

	public static final String FILE_NAME_LOG_DEFAULT = "default.log";
	private static PermanentUtil util;
	private static HashMap< String, PermanentUtil > utils = new HashMap<>();
	/**
	 * Log Tag
	 */
	private static final String LOG_TAG = "Lovecust";

	public static String log ( String msg ) {
		ConsoleUtil.console( msg );
		if ( !AppUtil.isDebug() ) {
			return msg;
		}
		if ( null == util )
			util = PermanentUtil.get( FILE_NAME_LOG_DEFAULT );
		util.write( TimeUtil.fnFormatTime() + " -> \r\n" + msg + "\r\n\r\n" );
		return msg;
	}

	public static void release ( ) {
		util.close();
		util = null;
		for ( String key : utils.keySet() ) {
			utils.get( key ).close();
		}
		utils.clear();
	}


	public static String log ( String filename, String msg ) {
		ConsoleUtil.console( msg );
		if ( !AppUtil.isDebug() ) {
			return msg;
		}
		PermanentUtil util = utils.get( filename );
		if ( null == util ) {
			util = PermanentUtil.get( filename );
			utils.put( filename, util );
		}
		util.write( msg + FileConstant.SEPARATOR );
		return msg;
	}

	/**
	 * Log some errors that you should handle imediate
	 *
	 * @param error Error info
	 * @return the same error string
	 */
	public static String e ( String error ) {
		if ( AppUtil.isDebug() )
			Log.e( LOG_TAG, error );
		return error;
	}

	/**
	 * Log some errors that you should handle imediate
	 *
	 * @param errorRes Error string resource
	 * @return the same error string
	 */
	public static String e ( @StringRes int errorRes ) {
		String error = AppUtil.getString( errorRes );
		if ( AppUtil.isDebug() )
			Log.e( LOG_TAG, error );
		return error;
	}


}
