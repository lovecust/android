package com.lovecust.utils;

import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lovecust.app.R;
import com.lovecust.app.Setting;
import com.lovecust.network.entities.AppProfile;

import java.io.File;

/**
 * Created by fisher at 3:33 AM on 12/19/16.
 * <p>
 * AppProfile Helper class.
 */

public class AppProfileHelper extends AppProfile {

	private transient String[] mStringArrayGender;

	private static AppProfileHelper profile;

	public static AppProfileHelper getInstance() {
		if (null != profile) {return profile;}
		String json = FileUtil.readFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_USER_PROFILE));
		if (!"".equals(json)) {
			try {
				profile = AppProfileHelper.fromJSON(json);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				BugsUtil.onFatalError("SettingWifi.renderViews()-> json string format failed[ configure edited ]!");
			}
		}
		if (null == profile) {profile = new AppProfileHelper();}
		return profile;
	}

	private AppProfileHelper() {
	}

	/**
	 * Get local singleton object storage json.
	 *
	 * @return Local stored fields.
	 */
	public static String getLocal() {
		return "";
	}

	/**
	 * Save singleton object to local storage.
	 *
	 * @param object Any object.
	 */
	public static void save(Object object) {
		String name = object.getClass().getName();
		return;
	}

	public void flush() {
		// Save this object to Local Storage.
		FileUtil.writeFileWithoutException(FileUtil.getInternalFile(Setting.FILE_GSON_SETTING_USER_PROFILE), toJSON());
		sync();
	}

	public String getGenderText() {
		return getGenderTextArray()[getGender()];
	}

	public String[] getGenderTextArray() {
		if (null == mStringArrayGender) {
			mStringArrayGender = AppUtil.getStringArray(R.array.dialog_gender);
		}
		return mStringArrayGender;
	}

	/**
	 * Get avatar file.
	 * @return Avatar File.
	 */
	public static File getAvatarFile() {
		return FileUtil.getInternalFile(Setting.FILE_PROFILE_AVATAR);
	}

	@Override
	public void setUid(String uid) {
		if (getUid().equals(uid)) {return;}
		super.setUid(uid);
		flush();
	}

	@Override
	public void setNick(String nick) {
		if (getNick().equals(nick)) {return;}
		super.setNick(nick);
		flush();
	}

	@Override
	public void setMajor(String major) {
		if (getMajor().equals(major)) {return;}
		super.setMajor(major);
		flush();
	}

	@Override
	public void setBirthday(String birthday) {
		if (getBirthday().equals(birthday)) {return;}
		super.setBirthday(birthday);
		flush();
	}

	@Override
	public void setName(String name) {
		if (getName().equals(name)) {return;}
		super.setName(name);
		flush();
	}

	@Override
	public void setEname(String ename) {
		if (getEname().equals(ename)) {return;}
		super.setEname(ename);
		flush();
	}

	@Override
	public void setGender(int gender) {
		if (getGender() == (gender)) {return;}
		super.setGender(gender);
		flush();
	}

	@Override
	public void setAvatar(String avatar) {
		if (getAvatar().equals(avatar)) {return;}
		super.setAvatar(avatar);
		flush();
	}

	@Override
	public void setMotto(String motto) {
		if (getMotto().equals(motto)) {return;}
		super.setMotto(motto);
		flush();
	}

	@Override
	public void setPhone(String phone) {
		if (getPhone().equals(phone)) {return;}
		super.setPhone(phone);
		flush();
	}

	@Override
	public void setQq(String qq) {
		if (getQq().equals(qq)) {return;}
		super.setQq(qq);
		flush();
	}

	@Override
	public void setEmail(String email) {
		if (getEmail().equals(email)) {return;}
		super.setEmail(email);
		flush();
	}

	/**
	 * Sync user profile
	 */
	public void sync() {
		NetworkManager.getLovecustNetworkManager()
				.updateProfile(profile)
				.subscribe(response -> {
					ConsoleUtil.log("AppProfile.sync()-> Succeeded sync profiles");
				}, err -> {
					err.printStackTrace();
				});
	}

	@Override
	public String toString() {
		return toJSON();
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this);
	}

	public static AppProfileHelper fromJSON(String json) {
		return new Gson().fromJson(json, AppProfileHelper.class);
	}
}
