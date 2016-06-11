package com.fisher.utils;


import com.lovecust.app.lovecust.AppContext;
import com.lovecust.app.lovecust.Setting;

import java.util.HashMap;

public class LogUtil {
	public static final String FILE_NAME_LOG_DEFAULT = "default.log";
	private static PermanentUtil util;
	private static HashMap< String, PermanentUtil > utils = new HashMap<>();

	public static String log( String msg ) {
		ConsoleUtil.console( msg );
		if( !AppContext.mDebug){
			return msg;
		}
		if ( null == util )
			util = PermanentUtil.get( FILE_NAME_LOG_DEFAULT );
		util.write( TimeUtil.fnFormatTime() + " -> \r\n" + msg + "\r\n\r\n" );
		return msg;
	}
	public static void release() {
		util.close();
		util = null;
		for ( String key : utils.keySet() ) {
			utils.get( key ).close();
		}
		utils.clear();
	}


	public static String log( String filename, String msg ) {
		ConsoleUtil.console( msg );
		if( !AppContext.mDebug){
			return msg;
		}
		PermanentUtil util = utils.get( filename );
		if ( null == util ) {
			util = PermanentUtil.get( filename );
			utils.put( filename, util );
		}
		util.write( msg + Setting.LINE_BREAK_MARK );
		return msg;
	}


}
