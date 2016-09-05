package com.lovecust.modules.ecust.classroom;


import com.lovecust.app.Setting;
import com.fisher.utils.PreferenceUtil;

public class SettingClassroom extends PreferenceUtil {
	public static String FILE_NAME = Setting.FILE_GSON_SETTING_ECUST_CLASSROOM;

	public static SettingClassroom getInstance () {
		return getInstance( SettingClassroom.class, getInternalFile( FILE_NAME ) );
	}

	public static void flush () {
		flush( getInternalFile( FILE_NAME ) );
	}


	private int lastBuilding = 0;
	private int lastCourse = 0;


	public int getLastBuilding () {
		return lastBuilding;
	}

	public void setLastBuilding ( int lastBuilding ) {
		if ( this.lastBuilding == lastBuilding )
			return;
		this.lastBuilding = lastBuilding;
		flush();
	}

	public int getLastCourse () {
		return lastCourse;
	}

	public void setLastCourse ( int lastCourse ) {
		if ( this.lastCourse == lastCourse )
			return;
		this.lastCourse = lastCourse;
		flush();
	}
}
