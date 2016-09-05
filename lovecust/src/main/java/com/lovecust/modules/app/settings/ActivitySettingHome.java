package com.lovecust.modules.app.settings;


import android.view.View;

import com.lovecust.modules.app.about.ActivityAboutHome;
import com.lovecust.modules.ecust.wifi.ActivityEcustWifiHome;
import com.lovecust.modules.explore.todo.ActivityExploreTodoSetting;
import com.lovecust.app.AlphaActivity;
import com.lovecust.app.R;
import com.lovecust.app.AppSetting;
import com.fisher.utils.AppUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivitySettingHome extends AlphaActivity {

	@Bind(R.id.btn_explore_todo)
	View layoutTodo;

	private AppSetting appSetting;

	@Override
	public int getLayout() {
		return R.layout.activity_setting_home;
	}

	public void init() {
		setOnBackListener().setTitle(R.string.title_setting);
		appSetting = AppSetting.getInstance();
	}


	@OnClick(R.id.btn_ecust_wifi)
	void btnEcustWifi() {
		start(ActivityEcustWifiHome.class);
	}


	@OnClick(R.id.btn_general)
	void btnGeneral() {
		start(ActivitySettingGeneral.class);
	}

	@OnClick(R.id.btn_about)
	void btnAbout() {
		start(ActivityAboutHome.class);
	}

	@OnClick(R.id.btn_explore_todo)
	void btnTodo() {
		start(ActivityExploreTodoSetting.class);
	}

	private void start(Class activity) {
		AppUtil.startActivityFromRight(this, activity);
	}

//	@Override
//	protected void onResume ( ) {
//		if(appSetting.isDeveloperMode()){
//			layoutTodo.setVisibility(View.VISIBLE);
//		}else {
//			layoutTodo.setVisibility(View.GONE);
//		}
//		super.onResume();
//	}
}
