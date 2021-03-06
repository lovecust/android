package com.lovecust.modules.ecust.wifi;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lovecust.app.R;
import com.lovecust.app.Setting;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.FileUtil;

import java.util.Calendar;

/**
 * `Created` by Fisher at 17:22 on 2017-02-19.
 */
public class SettingWifi {
	// should be edited ?
	private boolean autoConnect = false;
	private boolean sendNotification = true;
	private long cancelNotificationDelayTime = 8000;
	private String username = "";
	private String password = "";
	private int channel = 16;

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
				BugsUtil.onFatalError("SettingWifi.save()-> json string format failed[ configure edited ]!");
			}
		}
		if (null == setting)
			setting = new SettingWifi();
		return setting;
	}

	public void save() {
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
			return AppUtil.getStringArray(R.array.dialog_switch)[0];
		} else {
			return AppUtil.getStringArray(R.array.dialog_switch)[1];
		}
	}

	public void setAutoConnect(boolean autoConnect) {
		if (this.autoConnect == autoConnect)
			return;
		this.autoConnect = autoConnect;
		save();
	}

	public void toggleAutoConnect() {
		this.autoConnect = !autoConnect;
		save();
	}

	public boolean isSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(boolean sendNotification) {
		if (this.sendNotification == sendNotification)
			return;
		this.sendNotification = sendNotification;
		save();
	}

	public long getCancelNotificationDelayTime() {
		return cancelNotificationDelayTime;
	}

	public void setCancelNotificationDelayTime(long cancelNotificationDelayTime) {
		if (this.cancelNotificationDelayTime == cancelNotificationDelayTime)
			return;
		this.cancelNotificationDelayTime = cancelNotificationDelayTime;
		save();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (this.username.equals(username))
			return;
		this.username = username;
		save();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (this.password.equals(password))
			return;
		this.password = password;
		save();
	}

	public int getChannel() {
		return channel;
	}

	public SettingWifi setChannel(int channel) {
		this.channel = channel;
		return this;
	}
}
