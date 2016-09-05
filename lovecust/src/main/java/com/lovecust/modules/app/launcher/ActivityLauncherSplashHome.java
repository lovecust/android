package com.lovecust.modules.app.launcher;

import android.content.Intent;

import com.lovecust.app.R;
import com.lovecust.modules.app.launcher.grid.ActivityLauncherGridHome;
import com.lovecust.app.AlphaActivity;
import com.lovecust.app.Setting;


public class ActivityLauncherSplashHome extends AlphaActivity {


	@Override
	public int getLayout() {
		return R.layout.activity_launcher_splash_home;
	}

	public void init () {
		new Thread() {
			@Override
			public void run () {
				try {
					Thread.sleep( Setting.TIME_LAUNCHER_INTERVAL );
					ActivityLauncherSplashHome.this.startActivity( new Intent( ActivityLauncherSplashHome.this, ActivityLauncherGridHome.class ) );
					finish();
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}

