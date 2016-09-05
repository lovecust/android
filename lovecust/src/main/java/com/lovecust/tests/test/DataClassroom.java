package com.lovecust.tests.test;


import com.lovecust.app.Setting;
import com.fisher.utils.PreferenceUtil;


public class DataClassroom extends PreferenceUtil {
	public static String FILE_NAME = Setting.FILE_TEXT_ABOUT_ABOUT;

	public static DataClassroom getInstance () {
		return getInstance( DataClassroom.class, getInternalFile( FILE_NAME ) );
	}

	public static void flush () {
		flush( getInternalFile( FILE_NAME ) );
	}





}
