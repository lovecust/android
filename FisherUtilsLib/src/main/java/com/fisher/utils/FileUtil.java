package com.fisher.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;


import com.fisher.utils.constants.FileConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/*
* contains all the methods and constant files of processes about file or stream;
*
* # sdcard and internal path
* # read stream
* # read and write file
*
* $ Rely RegexUtil
* */
public class FileUtil {
	public static final String separator = File.separator;
	public static final String FILE_PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String FILE_PATH_INTERNAL = AppUtil.getContext().getFilesDir().getAbsolutePath();
	public static final String FILE_PATH_APP = FILE_PATH_SDCARD + separator + FileConstant.DIR_EXTERNAL_APP;
	public static final String FILE_PATH_APP_LOG = FILE_PATH_APP + "/Log";

	public static final String charset = "UTF-8";
	public static final String CHARSET_UTF_8 = "UTF-8";


	public static File getInternalFile ( String filename ) {
		try {
			return createDirectory( FILE_PATH_INTERNAL + separator + filename );
		} catch ( IOException e ) {
			e.printStackTrace();
			BugsUtil.onFatalError( "Fatal Error: crate file to Internal Storage failed!" );
			return null;
		}
	}

	public static File getExternalFile ( String filename ) {
		try {
			return createDirectory( FILE_PATH_SDCARD + separator + FileConstant.DIR_EXTERNAL_APP + separator + filename );
		} catch ( IOException e ) {
			e.printStackTrace();
			BugsUtil.onFatalError( "Fatal Error: crate file to External Storage failed!" );
			return null;
		}
	}

	private static File createDirectory ( String path ) throws IOException {
		return createDirectory( new File( path ) );
	}

	private static File createDirectory ( File file ) throws IOException {
		if ( file.exists() )
			return file;
		File p = file.getParentFile();
		if ( p.exists() )
			return file;
		p.mkdirs();
		return file;
	}

	/*
	* Get String from stream
	* */
	public static String getString ( InputStream in ) throws IOException {
		return getString( in, charset );
	}

	//	public static String getString ( InputStream in, String charset ) throws IOException {
//		StringBuffer out = new StringBuffer();
//		byte[] b = new byte[ 4096 ];
//		for ( int i; ( i = in.read( b ) ) != -1; ) {
//			out.append( new String( b, 0, i, charset ) );
//		}
//		return out.toString();
//	}
	public static String getString ( InputStream in, String charset ) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader( new InputStreamReader( in, charset ) );
		String line;
		while ( ( line = br.readLine() ) != null ) {
			sb.append( line + "\n" );
		}
		br.close();
		if ( sb.length() - 1 < 0 )
			return sb.toString();
		return sb.substring( 0, sb.length() - 1 );
	}

	public static String getString ( InputStream in, int length, String charset ) throws IOException {
		byte[] bytes = new byte[ length ];
		in.read( bytes );
		return new String( bytes, charset );
	}


	/*
	*
	* Read string from file  |  Write string to a file
	* */
	public static String readFileWithoutException ( File file ) {
		try {
			return readFile( file, charset );
		} catch ( IOException e ) {
			e.printStackTrace();
			BugsUtil.onFatalError( "FileUtil.readFileWithoutException(): failed to read file!" );
			return "";
		}
	}

	public static String readFile ( File file ) throws IOException {
		return readFile( file, charset );
	}

	public static String readFile ( File file, String charset ) throws IOException {
		if ( file.exists() ) {
			FileInputStream in = new FileInputStream( file );
			return getString( in, charset );
		} else {
			return "";
		}
	}

	public static void writeFileWithoutException ( File file, String content ) {
		writeFileWithoutException( file, content, charset );
	}

	public static void writeFileWithoutException ( File file, String content, String charset ) {
		try {
			writeFile( file, content, charset );
		} catch ( IOException e ) {
			e.printStackTrace();
			BugsUtil.onFatalError( "FileUtil.fnWriteWithoutException(): failed to write file!" );
		}
	}

	public static void writeFile ( File file, InputStream in ) throws IOException {
		FileOutputStream out = new FileOutputStream( file );
		byte[] buffer = new byte[ 1024 ];
		int len = in.read( buffer );
		while ( len != -1 ) {
			out.write( buffer, 0, len );
			len = in.read( buffer );
		}
		out.flush();
		out.close();
	}

	public static void writeFile ( File file, String content ) throws IOException {
		writeFile( file, content, charset );
	}

	public static void writeFile ( File file, String content, String charset ) throws IOException {
		FileOutputStream out = new FileOutputStream( file );
		out.write( content.getBytes( charset ) );
		out.flush();
		out.close();
	}


	/*
	* TODO filter file name
	* */


	public static void chooseFile ( Activity activity ) {
		Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
		intent.setType( "*/*" );
		intent.addCategory( Intent.CATEGORY_OPENABLE );
		activity.startActivityForResult( Intent.createChooser( intent, "Select File to Upload.." ), 200 );
	}

	public static byte[] getBytes ( File file ) {
		try {
			RandomAccessFile f = new RandomAccessFile( file, "r" );
			byte[] bytes = new byte[ ( int ) f.length() ];
			f.read( bytes );
			return bytes;
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
}
