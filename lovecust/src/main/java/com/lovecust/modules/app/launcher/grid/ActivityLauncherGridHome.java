package com.lovecust.modules.app.launcher.grid;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lovecust.app.R;
import com.lovecust.modules.app.about.ActivityAboutHome;
import com.lovecust.modules.ecust.calendar.ActivityEcustCalendarHome;
import com.lovecust.modules.ecust.classroom.ActivityEcustClassroomHome;
import com.lovecust.modules.ecust.jwc.ActivityEcustJWCHome;
import com.lovecust.modules.ecust.library.ActivityEcustLibraryHome;
import com.lovecust.modules.ecust.wifi.ActivityEcustWifiHome;
import com.lovecust.modules.app.feedback.ActivityFeedbackHome;
import com.lovecust.app.AlphaActivity;
import com.lovecust.app.AppContext;
import com.lovecust.modules.app.profile.ActivityProfileHome;
import com.lovecust.modules.app.settings.ActivitySettingHome;
import com.fisher.utils.AppUtil;

import butterknife.Bind;

public class ActivityLauncherGridHome extends AlphaActivity implements CompoundButton.OnCheckedChangeListener {

	@Bind(R.id.viewPager)
	ViewPager mPager;
	@Bind(R.id.tabNav)
	CheckBox tabNav;
	@Bind(R.id.tabHome)
	CheckBox tabHome;

	private AdapterLauncherGridHome mPagerAdapter;


	@Override
	public int getLayout() {
		// this should be set by user
		isSwipeExit = AppContext.mIsHomeSwipeExit;
		return R.layout.activity_launcher_grid_home;
	}

	@Override
	public void init() {
		setTitle(R.string.app_name);

		// Instantiate a ViewPager and a PagerAdapter.Z
		mPagerAdapter = new AdapterLauncherGridHome(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
					case 0:
						tabNav.setChecked(true);
						break;
					case 1:
						tabHome.setChecked(true);
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		tabNav.setOnCheckedChangeListener(this);
		tabHome.setOnCheckedChangeListener(this);
		tabNav.setChecked(true);
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.tabNav:
				if (isChecked) {
					tabNav.setClickable(false);
					tabNav.getBackground().setColorFilter(AppUtil.getColor(R.color.app), PorterDuff.Mode.SRC_IN);

					tabHome.setClickable(true);
					tabHome.setChecked(false);
					tabHome.getBackground().clearColorFilter();

					mPager.setCurrentItem(0, true);
				}
				break;
			case R.id.tabHome:
				if (isChecked) {
					tabHome.setClickable(false);
					tabHome.getBackground().setColorFilter(AppUtil.getColor(R.color.app), PorterDuff.Mode.SRC_IN);

					tabNav.setClickable(true);
					tabNav.setChecked(false);
					tabNav.getBackground().clearColorFilter();

					mPager.setCurrentItem(1, true);
				}
				break;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
	}


	public void btnEcustCalendar(View view) {
		startActivityFromCenter(ActivityEcustCalendarHome.class);
	}

	public void btnEcustLibrary(View view) {
		startActivityFromCenter(ActivityEcustLibraryHome.class);
	}

	public void btnEcustClassroom(View view) {
		startActivityFromCenter(ActivityEcustClassroomHome.class);
	}

	public void btnEcustWifi(View view) {
		startActivityFromCenter(ActivityEcustWifiHome.class);
	}

	public void btnEcustJWC(View view) {
		startActivityFromCenter(ActivityEcustJWCHome.class);
	}


	public void btnProfile(View view) {
		startActivityFromRight(ActivityProfileHome.class);
	}

	public void btnSetting(View view) {
		startActivityFromRight(ActivitySettingHome.class);
	}

	public void btnFeedback(View view) {
		startActivityFromRight(ActivityFeedbackHome.class);
	}

	public void btnAbout(View view) {
		startActivityFromRight(ActivityAboutHome.class);
	}

	private void startActivityFromCenter(Class activityClass) {
		startActivity(new Intent(this, activityClass));
		overridePendingTransition(R.anim.activity_zoom_in_from_center, R.anim.activity_zoom_out_to_center );
	}

	private void startActivityFromRight(Class<? extends Activity> activityClass) {
		startActivity(new Intent(this, activityClass));
		overridePendingTransition(R.anim.activity_push_in_from_right, R.anim.activity_push_out_to_left );
	}


	@Override
	public void finish ( ) {
		super.finish();
//		overridePendingTransition( R.anim.activity_push_in_from_left, R.anim.activity_push_out_to_bottom );
	}
}
