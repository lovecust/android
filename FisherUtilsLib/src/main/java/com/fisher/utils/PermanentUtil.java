package com.fisher.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PermanentUtil {
	public static String defaultCharset = "utf-8";
	private OutputStreamWriter osw;

	public static PermanentUtil get( String name, String charset ) {
		PermanentUtil util = null;
		try {
			util = new PermanentUtil().init( name, charset );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return util;
	}
	public static PermanentUtil get( String name ) {
		return get( name, defaultCharset );
	}

	private PermanentUtil( ) {
	}
	private PermanentUtil init( String name, String charset ) throws Exception {
		if ( !name.startsWith( "/" ) ) {
			name = FileUtil.FILE_PATH_APP_LOG + "/" + name;
		}
		if ( new File( name ).getParentFile().exists() || new File( name ).getParentFile().mkdirs() )
			osw = new OutputStreamWriter( new FileOutputStream( name, true ), charset );
		return this;
	}


	public boolean write( String msg ) {
		if ( osw == null )
			return false;
		try {
			osw.write( msg );
			osw.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean close() {
		try {
			if ( osw != null ) {
				osw.close();
				osw = null;
			}
		} catch ( IOException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
