package com.fisher.utils;

import com.fisher.utils.constants.FileConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLUtil {

	/**
	 * Using specific charset encode url.
	 *
	 * @param params  Data to be encoded.
	 * @param charset Charset to be used.
	 * @return Encoded string.
	 */
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

	/**
	 * Using utf8 encode url.
	 *
	 * @param params Data to be encoded.
	 * @return Encoded string.
	 */
	public static String getPostDataString ( HashMap< String, String > params ) {
		return getPostDataString( params, FileConstant.CHARSET_UTF_8 );
	}
}
