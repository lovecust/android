package com.fisher.utils;


import android.content.Context;
import android.widget.Toast;

public class MobileUtil {




	public static void fnLongToast(Context context, String msg){
		Toast.makeText( context, msg, Toast.LENGTH_LONG ).show();
	}
	public static void fnToast(Context context, String msg){
		Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
	}
}
