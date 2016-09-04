package com.fisher.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TextUtil {
	private static String charset = FileUtil.CHARSET_UTF_8;
	public static String MD5 = "MD5";
	public static String SHA1 = "SHA1";

	public static String md5 ( String text ) {
		return digest( text, MD5 );
	}

	public static String md5 ( File file ) {
		return digest( FileUtil.getBytes(file), MD5 );
	}

	public static String sha1 ( String text ) {
		return digest( text, SHA1 );
	}

	private static String digest ( byte[] bytes, String algorithm ) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance( algorithm );
			return toStringFromBytes( mDigest.digest( bytes ) );
		} catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace();
			return null;
		}
	}

	private static String digest ( String input, String algorithm ) {
		try {
			return digest( input.getBytes( charset ), algorithm );
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}

	private static String toStringFromBytes ( byte[] bytes ) {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < bytes.length; i++ ) {
			sb.append( Integer.toString( ( bytes[ i ] & 0xff ) + 0x100, 16 ).substring( 1 ) );
		}
		return sb.toString();
	}


	public static SpannableString fnSetSpan ( String msg, int color ) {
		SpannableString spannableString = new SpannableString( msg );
		spannableString.setSpan( new ForegroundColorSpan( color ), 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
		return spannableString;
	}

	public static String textToHtml(String str) {
		StringBuilder builder = new StringBuilder();
		boolean previousWasASpace = false;
		for (char c : str.toCharArray()) {
			if (c == ' ') {
				if (previousWasASpace) {
					builder.append("&nbsp;");
					previousWasASpace = false;
					continue;
				}
				previousWasASpace = true;
			} else {
				previousWasASpace = false;
			}
			switch (c) {
				case '<':
					builder.append("&lt;");
					break;
				case '>':
					builder.append("&gt;");
					break;
				case '&':
					builder.append("&amp;");
					break;
				case '"':
					builder.append("&quot;");
					break;
				case '\n':
					builder.append("<br>");
					break;
				// We need Tab support here, because we print StackTraces as HTML
				case '\t':
					builder.append("&nbsp; &nbsp; &nbsp;");
					break;
				default:
					builder.append(c);

			}
		}
		return builder.toString();
	}

}
