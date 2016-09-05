package com.lovecust.modules.app.launcher.grid;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.modules.ecust.jwc.ActivityEcustJWCHome;
import com.lovecust.modules.ecust.wifi.ActivityEcustWifiHome;
import com.lovecust.modules.ecust.wifi.SettingWifi;
import com.lovecust.app.BaseFragment;
import com.lovecust.app.AppSetting;
import com.fisher.utils.TimeUtil;
import com.fisher.utils.VibrateUtil;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentLauncherPageNav extends BaseFragment {

	@Bind( R.id.btn_ecust_jwc )
	View layoutEcustJwc;
	@Bind( R.id.btn_ecust_wifi )
	View layoutEcustWifi;
	@Bind( R.id.tv_current_week )
	TextView tvCurrentWeek;

	private AppSetting setting;
	private SettingWifi wifi;
	private boolean weekBig = false;
	private int cc = 0;

	@Override
	public int getLayoutID ( ) {
		return R.layout.fragment_launcher_page_nav;
	}

	@Override
	public void init ( ) {
		setting = AppSetting.getInstance();
		wifi = SettingWifi.getInstance();
		// TODO get info from server and dismiss offset with local time
		int week = TimeUtil.getWeekOfYear() - 8;
		tvCurrentWeek.setText( week + "th" );

		if ( wifi.isAutoConnect() ) {
			layoutEcustWifi.setVisibility( View.INVISIBLE );
		} else {
			layoutEcustWifi.setVisibility( View.VISIBLE );
		}
	}


	@OnClick( R.id.btn_ecust_jwc )
	void btnEcustJwc ( ) {
		btnStartActivity( ActivityEcustJWCHome.class );
	}

	@OnClick( R.id.btn_ecust_wifi )
	void btnEcustWifi ( ) {
		btnStartActivity( ActivityEcustWifiHome.class );
	}

	private void btnStartActivity ( Class< ? extends BaseActivity > activity ) {
		getActivity().startActivity( new Intent( getActivity(), activity ) );
		getActivity().overridePendingTransition( R.anim.activity_zoom_in_from_center, R.anim.activity_zoom_out_to_center );
	}

	@OnClick( R.id.layout_current_week )
	void btnCurrentWeek ( ) {
		tvCurrentWeek.clearAnimation();
		if ( weekBig ) {
			Animation anim = AnimationUtils.loadAnimation( getContext(), R.anim.zoom_in_2x_from_left_bottom );
			tvCurrentWeek.startAnimation( anim );
			anim.setFillAfter( true );
		} else {
			Animation anim = AnimationUtils.loadAnimation( getContext(), R.anim.zoom_out_2x_from_left_bottom );
			tvCurrentWeek.startAnimation( anim );
			anim.setFillAfter( true );
		}
		weekBig = !weekBig;
		switch ( cc++ ) {
			case 26:
				toast( "umm, how are you doing~" );
				break;
			case 35:
				toast( "okay, great to see you here *.*" );
				VibrateUtil.vibrate( VibrateUtil.PATTERN_SHORT_SHORT_SHORT );
				break;
			case 45:
				toast( "share your ideas if you find something fun" );
				break;
			case 70:
				toast( "Take a chance but risky, or play it safe and loose everything" );
				break;
			case 99:
				toast( "well, you win finally!" );
				VibrateUtil.vibrate( VibrateUtil.PATTERN_LONG_SHORT_PAUSE_SHORT_SHORT );
				break;
			case 125:
				toast( "Move forward and risk it all, or play it safe and suffer defeat @#" );
				VibrateUtil.vibrate( VibrateUtil.PATTERN_LONG_SHORT_PAUSE_SHORT_SHORT );
				break;
		}
	}

//	@Override
//	public void onResume() {
//		if (setting.isDeveloperMode()) {
//			layoutEcustJwc.setVisibility(View.VISIBLE);
//		} else {
//			layoutEcustJwc.setVisibility(View.INVISIBLE);
//		}
//		super.onResume();
//	}
}
