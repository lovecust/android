package com.lovecust.app.launcher.grid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovecust.app.R;
import com.lovecust.app.ecust.jwc.ActivityEcustJWCHome;
import com.lovecust.app.ecust.wifi.ActivityEcustWifiHome;
import com.lovecust.app.ecust.wifi.SettingWifi;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.AlphaFragment;
import com.lovecust.app.lovecust.AppSetting;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentLauncherPageNav extends AlphaFragment {

	@Bind(R.id.btn_ecust_jwc)
	View layoutEcustJwc;
	@Bind(R.id.btn_ecust_wifi)
	View layoutEcustWifi;

	private AppSetting setting;
	private SettingWifi wifi;

	@Override
	public int getLayoutID() {
		return R.layout.fragment_launcher_page_nav;
	}

	@Override
	public void init() {
		setting = AppSetting.getInstance();
		wifi = SettingWifi.getInstance();

		if (wifi.isAutoConnect()) {
			layoutEcustWifi.setVisibility(View.INVISIBLE);
		} else {
			layoutEcustWifi.setVisibility(View.VISIBLE);
		}
	}


	@OnClick(R.id.btn_ecust_jwc)
	void btnEcustJwc() {
		btnStartActivity(ActivityEcustJWCHome.class);
	}

	@OnClick(R.id.btn_ecust_wifi)
	void btnEcustWifi() {
		btnStartActivity(ActivityEcustWifiHome.class);
	}

	private void btnStartActivity(Class<? extends AlphaActivity> activity) {
		getActivity().startActivity(new Intent(getActivity(), activity));
		getActivity().overridePendingTransition(R.anim.activity_zoom_in_from_center, R.anim.activity_zoom_out_from_center);
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
