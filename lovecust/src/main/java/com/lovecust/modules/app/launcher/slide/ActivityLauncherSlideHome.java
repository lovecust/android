package com.lovecust.modules.app.launcher.slide;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.modules.ecust.calendar.ActivityEcustCalendarHome;
import com.lovecust.modules.ecust.classroom.ActivityEcustClassroomHome;
import com.lovecust.modules.ecust.library.ActivityEcustLibraryHome;
import com.lovecust.app.BaseActivity;
import com.lovecust.entities.AppProfile;
import com.lovecust.modules.app.profile.ActivityProfileHome;
import com.lovecust.app.R;
import com.lovecust.modules.app.settings.ActivitySettingHome;
import com.lovecust.modules.app.about.ActivityAboutHome;
import com.lovecust.modules.app.feedback.ActivityFeedbackHome;

import java.io.File;

import butterknife.Bind;

public class ActivityLauncherSlideHome extends BaseActivity {

	@Bind(R.id.drawer_layout)
	DrawerLayout drawerLayout;
	@Bind(R.id.menu)
	View menu;
	@Bind(R.id.nick)
	TextView nick;
	@Bind(R.id.avatar)
	ImageView avatar;


	@Override
	public int getLayout() {
		return R.layout.activity_home;
	}

	@Override
	public void init () {
		setTitle(R.string.app_name);
	}

	@Override
	protected void onResume () {
		nick.setText( AppProfile.getInstance().getNick() );
		File temp = AppProfile.getInstance().getAvatarFile();
		if ( null != temp && temp.exists() )
			avatar.setImageURI( Uri.fromFile( temp ) );
		super.onResume();
	}


	public void btnProfile ( View view ) {
		startActivity( new Intent( this, ActivityProfileHome.class ) );
	}

	public void btnEcustCalendar ( View view ) {
		startActivity( new Intent( this, ActivityEcustCalendarHome.class ) );
	}
	public void btnEcustLibrary ( View view ) {
		startActivity( new Intent( this, ActivityEcustLibraryHome.class ) );
	}
	public void btnEcustClassroom ( View view ) {
		startActivity( new Intent( this, ActivityEcustClassroomHome.class ) );
	}
	public void btnFeedback ( View view ) {
		startActivity( new Intent( this, ActivityFeedbackHome.class ) );
	}

	public void btnSetting ( View view ) {
		startActivity( new Intent( this, ActivitySettingHome.class ) );
	}

	public void btnAbout ( View view ) {
		startActivity( new Intent( this, ActivityAboutHome.class ) );
	}
}
