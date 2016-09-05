package com.lovecust.modules.ecust.wifi;

import android.view.View;
import android.widget.TextView;

import com.lovecust.app.AlphaActivity;
import com.lovecust.app.R;
import com.lovecust.surfaces.DialogConfirmation;
import com.lovecust.surfaces.DialogEdittext;

import butterknife.Bind;


public class ActivityEcustWifiHome extends AlphaActivity {

	@Bind(R.id.settingAutoConnect)
	 TextView settingAutoConnect;
	@Bind(R.id.tv_username)
	TextView settingUsername;
	@Bind(R.id.settingPassword)
	TextView settingPassword;

	private SettingWifi wifi;

	@Override
	public int getLayout() {
		return R.layout.activity_ecust_wifi_home;
	}

	@Override
	public void init () {
		setOnBackListener().setTitle(R.string.title_ecust_wifi);

		wifi = SettingWifi.getInstance();

		flush();
	}

	public void flush () {
		if(wifi.isAutoConnect()){
			WifiConnector.check();
		}
		settingAutoConnect.setText( wifi.isAutoConnectText() );
		settingUsername.setText( wifi.getUsername() );
		settingPassword.setText( wifi.getPassword() );
	}

	public void btnSettingUsername ( View view ) {
		DialogEdittext.newDialog( this ).init( getString( R.string.dialog_title_input_campus_id ) ).toInputMethodNumber().setDigitsAndAlphabets()
				.setEdittext( wifi.getUsername() ).setHintText( R.string.hint_profile_uid )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						wifi.setUsername( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnSettingPassword ( View view ) {
		DialogEdittext.newDialog( this ).init( getString( R.string.dialog_title_input_password ) ).toPasswordMode()
				.setEdittext( wifi.getPassword() )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						wifi.setPassword( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnSettingAutoConnect ( View view ) {
		if ( wifi.isAutoConnect() ) {
			DialogConfirmation.newDialog( this ).init( getString( R.string.dialog_title_confirmation_auto_connect ) )
					.setOnActionResultListener( new DialogConfirmation.OnActionResultListener() {
						@Override
						public void onActionCommit () {
							wifi.toggleAutoConnect();
							toast( getString( R.string.toast_setting_wifi_closed_auto_connect ) );
							flush();
						}

						@Override
						public void onActionCancel () {

						}
					} ).show();
		} else {
			if ( "".equals( wifi.getUsername() ) || "".equals( wifi.getPassword() ) ) {
				toast( getString( R.string.toast_setting_wifi_check_username_and_password ) );
				return;
			}
			wifi.toggleAutoConnect();
			toast( getString( R.string.toast_setting_wifi_started_auto_connect ) );
			flush();
		}
	}

	public void btnSettingCheckInternet(View view){
		WifiConnector.check();
	}

}
