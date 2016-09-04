package com.fisher.utils;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;

/**
 * Created on 6/12/2016 at 1:08
 * By Fisher
 * used to save and fetch caches
 */
public class CacheManagerUtil< T > {
	public static final String CACHE_DIR = "data";

	public static < T > CacheManagerUtil< T > getInstance ( T data ) {
		return new CacheManagerUtil<>( data );
	}

	public void save ( ) {
		try {
			String name = data.getClass().getPackage() + "." + data.getClass().getName();
			FileUtil.writeFile( getFile(name), this.toString() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static < T > CacheManagerUtil< T > getCache ( Class< T > cls ) {
		String name = cls.getPackage() + "." + cls.getName();
		CacheManagerUtil< T > data = null;
		try {
			String json = FileUtil.readFile( FileUtil.getInternalFile( "data" + FileUtil.separator + TextUtil.md5( name ) ) );
			if ( null == json || "".equals( json ) ) {
				return null;
			} else {
				try {
					data = new Gson().fromJson( json, CacheManagerUtil.class );
				} catch ( JsonSyntaxException e ) {
					e.printStackTrace();
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return data;
	}

	public static File getFile (String name ) {
		return FileUtil.getInternalFile( CACHE_DIR + FileUtil.separator + TextUtil.md5( name ) );
	}


	private CacheManagerUtil ( T data ) {
		this.data = data;
	}

	private long ctime;
	private T data;

	public long getCtime ( ) {
		return ctime;
	}

	public void setCtime ( long ctime ) {
		this.ctime = ctime;
	}

	public T getData ( ) {
		return data;
	}

	public void setData ( T data ) {
		this.ctime = System.currentTimeMillis();
		this.data = data;
	}

	@Override
	public String toString ( ) {
		return new Gson().toJson( this );
	}
}

