package com.lovecust.app.lovecust;


import android.content.Intent;

import java.io.File;

public class Setting {
	public static final String separator = File.separator;
	public static final String URL_DOUBAN_API_SERVER = "https://api.douban.com";
	public static final String URL_DOUBAN_API_PATH = "/v2/book/isbn/:{isbn}";
	public static final String URL_DOUBAN_API_PATH_KEY = "isbn";

	public static final String ACTION_ECUST_WIFI_RECONNECT = "com.lovecust.action.RECONNECT";

	public static final String INTENT_KEY_DEFAULT = "key";
	public static final String INTENT_KEY_MESSAGE = "msg";
	public static final String INTENT_KEY_TITLE = "title";
	public static final String INTENT_KEY_DATE = "date";

	public static final int INTENT_REQUEST_CODE = 200;

	public static final String FILE_GSON_SETTING_ECUST_WIFI = "preferences/ecust/wifi.cfg";
	public static final String FILE_GSON_SETTING_ECUST_CLASSROOM = "preferences/ecust/classroom.cfg";
	public static final String FILE_GSON_SETTING_APP = "preferences/app/setting.cfg";
	public static final String FILE_GSON_SETTING_USER_PROFILE = "preferences/user/profile.cfg";
	public static final String FILE_GSON_APP_INFO = "preferences/app/info.cfg";


	public static String FILE_GSON_DATA_FLOAT_IMAGE = "data/image.json";

	public static final String FILE_INTERNAL_CACHE_JWC_NEWS = "cache/jwc/jwc-list.json";

	public static final String FILE_TEXT_ABOUT_CONTACT = "about/contact.txt";
	public static final String FILE_TEXT_ABOUT_ABOUT = "about/about.txt";
	public static final String FILE_TEXT_ABOUT_COPYRIGHT = "about/copyright.txt";

	public static final String FILE_FONT_CONSOLA = "fonts/consola.ttf";

	public static final String FILENAME_LOG_EVENT = "events.log";
	public static final String FILENAME_LOG_PASTEBOARD = "pasteboard.log";
	public static final String FILE_PROFILE_AVATAR = "user/avatar.png";


	// TODO editable
	public static final long TIME_LAUNCHER_INTERVAL = 800;

	public static final String LINE_BREAK_MARK = "\r\n";

	// this should be changeable
	public static String FILE_EXTERNAL_DIR_APP = "Lovecust";

	public static String FILE_EXTERNAL_DIR_APP_IMAGE = "Images";
	public static String FILE_EXTERNAL_DIR_APP_APP = "App";
	public static String FILE_EXTERNAL_FILE_APP_APP_UPDATE_APK = FILE_EXTERNAL_DIR_APP_APP + separator + "lovecust.apk";


}
