package com.lovecust.modules.ecust.wifi;

import android.view.View;
import android.widget.TextView;

import com.fisher.utils.LogUtil;
import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.surfaces.DialogConfirmation;
import com.lovecust.surfaces.DialogEdittext;

import butterknife.Bind;
import butterknife.OnClick;


public class ActivityEcustWifiHome extends BaseActivity {

	@Bind( R.id.settingAutoConnect )
	TextView settingAutoConnect;
	@Bind( R.id.tv_username )
	TextView settingUsername;
	@Bind( R.id.settingPassword )
	TextView settingPassword;

	private SettingWifi wifi;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_ecust_wifi_home;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_ecust_wifi );

		wifi = SettingWifi.getInstance();

		render();
	}

	/**
	 * Render the configuration data to view.
	 */
	public void render ( ) {
		if ( wifi.isAutoConnect() ) {
			WifiConnector.checkConnection();
		}
		settingAutoConnect.setText( wifi.isAutoConnectText() );
		settingUsername.setText( wifi.getUsername() );
		settingPassword.setText( wifi.getPassword() );
	}

	@OnClick( { R.id.rl_auto_connect, R.id.rl_check_connection, R.id.rl_pwd, R.id.rl_cid } )
	public void onBtnClick ( View view ) {
		switch ( view.getId() ) {
			case R.id.rl_auto_connect:
				if ( wifi.isAutoConnect() ) {
					DialogConfirmation.newDialog( this ).init( getString( R.string.dialog_title_confirmation_auto_connect ) )
							.setOnActionResultListener( new DialogConfirmation.OnActionResultListener() {
								@Override
								public void onActionCommit ( ) {
									wifi.toggleAutoConnect();
									toast( getString( R.string.toast_setting_wifi_closed_auto_connect ) );
									render();
								}

								@Override
								public void onActionCancel ( ) {

								}
							} ).show();
				} else {
					if ( "".equals( wifi.getUsername() ) || "".equals( wifi.getPassword() ) ) {
						toast( getString( R.string.toast_setting_wifi_check_username_and_password ) );
						return;
					}
					wifi.toggleAutoConnect();
					toast( getString( R.string.toast_setting_wifi_started_auto_connect ) );
					render();
				}
				break;
			case R.id.rl_cid:
				DialogEdittext.newDialog( this ).init( getString( R.string.dialog_title_input_campus_id ) ).toInputMethodNumber().setDigitsAndAlphabets()
						.setEdittext( wifi.getUsername() ).setHintText( R.string.hint_profile_uid )
						.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
							@Override
							public void onActionCommit ( String input ) {
								wifi.setUsername( input );
								render();
							}

							@Override
							public void onActionCancel ( ) {

							}
						} ).show();
				break;
			case R.id.rl_pwd:
				DialogEdittext.newDialog( this ).init( getString( R.string.dialog_title_input_password ) ).toPasswordMode()
						.setEdittext( wifi.getPassword() )
						.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
							@Override
							public void onActionCommit ( String input ) {
								wifi.setPassword( input );
								render();
							}

							@Override
							public void onActionCancel ( ) {

							}
						} ).show();
				break;
			case R.id.rl_check_connection:
				WifiConnector.checkConnection();
				break;
			default:
				LogUtil.e( R.string.error_fatal_unhandled_click_event );
				break;
		}
	}

}
