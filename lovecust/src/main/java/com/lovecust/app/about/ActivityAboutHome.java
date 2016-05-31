package com.lovecust.app.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.R;
import com.lovecust.app.lovecust.AppContext;
import com.lovecust.app.utils.AppUtil;

import butterknife.OnClick;


public class ActivityAboutHome extends AlphaActivity {

	@Override
	public int getLayout() {
		return R.layout.activity_about_home;
	}

	@Override
	public void init() {
		setTitle(getString(R.string.title_about)).setOnBackListener();

		TextView app = (TextView) findViewById(R.id.appVersion);
		app.setText(AppContext.mAppVersion);
	}

	public void btnUpdate(View view) {
		start(ActivityAboutUpdate.class);
	}

	public void btnContact(View view) {
		start(ActivityAboutContact.class);
	}

	@OnClick(R.id.btn_share)
	void btnShare(){
		start( ActivityAboutShare.class );
	}



	public void btnAbout(View view) {
		start(ActivityAboutAbout.class);
	}

	public void btnCopyright(View view) {
		start(ActivityAboutCopyright.class);
	}

	private void start(Class activity) {
		AppUtil.startActivityFromRight(this, activity);
	}

}
