package com.fisher.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLUtil {
	public static String charset = "UTF-8";

	public static String getPostDataString ( HashMap< String, String > params, String charset ) {
		if ( null == params )
			return null;
		try {
			StringBuilder result = new StringBuilder();
			boolean first = true;
			for ( Map.Entry< String, String > entry : params.entrySet() ) {
				if ( first )
					first = false;
				else
					result.append( "&" );
				result.append( URLEncoder.encode( entry.getKey(), charset ) );
				result.append( "=" );
				result.append( URLEncoder.encode( entry.getValue(), charset ) );
			}
			return result.toString();
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getPostDataString ( HashMap< String, String > params ){
		return getPostDataString( params, charset );
	}
}
