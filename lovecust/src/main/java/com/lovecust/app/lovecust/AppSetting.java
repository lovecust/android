package com.lovecust.app.lovecust;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lovecust.app.R;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.FileUtil;

import java.util.Locale;

public class AppSetting {

	private static AppSetting setting;
	private transient String[] mLanguageDialog;
	private transient String[] mDialogSwitch;

	public static AppSetting getInstance() {
		if (null != setting)
			return setting;
		String json = FileUtil.readFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_APP));
		if (!"".equals(json)) {
			try {
				setting = new Gson().fromJson(json, AppSetting.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				BugsUtil.onFatalError("SettingWifi.flush()-> json string format failed[ configure edited ]!");
			}
		}
		if (null == setting)
			setting = new AppSetting();
		return setting;
	}

	public void flush() {
		FileUtil.writeFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_APP), new Gson().toJson(this));
	}


	// 0 is default
	private int language = 0;
	private boolean developerMode = false;
	private int colorApp = AppUtil.getColor(R.color.app);



	public int getLanguage() {
		return language;
	}

	public String getLanguageString() {
		return getLanguageDialog()[getLanguage()];
	}

	public String getDeveloperModeString() {
		if (isDeveloperMode()) {
			return getDeveloperModeDialog()[0];
		}
		return getDeveloperModeDialog()[1];
	}

	public String[] getLanguageDialog() {
		if (null == mLanguageDialog)
			mLanguageDialog = AppUtil.getStringArray(R.array.dialog_general_language);
		return mLanguageDialog;
	}

	public String[] getDeveloperModeDialog() {
		if (null == mDialogSwitch)
			mDialogSwitch = AppUtil.getStringArray(R.array.dialog_switch);
		return mDialogSwitch;
	}

	public void setLanguage(int language) {
		flushLanguageMode();
		if (this.language == language)
			return;
		this.language = language;
		flush();
	}

	public boolean isDeveloperMode() {
		return developerMode;
	}

	public void setDeveloperMode(boolean developerMode) {
		if (this.developerMode == developerMode)
			return;
		this.developerMode = developerMode;
		flush();
	}

	public int getColorApp ( ) {
		return colorApp;
	}

	public void setColorApp ( int colorApp ) {
		if (this.colorApp == colorApp)
			return;
		this.colorApp = colorApp;
		flush();
	}

	// flush app language
	public void flushLanguageMode() {
		switch (getLanguage()) {
			case 0:
				break;
			case 1:
				AppUtil.changeLanguageMode(Locale.CHINA);
				break;
			case 2:
				AppUtil.changeLanguageMode(Locale.ENGLISH);
				break;
		}
	}
}
