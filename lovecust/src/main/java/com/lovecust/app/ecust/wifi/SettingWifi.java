package com.lovecust.app.ecust.wifi;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lovecust.app.R;
import com.lovecust.app.lovecust.Setting;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.FileUtil;

import java.util.Calendar;


public class SettingWifi {
	// should be edited ?
	public static final String SSID_ECUST = "\"ECUST\"";
	private boolean autoConnect = false;
	private boolean sendNotification = true;
	private long cancelNotificationDelayTime = 8000;
	private String username = "";
	private String password = "";


	private static SettingWifi setting;

	public static SettingWifi getInstance() {
		if (null != setting)
			return setting;
		String json = FileUtil.readFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_ECUST_WIFI));
		if (!"".equals(json)) {
			try {
				setting = new Gson().fromJson(json, SettingWifi.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				BugsUtil.onFatalError("SettingWifi.flush()-> json string format failed[ configure edited ]!");
			}
		}
		if (null == setting)
			setting = new SettingWifi();
		return setting;
	}

	public void flush() {
		System.currentTimeMillis();
		Calendar.getInstance().getTime();
		FileUtil.writeFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_ECUST_WIFI), new Gson().toJson(this));
	}


	public boolean isAutoConnect() {
		if (autoConnect && !"".equals(getUsername()) && !"".equals(getPassword())) {
			return autoConnect;
		} else {
			setAutoConnect(false);
			return false;
		}
	}

	public String isAutoConnectText() {
		if (isAutoConnect()) {
			return AppUtil.getStringArray( R.array.dialog_switch )[0];
		} else {
			return AppUtil.getStringArray( R.array.dialog_switch )[1];
		}
	}

	public void setAutoConnect(boolean autoConnect) {
		if (this.autoConnect == autoConnect)
			return;
		this.autoConnect = autoConnect;
		flush();
	}

	public void toggleAutoConnect() {
		this.autoConnect = !autoConnect;
		flush();
	}

	public boolean isSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(boolean sendNotification) {
		if (this.sendNotification == sendNotification)
			return;
		this.sendNotification = sendNotification;
		flush();
	}

	public long getCancelNotificationDelayTime() {
		return cancelNotificationDelayTime;
	}

	public void setCancelNotificationDelayTime(long cancelNotificationDelayTime) {
		if (this.cancelNotificationDelayTime == cancelNotificationDelayTime)
			return;
		this.cancelNotificationDelayTime = cancelNotificationDelayTime;
		flush();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (this.username.equals(username))
			return;
		this.username = username;
		flush();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (this.password.equals(password))
			return;
		this.password = password;
		flush();
	}
}
