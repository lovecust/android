package com.lovecust.app.recorder;


import android.content.ClipboardManager;
import android.content.Context;

import com.lovecust.app.lovecust.AppContext;


public class LogPasteboard {


	private static ClipboardManager clipboardManager;
	private static ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener;

	private static String sPasteBoard;


	public static void init() {
		clipboardManager = (ClipboardManager) AppContext.getContext().getSystemService( Context.CLIPBOARD_SERVICE );
		onPrimaryClipChangedListener = ( ) -> {
			String sNewPasteBoard = clipboardManager.getText().toString();
			if ( sNewPasteBoard.equals( "" ) || ( sNewPasteBoard.equals( sPasteBoard ) ) ) {
				return;
			}
			sPasteBoard = sNewPasteBoard;
			AppContext.mTextPasteBoard = sPasteBoard;
		};
		clipboardManager.addPrimaryClipChangedListener( onPrimaryClipChangedListener );
	}

	public static void release(){
		clipboardManager.removePrimaryClipChangedListener( onPrimaryClipChangedListener );
	}
}
