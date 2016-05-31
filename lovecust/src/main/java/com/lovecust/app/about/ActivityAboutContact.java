package com.lovecust.app.about;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;


public class ActivityAboutContact extends AlphaActivity {

	@Bind(R.id.text)
	TextView text;

	@Override
	public int getLayout() {
		return R.layout.activity_about_contact;
	}

	@Override
	public void init() {
		setTitle(getString(R.string.title_about_contact)).setOnBackListener();

		try {
			InputStream in = getResources().getAssets().open(Setting.FILE_TEXT_ABOUT_CONTACT);
			String contact = FileUtil.getString(in);
			Typeface face = Typeface.createFromAsset(getAssets(), Setting.FILE_FONT_CONSOLA);
			text.setTypeface(face);
			text.setText(contact);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
