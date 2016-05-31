package com.lovecust.app.surface;

import android.content.Context;

import com.lovecust.app.lovecust.AppContext;
import com.lovecust.app.R;
import com.lovecust.app.utils.VibrateUtil;


public class DialogVibrateMode {
	public static long[][] mVibratePatternInstances = { VibrateUtil.PATTERN_SHORT_SHORT, VibrateUtil.PATTERN_NORMAL_SHORT, VibrateUtil.PATTERN_NORMAL, VibrateUtil.PATTERN_LONG_SHORT_PAUSE_SHORT_SHORT };
	private static String[] mVibratePatternHints;

	public static DialogListView newDialog(Context context, DialogListView.OnActionResultListener listener ) {
		DialogListView dialog = DialogListView.newDialog( context ).init( getVibratePatternHints() )
				.setOnActionResultListener( listener );
		return dialog;
	}


	public static String[] getVibratePatternHints() {
		if ( null != mVibratePatternHints )
			return mVibratePatternHints;
		mVibratePatternHints = AppContext.getContext().getResources().getStringArray( R.array.dialog_vibrate_patterns_reminder );
		return mVibratePatternHints;
	}

}
