package com.lovecust.modules.app.about;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.network.ApiManager;
import com.lovecust.entities.AppUpdateStatus;
import com.lovecust.app.AlphaActivity;
import com.lovecust.app.AppContext;
import com.lovecust.app.Setting;
import com.fisher.utils.FileUtil;
import com.fisher.utils.HTTPUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.TextUtil;

import java.io.File;

import butterknife.Bind;


public class ActivityAboutUpdate extends AlphaActivity {

	private static final int TARGET_DOWNLOAD_AND_INSTALL = -1;
	private static final int TARGET_INSTALL = -2;
	private static final int TARGET_ALREADY_UPDATED = -3;
	private static final int TARGET_CHECKING_FOR_UPDATE = -4;
	private static final int TARGET_DEVICE_OFFLINE = -5;
	private static final int TARGET_SERVER_ERROR = -6;

	@Bind( R.id.text )
	TextView text;
	@Bind( R.id.button )
	TextView button;

	private AppUpdateStatus update;
	private int mButtonTarget = -1;
	private File file;
	private ApiManager api;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_about_update;
	}

	@Override
	public void init ( ) {
		setTitle( getString( R.string.title_about_update ) ).setOnBackListener();
		api = ApiManager.getInstance();

		button.setOnClickListener( v -> {
			switch ( mButtonTarget ) {
				case TARGET_DOWNLOAD_AND_INSTALL:
					downloadAndInstall();
					break;
				case TARGET_INSTALL:
					install( file );
					break;
				case TARGET_ALREADY_UPDATED:
					toast( R.string.about_update_button_text_already_updated );
					break;
				case TARGET_CHECKING_FOR_UPDATE:
					toast( R.string.about_update_button_text_checking_for_update );
					break;
				case TARGET_DEVICE_OFFLINE:
					toast( R.string.about_update_button_text_device_offline );
					break;
				case TARGET_SERVER_ERROR:
					button.setText( R.string.about_update_button_text_server_error );
					break;
			}
		} );
		alterTarget( TARGET_CHECKING_FOR_UPDATE );

		if ( !NetUtil.isConnected ) {
			alterTarget( TARGET_DEVICE_OFFLINE );
			return;
		}
		if ( NetUtil.isOfflineAndToast() )
			return;
		api.appUpdateStatus().subscribe( res -> {
			update = res;
			flush();
		}, err -> {
			toast( R.string.toast_server_error );
			err.printStackTrace();
		} );
	}

	private void alterTarget ( int target ) {
		mButtonTarget = target;
		switch ( mButtonTarget ) {
			case TARGET_CHECKING_FOR_UPDATE:
				button.setText( R.string.about_update_button_text_checking_for_update );
				break;
			case TARGET_DEVICE_OFFLINE:
				button.setText( R.string.about_update_button_text_device_offline );
				break;
			case TARGET_SERVER_ERROR:
				button.setText( R.string.about_update_button_text_server_error );
				break;
			case TARGET_ALREADY_UPDATED:
				button.setText( R.string.about_update_button_text_already_updated );
				break;
			case TARGET_INSTALL:
				button.setText( R.string.about_update_button_text_install_now );
				break;
			case TARGET_DOWNLOAD_AND_INSTALL:
				button.setText( R.string.about_update_button_text_download_and_install );
				break;
		}
	}

	private void downloadAndInstall ( ) {
		alterTarget( TARGET_CHECKING_FOR_UPDATE );
		HTTPUtil.download( update.getUrl(), FileUtil.getExternalFile( Setting.FILE_EXTERNAL_FILE_APP_APP_UPDATE_APK ), new HTTPUtil.OnDownloadCompleteListener() {
			@Override
			public void onCompleted ( File file ) {
				alterTarget( TARGET_INSTALL );
				install( file );
			}

			@Override
			public void onFailed ( ) {
				alterTarget( TARGET_SERVER_ERROR );
				toast( "Sorry, Server Error!" );
			}
		} );
	}

	private void install ( File file ) {
		if ( file != null && file.exists() ) {
			String md5 = TextUtil.md5( file );
			log( "Exists file md5: " + md5 + "; Got md5 from server: " + update.getMd5() );
			if ( null != md5 && md5.equals( update.getMd5() ) ) {
				// found new version and already downloaded
				Intent promptInstall = new Intent( Intent.ACTION_VIEW )
						.setDataAndType( Uri.fromFile( file ), "application/vnd.android.package-archive" ).addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity( promptInstall );
			} else {
				alterTarget( TARGET_SERVER_ERROR );
				toast( "The hash code of downloaded code is desired! Please try manually!" );
			}
		} else {
			alterTarget( TARGET_SERVER_ERROR );
			toast( "Download failed and please try manually!" );
		}
	}

	private void flush ( ) {
		if ( null == update || null == text ) {
			return;
		}
		text.setText( update.toString() );

		if ( AppContext.mAppVersion.equals( update.getApp() ) || null == update.getUrl() || "".equals( update.getUrl() ) ) {
			// already up-to-date
			alterTarget( TARGET_ALREADY_UPDATED );
			return;
		}
		file = FileUtil.getExternalFile( Setting.FILE_EXTERNAL_FILE_APP_APP_UPDATE_APK );
		if ( file != null && file.exists() ) {
			String md5 = TextUtil.md5( file );
			log( "Exists file md5: " + md5 + "; Got md5 from server: " + update.getMd5() );
			if ( null != md5 && md5.equals( update.getMd5() ) ) {
				// found new version and already downloaded
				alterTarget( TARGET_INSTALL );
				return;
			}
		}
		// nor up-to-date or downloaded
		alterTarget( TARGET_DOWNLOAD_AND_INSTALL );
	}

}
