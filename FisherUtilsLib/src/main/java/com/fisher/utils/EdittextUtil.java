package com.fisher.utils;

import android.text.method.DigitsKeyListener;
import android.widget.EditText;

/**
 * Created by Fisher on 5/12/2016 at 21:21
 */
public class EdittextUtil {
	public static final String TEXT_DIGITS_AND_ALPHABETS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";


	public static EditText setDigitsAndAlphabets(EditText mEdittext) {
		mEdittext.setKeyListener(DigitsKeyListener.getInstance(TEXT_DIGITS_AND_ALPHABETS));
		return mEdittext;
	}

	public static EditText toCursorEnd(EditText mEdittext) {
		mEdittext.setSelection(mEdittext.getText().length());
		return mEdittext;
	}

	public static EditText setText(EditText mEdittext, String text) {
		mEdittext.setText(text);
		return toCursorEnd(mEdittext);
	}
}
