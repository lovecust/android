package com.lovecust.app;


import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.FileUtil;

public class AppInfo {

	private static AppInfo setting;

	public static AppInfo getInstance () {
		if ( null != setting )
			return setting;
		String json = FileUtil.readFileWithoutException( FileUtil.getInternalFile( Setting.FILE_GSON_SETTING_USER_PROFILE ) );
		if ( !"".equals( json ) ) {
			try {
				setting = new Gson().fromJson( json, AppInfo.class );
			} catch ( JsonSyntaxException e ) {
				e.printStackTrace();
				BugsUtil.onFatalError( "SettingWifi.renderViews()-> json string format failed[ configure edited ]!" );
			}
		}
		if ( null == setting )
			setting = new AppInfo();
		return setting;
	}

	public void flush () {
		FileUtil.writeFileWithoutException( FileUtil.getInternalFile( Setting.FILE_GSON_SETTING_USER_PROFILE ), new Gson().toJson( this ) );
	}

	// device id; will never be changed
	private String did = "";
	private long installTime;

	private String version = Build.VERSION.RELEASE;
	private int sdk = Build.VERSION.SDK_INT;


}
