package com.lovecust.app.about;

import android.view.View;
import android.widget.TextView;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.R;
import com.lovecust.app.lovecust.AppContext;
import com.fisher.utils.AppUtil;

import butterknife.OnClick;


public class ActivityAboutHome extends AlphaActivity {

	@Override
	public int getLayout ( ) {
		return R.layout.activity_about_home;
	}

	@Override
	public void init ( ) {
		setTitle( getString( R.string.title_about ) );

		TextView app = ( TextView ) findViewById( R.id.appVersion );
		app.setText( AppContext.mAppVersion );
	}

	@OnClick( { R.id.layout_update, R.id.item_update } )
	void btnUpdate ( ) {
		start( ActivityAboutUpdate.class );
	}

	public void btnContact ( View view ) {
		start( ActivityAboutContact.class );
	}

	@OnClick( R.id.btn_share )
	void btnShare ( ) {
		start( ActivityAboutShare.class );
	}


	public void btnAbout ( View view ) {
		start( ActivityAboutAbout.class );
	}

	public void btnCopyright ( View view ) {
		start( ActivityAboutCopyright.class );
	}

	private void start ( Class activity ) {
		AppUtil.startActivityFromRight( this, activity );
	}

}
