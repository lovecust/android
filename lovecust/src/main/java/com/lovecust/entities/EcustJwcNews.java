package com.lovecust.entities;


import com.google.gson.Gson;
import com.lovecust.app.Setting;
import com.fisher.utils.FileUtil;

import java.io.File;

public class EcustJwcNews {
	public static String FILENAME = Setting.FILE_INTERNAL_CACHE_JWC_NEWS;

	public static EcustJwcNews[] getCachedNews () {
		File file = FileUtil.getInternalFile( FILENAME );

		return null;
	}

	private String md5;
	private String name;
	private String url;
	private String date;
	private String value;
	private boolean bold;
	private boolean red;
	private int favorite;
	private int view;
	private int comment;


	public String getMd5 () {
		return md5;
	}

	public void setMd5 ( String md5 ) {
		this.md5 = md5;
	}

	public String getName () {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getUrl () {
		return url;
	}

	public void setUrl ( String url ) {
		this.url = url;
	}

	public String getDate () {
		return date;
	}

	public void setDate ( String date ) {
		this.date = date;
	}

	public String getValue () {
		return value;
	}

	public void setValue ( String value ) {
		this.value = value;
	}

	public int getFavorite () {
		return favorite;
	}

	public void setFavorite ( int favorite ) {
		this.favorite = favorite;
	}

	public int getView () {
		return view;
	}

	public void setView ( int view ) {
		this.view = view;
	}

	public int getComment () {
		return comment;
	}

	public void setComment ( int comment ) {
		this.comment = comment;
	}

	public boolean isBold ( ) {
		return bold;
	}

	public void setBold ( boolean bold ) {
		this.bold = bold;
	}

	public boolean isRed ( ) {
		return red;
	}

	public void setRed ( boolean red ) {
		this.red = red;
	}


	@Override
	public String toString ( ) {
		return new Gson().toJson( this );
	}
}
