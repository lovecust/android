package com.lovecust.app.library;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.R;
import com.fisher.utils.ToastUtil;

import butterknife.Bind;


public class ActivityLibraryTagEditor extends AlphaActivity {
	public static final long CODE_CREATE_TAG = -100;
	private long id;

	@Bind(R.id.tag)
	EditText tagEdittext;
	@Bind(R.id.note)
	EditText noteEdittext;

	private Tag tag;

	@Override
	public int getLayout() {
		return R.layout.activity_library_tag_editor;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle("Tag Editor").setOptionText("Save");

		findViewById(R.id.titleBarOptionRight).setOnClickListener(this);

		Intent intent = getIntent();
		id = intent.getLongExtra(Setting.INTENT_KEY_DEFAULT, -1);
		if (id == -1) {
			finish();
			return;
		} else if (id == CODE_CREATE_TAG) {

		} else {
			tag = TagsManager.getTag(id);
			tagEdittext.setText(tag.getTag());
			noteEdittext.setText(tag.getNote());
		}
	}

	private void save() {
		String sTag = tagEdittext.getText().toString().trim();
		String sNote = noteEdittext.getText().toString().trim();
		if (id == CODE_CREATE_TAG) {
			tag = new Tag();
		}
		tag.setTag(sTag);
		tag.setNote(sNote);
		try {
			tag.saveThrows();
			ToastUtil.toast("Tag Successfully Saved!");
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.toast("Tag is not Saved!" + e.toString());
		}
		finish();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.titleBarOptionLeft:
				finish();
				break;
			case R.id.titleBarOptionRight:
				save();
				break;
		}
	}

}
