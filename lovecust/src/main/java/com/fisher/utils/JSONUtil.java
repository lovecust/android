package com.fisher.utils;


import org.json.JSONArray;
import org.json.JSONException;

public class JSONUtil {

	public static String fnToJSON( String[] strings ) {
		if ( null == strings ) return null;
		JSONArray jsonArrayTag = new JSONArray();
		String tag, json;

		for ( int i = 0; i < strings.length; i++ ) {
			tag = strings[i].trim();
			if ( tag.equals( "" ) ) {

			} else {
				jsonArrayTag.put( tag );
			}
		}
		json = jsonArrayTag.toString();
		return json;
	}
	public static String[] fnToStrings( String json ) {
		if ( null == json || "".equals( json ) ) return null;
		String[] sTags = null;
		try {
			JSONArray tags = new JSONArray( json );
			sTags = new String[tags.length()];
			for ( int i = 0; i < tags.length(); i++ ) {
				sTags[i] = tags.getString( i );
			}
		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		return sTags;
	}

}
