package com.lovecust.app.launcher;

import android.content.Intent;
import android.os.Bundle;

import com.lovecust.app.R;
import com.lovecust.app.launcher.grid.ActivityLauncherGridHome;
import com.lovecust.app.launcher.slide.ActivityLauncherSlideHome;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;


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

