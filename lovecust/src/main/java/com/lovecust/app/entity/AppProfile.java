package com.lovecust.app.entity;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lovecust.app.R;
import com.lovecust.app.api.ApiManager;
import com.lovecust.app.lovecust.Setting;
import com.fisher.utils.AppUtil;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.FileUtil;

import java.io.File;

public class AppProfile {

	private static AppProfile setting;
	private transient String[] mStringArrayGender;

	public static AppProfile getInstance ( ) {
		if ( null != setting )
			return setting;
		String json = FileUtil.readFileWithoutException( FileUtil.getInternalFile( Setting.FILE_GSON_SETTING_USER_PROFILE ) );
		if ( !"".equals( json ) ) {
			try {
				setting = new Gson().fromJson( json, AppProfile.class );
			} catch ( JsonSyntaxException e ) {
				e.printStackTrace();
				BugsUtil.onFatalError( "SettingWifi.flush()-> json string format failed[ configure edited ]!" );
			}
		}
		if ( null == setting )
			setting = new AppProfile();
		return setting;
	}

	public void flush ( ) {
		FileUtil.writeFileWithoutException( FileUtil.getInternalFile( Setting.FILE_GSON_SETTING_USER_PROFILE ), toString() );
		sync();
	}

	private String uid = "";
	private String name = "";
	private String nick = "";
	private String ename = "";
	private int gender = 0;
	private String major = "";
	private String birthday = "";
	private String avatar = "";
	private String motto = "";
	private String phone = "";
	private String qq = "";
	private String email = "";

	public String getUid ( ) {
		return uid;
	}

	public void setUid ( String uid ) {
		if ( this.uid.equals( uid ) )
			return;
		this.uid = uid;
		flush();
	}

	public String getNick ( ) {
		return nick;
	}

	public void setNick ( String nick ) {
		if ( this.nick.equals( nick ) )
			return;
		this.nick = nick;
		flush();
	}

	public String getMajor ( ) {
		return major;
	}

	public void setMajor ( String major ) {
		this.major = major;
	}

	public String getBirthday ( ) {
		return birthday;
	}

	public void setBirthday ( String birthday ) {
		if ( this.birthday.equals( birthday ) )
			return;
		this.birthday = birthday;
		flush();
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		if ( this.name.equals( name ) )
			return;
		this.name = name;
		flush();
	}

	public String getEname ( ) {
		return ename;
	}

	public void setEname ( String ename ) {
		if ( this.ename.equals( ename ) )
			return;
		this.ename = ename;
		flush();
	}

	public int getGender ( ) {
		return gender;
	}

	public String getGenderText ( ) {
		return getGenderTextArray()[ getGender() ];
	}

	public String[] getGenderTextArray ( ) {
		if ( null == mStringArrayGender )
			mStringArrayGender = AppUtil.getStringArray( R.array.dialog_gender );
		return mStringArrayGender;
	}

	public void setGender ( int gender ) {
		if ( this.gender == ( gender ) )
			return;
		this.gender = gender;
		flush();
	}

	public String getAvatar ( ) {
		return avatar;
	}

	public File getAvatarFile ( ) {
		return FileUtil.getInternalFile( Setting.FILE_PROFILE_AVATAR );
	}

	public void setAvatar ( String avatar ) {
		if ( this.avatar.equals( avatar ) )
			return;
		this.avatar = avatar;
		flush();
	}

	public String getMotto ( ) {
		return motto;
	}

	public void setMotto ( String motto ) {
		if ( this.motto.equals( motto ) )
			return;
		this.motto = motto;
		flush();
	}

	public String getPhone ( ) {
		return phone;
	}

	public void setPhone ( String phone ) {
		if ( this.phone.equals( phone ) )
			return;
		this.phone = phone;
		flush();
	}

	public String getQq ( ) {
		return qq;
	}

	public void setQq ( String qq ) {
		if ( this.qq.equals( qq ) )
			return;
		this.qq = qq;
		flush();
	}

	public String getEmail ( ) {
		return email;
	}

	public void setEmail ( String email ) {
		if ( this.email.equals( email ) )
			return;
		this.email = email;
		flush();
	}

	@Override
	public String toString ( ) {
		return new Gson().toJson( this );
	}

	public void sync ( ) {
		ApiManager manager = ApiManager.getInstance();
		manager.appProfileUpdate( this )
				.subscribe( response -> {
					ConsoleUtil.console( "AppProfile.sync()-> Succeeded sync profiles" );
				}, err -> {
					err.printStackTrace();
				} );
	}
}
