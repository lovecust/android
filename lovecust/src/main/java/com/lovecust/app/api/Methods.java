package com.lovecust.app.api;

/**
 * Created by Fisher on 5/22/2016 at 1:04
 */
public class Methods {
	public static String server = "http://apis.lovecust.com/";
	private static final String APIS = "apis/";

	/* apis related to lovecust-app */
	private static final String APP = APIS + "lovecust-app/";
	// profile part
	private static final String APP_PROFILE = APP + "profile";
	public static final String APP_PROFILE_UPDATE = APP_PROFILE + "-update";
	// app-update part
	private static final String APP_UPDATE = APP + "update";
	public static final String APP_UPDATE_STATUS = APP_UPDATE + "-status";
	// feedback part
	private static final String APP_FEEDBACK = APP + "feedback";
	public static final String APP_FEEDBACK_CREATE = APP_FEEDBACK + "-create";
	public static final String APP_FEEDBACK_FETCH = APP_FEEDBACK + "-fetch";
	// internet part
	private static final String APP_INTERNET = APP + "internet";
	public static final String APP_INTERNET_STATUS = APP_INTERNET + "-status";

	/* apis related to Ecust */
	private static final String ECUST = APIS + "ecust-related/";
	// library part
	private static final String ECUST_LIBRARY = ECUST + "library";
	public static final String ECUST_LIBRARY_STATUS = ECUST_LIBRARY + "-status";
	// jwc part
	private static final String ECUST_JWC = ECUST + "jwc";
	public static final String ECUST_JWC_LIST = ECUST_JWC + "-list";
	public static final String ECUST_JWC_FETCH = ECUST_JWC + "-fetch";
	private static final String ECUST_JWC_COMMENT = ECUST_JWC + "-comment";
	public static final String ECUST_JWC_COMMENT_CREATE = ECUST_JWC_COMMENT + "-create";
	public static final String ECUST_JWC_COMMENT_LIST = ECUST_JWC_COMMENT + "-list";

}
