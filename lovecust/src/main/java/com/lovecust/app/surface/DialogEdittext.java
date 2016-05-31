package com.lovecust.app.surface;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.lovecust.AppContext;
import com.lovecust.app.utils.AppUtil;
import com.lovecust.app.utils.EdittextUtil;


public class DialogEdittext extends AlertDialog implements View.OnClickListener {
	public interface OnActionResultListener {
		void onActionCommit(String input);

		void onActionCancel();
	}

	private OnActionResultListener listener;
	private TextView tTitle;
	private EditText mEdittext;

	public static DialogEdittext newDialog(Context context) {
		if (context == null) {
			return newDialog();
		}
		return new DialogEdittext(context);
	}

	public static DialogEdittext newDialog() {
		Context context = AppContext.getContext();
		return new DialogEdittext(context, true);
	}

	private DialogEdittext(Context context) {
		super(context);
	}

	private DialogEdittext(Context context, boolean system) {
		super(context);
		if (system) {
			getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
	}

	public DialogEdittext init(String title) {
		View view = View.inflate(getContext(), R.layout.dialog_edittext, null);
		tTitle = (TextView) view.findViewById(R.id.textTitle);
		mEdittext = (EditText) view.findViewById(R.id.et_text);
		view.findViewById(R.id.mActionCommit).setOnClickListener(this);
		view.findViewById(R.id.mActionCancel).setOnClickListener(this);
		if (null != title)
			tTitle.setText(title);
		setView(view);
		return this;
	}

	public DialogEdittext setDigitsAndAlphabets() {
		EdittextUtil.setDigitsAndAlphabets(mEdittext);
		return this;
	}

	public DialogEdittext setTitle(String title) {
		tTitle.setText(title);
		return this;
	}

	public DialogEdittext setEdittext(String content) {
		mEdittext.setText(content);
		return toCursorEnd();
	}

	public DialogEdittext toPasswordMode() {
		mEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		return toCursorEnd();
	}

	public DialogEdittext toInputMethodNormal() {
		mEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
		return toCursorEnd();
	}

	public DialogEdittext toInputMethodNumber() {
		mEdittext.setInputType(InputType.TYPE_CLASS_NUMBER);
		return toCursorEnd();
	}

	public DialogEdittext toInputMethodPhone() {
		mEdittext.setInputType(InputType.TYPE_CLASS_PHONE);
		return toCursorEnd();
	}

	public DialogEdittext toInputMethodDate() {
		mEdittext.setInputType(InputType.TYPE_CLASS_DATETIME);
		return toCursorEnd();
	}

	public DialogEdittext toInputMethodWebEmail() {
		mEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		return toCursorEnd();
	}

	public DialogEdittext toCursorEnd() {
		mEdittext.setSelection(mEdittext.getText().length());
		return this;
	}

	public DialogEdittext setHintText(String hint) {
		mEdittext.setHint(hint);
		return this;
	}

	public DialogEdittext setHintText(int id) {
		mEdittext.setHint(AppUtil.getString(id));
		return this;
	}

	public DialogEdittext setOnActionResultListener(OnActionResultListener listener) {
		this.listener = listener;
		return this;
	}


	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
			case R.id.mActionCommit:
				if (null != listener) {
					listener.onActionCommit(mEdittext.getText().toString().trim());
				}
				break;
			case R.id.mActionCancel:
				if (null != listener)
					listener.onActionCancel();
				break;
		}
	}
}
