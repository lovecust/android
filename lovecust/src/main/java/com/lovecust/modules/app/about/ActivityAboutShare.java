package com.lovecust.modules.app.about;

import android.content.Intent;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;

import butterknife.OnClick;

/**
 * Created by Fisher on 5/13/2016 at 1:36
 */
public class ActivityAboutShare extends BaseActivity {


	@Override
	public int getLayout ( ) {
		return R.layout.activity_about_share;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_about_share );
	}

	@OnClick( R.id.tv_share )
	void btnShare ( ) {
		Intent sharingIntent = new Intent( android.content.Intent.ACTION_SEND );
		sharingIntent.setType( "text/plain" );
		String shareBody = "欢迎加入QQ群: 515656842\n--Lovecust";
		// TODO share the message
		sharingIntent.putExtra( android.content.Intent.EXTRA_SUBJECT, "Subject Here" );
		sharingIntent.putExtra( android.content.Intent.EXTRA_TEXT, shareBody );
		startActivity( Intent.createChooser( sharingIntent, "分享Lovecust" ) );

	}
}
