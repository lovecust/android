package com.lovecust.app.test;

import android.view.View;
import android.widget.TextView;

import com.fisher.utils.CacheManagerUtil;
import com.lovecust.app.R;
import com.lovecust.app.api.Methods;
import com.lovecust.app.ecust.wifi.ActivityEcustWifiHome;
import com.lovecust.app.feedback.ActivityFeedbackHome;
import com.lovecust.app.launcher.grid.ActivityLauncherGridHome;
import com.lovecust.app.launcher.slide.ActivityLauncherSlideHome;
import com.lovecust.app.library.ActivityLibraryHome;
import com.lovecust.app.library.ActivityLibraryTagManager;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.profile.ActivityProfileHome;
import com.lovecust.app.settings.ActivitySettingHome;
import com.fisher.utils.AppUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivityLauncherTestHome extends AlphaActivity {
	public static final String SERVER_TEST = "http://192.168.1.246:20161/";
	public static final String SERVER_PLUTO = "http://apis.lovecust.com/";

	@Bind( R.id.tv_server )
	TextView tvServer;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_launcher_test_home;
	}

	public void init ( ) {
		setTitle( R.string.title_test );

		tvServer.setText( Methods.server );
	}

	public void btnLibrary ( View view ) {
		start( ActivityLibraryHome.class );
	}

	public void btnTags ( View view ) {
		start( ActivityLibraryTagManager.class );
	}

	public void btnEcustWifi ( View view ) {
		start( ActivityEcustWifiHome.class );
	}

	public void btnTestDevice ( View view ) {
		start( ActivityDeviceHome.class );
	}


	public void btnSetting ( View view ) {
		start( ActivitySettingHome.class );
	}

	public void btnFeedback ( View view ) {
		start( ActivityFeedbackHome.class );
	}

	public void btnProfile ( View view ) {
		start( ActivityProfileHome.class );
	}

	@OnClick( R.id.tv_grid_launcher )
	public void btnGridLauncher ( ) {
		start( ActivityLauncherGridHome.class );
	}

	@OnClick( R.id.tv_slide_launcher )
	void btnSlideLauncher ( ) {
		start( ActivityLauncherSlideHome.class );
	}

	private void start ( Class activity ) {
		AppUtil.startActivityFromCenter( this, activity );
	}

	@OnClick( { R.id.tv_server } )
	void click ( View view ) {
		switch ( view.getId() ) {
			case R.id.tv_server:
				Methods.server = Methods.server.equals( SERVER_TEST ) ? SERVER_PLUTO : SERVER_TEST;
				tvServer.setText( Methods.server );
				break;
		}
	}


}
