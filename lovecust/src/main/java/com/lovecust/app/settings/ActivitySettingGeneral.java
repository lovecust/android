package com.lovecust.app.settings;

import android.view.View;
import android.widget.TextView;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.AppSetting;
import com.lovecust.app.R;
import com.lovecust.app.surface.DialogConfirmation;
import com.lovecust.app.surface.DialogListView;
import com.lovecust.app.utils.AppUtil;

import butterknife.Bind;
import butterknife.OnClick;


public class ActivitySettingGeneral extends AlphaActivity {

	@Bind(R.id.tv_language)
	TextView settingLanguage;
	@Bind(R.id.tv_developer_mode)
	TextView settingDeveloperMode;

	private AppSetting setting;

	@Override
	public int getLayout() {
		return R.layout.activity_setting_general;
	}

	public void init() {
		setOnBackListener().setTitle(R.string.title_setting_general);

		setting = AppSetting.getInstance();

		flush();
	}

	private void flush() {
		settingLanguage.setText(setting.getLanguageString());
		settingDeveloperMode.setText(setting.getDeveloperModeString());
	}

	public void btnLanguage(View view) {
		DialogListView.newDialog(this)
				.init(setting.getLanguageDialog()).setOnActionResultListener(new DialogListView.OnActionResultListener() {
			@Override
			public void onActionCommit(int index) {
				setting.setLanguage(index);
				flush();
//				startActivity( new Intent( ActivitySettingGeneral.this, ActivityLauncherHome.class ) );
//				overridePendingTransition( R.anim.activity_zoom_out_from_center, R.anim.activity_zoom_in_from_center );
			}

			@Override
			public void onActionCancel() {

			}
		}).show();
	}

	@OnClick(R.id.btn_developer_mode)
	void btnDeveloperMode() {
		DialogConfirmation.newDialog(this).init(AppUtil.getString(R.string.dialog_title_developer_mode)).toYesNoChoice()
				.setOnActionResultListener(new DialogConfirmation.OnActionResultListener() {
			@Override
			public void onActionCommit() {
				setting.setDeveloperMode(true);
				flush();
			}

			@Override
			public void onActionCancel() {
				setting.setDeveloperMode(false);
				flush();
			}
		}).show();
	}

}
