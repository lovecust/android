package com.lovecust.app.test;


import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.utils.PreferenceUtil;


public class DataClassroom extends PreferenceUtil {
	public static String FILE_NAME = Setting.FILE_TEXT_ABOUT_ABOUT;

	public static DataClassroom getInstance () {
		return getInstance( DataClassroom.class, getInternalFile( FILE_NAME ) );
	}

	public static void flush () {
		flush( getInternalFile( FILE_NAME ) );
	}





}
