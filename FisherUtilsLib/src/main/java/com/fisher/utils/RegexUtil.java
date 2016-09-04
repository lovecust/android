package com.fisher.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * filter
 * [ user name | email | phone number | password | verifying code ]
 * TODO [ file name ]
 */
public class RegexUtil {
	public static final String PATTERN_USERNAME = "[a-zA-Z][a-zA-Z0-9_\\-]{4,30}";
	public static final String PATTERN_EMAIL = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	public static final String PATTERN_URL = "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	public static final String PATTERN_PHONE_NUMBER = "1[0-9]{10}";
	public static final String PATTERN_PASSWORD = "[a-zA-Z0-9,\\.!@#$%^&*+=\\(\\)\\[\\]\\{\\}]{6,}";
	public static final String PATTERN_VERIFYING_CODE = "[0-9]{6}";

	public static boolean isUserNameOK( String username ) {
		boolean isOK = true;
		if ( null == username || "".equals( username ) || username.length() < 5 || username.length() > 30 )
			isOK = false;
		if ( !isOK( PATTERN_USERNAME, username ) )
			isOK = false;
		return isOK;
	}

	public static boolean isUrlOk ( String url ) {
		// todo this is not correct
		boolean isOK = true;
		if ( null == url || "".equals( url ) )
			isOK = false;
		if ( !isOK( PATTERN_EMAIL, url ) )
			isOK = false;
		return isOK;
	}
	public static boolean isEmailOK( String email ) {
		boolean isOK = true;
		if ( null == email || "".equals( email ) || email.length() < 5 || email.length() > 30 )
			isOK = false;
		if ( !isOK( PATTERN_EMAIL, email ) )
			isOK = false;
		return isOK;
	}

	public static boolean isPhoneNumberOK( String phone ) {
		boolean isOK = true;
		if ( null == phone || "".equals( phone ) )
			isOK = false;
		if ( !isOK( PATTERN_PHONE_NUMBER, phone ) )
			isOK = false;
		return isOK;
	}

	public static boolean isPasswordOK( String password ) {
		boolean isOK = true;
		if ( null == password || "".equals( password ) || password.length() < 6 || password.length() > 64 )
			isOK = false;
		if ( !isOK( PATTERN_PASSWORD, password ) )
			isOK = false;
		return isOK;
	}

	public static boolean isVerifyCodeOK( String verifyCode ) {
		boolean isOK = true;
		if ( verifyCode.length() != 6 )
			isOK = false;
		if ( !isOK( PATTERN_VERIFYING_CODE, verifyCode ) )
			isOK = false;
		return isOK;
	}

	public static boolean isOK( String regex, String input ) {
		Pattern pattern = Pattern.compile( regex );
		Matcher matcher = pattern.matcher( input );
		return matcher.matches();
	}

	public static String fnFilterFileName( String name ) {
		return fnFilterFileName( name, "-" );
	}
	public static String fnFilterFileName( String name, String replacement ) {
		if(null==name||"".equals( name )){
			name="default-name";
		}else {
			name=name.replace( "\\/:*?\"<>|", replacement );
		}
		return name;
	}

}
