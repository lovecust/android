package com.lovecust.app.utils;


import android.content.Context;
import android.widget.Toast;

import com.lovecust.app.lovecust.AppContext;


public class ToastUtil {


	public static String toast ( String msg ) {
		return toast( AppContext.getContext(), msg );
	}

	public static String toast ( int stringRes ) {
		return toast( AppUtil.getString( stringRes ) );
	}

	public static String toast ( Context context, String msg ) {
		Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
		return msg;
	}

	public static String toastLong ( Context context, String msg ) {
		Toast.makeText( context, msg, Toast.LENGTH_LONG ).show();
		return msg;
	}

}
