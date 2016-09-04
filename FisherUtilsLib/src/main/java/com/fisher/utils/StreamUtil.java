package com.fisher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {




	public static String getString( InputStream in, String charset ) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader( new InputStreamReader( in, charset ) );
		String line;
		while ( ( line = br.readLine() ) != null ) {
			sb.append( line );
		}
		br.close();
		return sb.toString();
	}
}
