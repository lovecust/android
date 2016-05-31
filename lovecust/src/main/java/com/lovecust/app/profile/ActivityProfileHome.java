package com.lovecust.app.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.app.entity.AppProfile;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.R;
import com.lovecust.app.surface.DialogEdittext;
import com.lovecust.app.surface.DialogListView;
import com.lovecust.app.utils.BugsUtil;
import com.lovecust.app.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;


public class ActivityProfileHome extends AlphaActivity implements View.OnClickListener {
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;

	private AppProfile appProfile;

	@Bind(R.id.settingNickname)
	TextView settingNickname;
	@Bind(R.id.settingMoto)
	TextView settingMoto;
	@Bind(R.id.settingGender)
	TextView settingGender;
	@Bind(R.id.settingBirthday)
	TextView settingBirthday;
	@Bind(R.id.settingName)
	TextView settingName;
	@Bind(R.id.settingPhoneNumber)
	TextView settingPhoneNumber;
	@Bind(R.id.settingEmail)
	TextView settingEmail;
	@Bind(R.id.avatar)
	ImageView settingAvatar;
	@Bind(R.id.nick)
	TextView mNickname;
	@Bind(R.id.settingUid)
	TextView mUid;
	@Bind(R.id.settingMajor)
	TextView settingMajor;

	@Override
	public int getLayout() {
		return R.layout.activity_profile_home;
	}

	@Override
	public void init() {
		setTitle( getString( R.string.title_profile ) ).setOnBackListener();

		appProfile = AppProfile.getInstance();

		File temp = appProfile.getAvatarFile();
		if ( null != temp && temp.exists() )
			settingAvatar.setImageURI( Uri.fromFile( temp ) );
		flush();
	}

	public void flush () {
		mNickname.setText( appProfile.getNick() );
		mUid.setText( appProfile.getUid() );
		settingNickname.setText( appProfile.getNick() );
		settingMoto.setText( appProfile.getMotto() );
		settingMajor.setText( appProfile.getMajor() );
		settingGender.setText( appProfile.getGenderText() );
		settingBirthday.setText( appProfile.getBirthday() );
		settingName.setText( appProfile.getName() );
		settingPhoneNumber.setText( appProfile.getPhone() );
		settingEmail.setText( appProfile.getEmail() );
	}

	public void btnNick ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_nickname ) )
				.setEdittext( appProfile.getNick() ).setHintText( getString( R.string.hint_profile_nick ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setNick( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnUid ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_uid ) ).toInputMethodNumber().setDigitsAndAlphabets()
				.setEdittext( appProfile.getUid() ).setHintText( getString( R.string.hint_profile_uid ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						// TODO check uid form
						appProfile.setUid( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnMajor ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_major ) )
				.setEdittext( appProfile.getMajor() ).setHintText( getString( R.string.hint_profile_major ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						// TODO check uid form
						appProfile.setMajor( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnMoto ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_moto ) )
				.setEdittext( appProfile.getMotto() ).setHintText( getString( R.string.hint_profile_moto ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setMotto( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnGender ( View view ) {
		DialogListView.newDialog( this )
				.init( appProfile.getGenderTextArray() )
				.setOnActionResultListener( new DialogListView.OnActionResultListener() {
					@Override
					public void onActionCommit ( final int index ) {
						appProfile.setGender( index );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnBirthday ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_birthday ) ).toInputMethodDate()
				.setEdittext( appProfile.getBirthday() ).setHintText( getString( R.string.hint_profile_birthday ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setBirthday( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnPhone ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_phone_number ) ).toInputMethodPhone()
				.setEdittext( appProfile.getPhone() ).setHintText( getString( R.string.hint_profile_phone ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setPhone( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnName ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_name ) )
				.setEdittext( appProfile.getName() ).setHintText( getString( R.string.hint_profile_name ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setName( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnEmail ( View view ) {
		DialogEdittext.newDialog( this )
				.init( getString( R.string.dialog_profile_set_email ) ).toInputMethodWebEmail()
				.setEdittext( appProfile.getEmail() ).setHintText( getString( R.string.hint_profile_email ) )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						input = input.replace( "\n\r\b", "" ).trim();
						appProfile.setEmail( input );
						flush();
					}

					@Override
					public void onActionCancel () {

					}
				} ).show();
	}

	public void btnImage ( View view ) {
		choosePhoto();
	}

	public void choosePhoto () {
		Intent galleryIntent = new Intent( Intent.ACTION_GET_CONTENT );
		galleryIntent.addCategory( Intent.CATEGORY_OPENABLE );
		galleryIntent.setType( "image/*" );
		startActivityForResult( galleryIntent, IMAGE_REQUEST_CODE );
	}


	private void resizeImage ( Uri uri ) {
		Intent intent = new Intent( "com.android.camera.action.CROP" );
		intent.setDataAndType( uri, "image/*" );
		intent.putExtra( "crop", "true" );
		intent.putExtra( "aspectX", 1 );
		intent.putExtra( "aspectY", 1 );
		intent.putExtra( "outputX", 150 );
		intent.putExtra( "outputY", 150 );
		intent.putExtra( "return-data", true );
		startActivityForResult( intent, RESIZE_REQUEST_CODE );
	}

	private void showResizeImage ( Intent data ) {
		if ( null == data )
			return;
		Bundle extras = data.getExtras();
		if ( extras != null ) {
			Bitmap photo = extras.getParcelable( "data" );
			try {
				photo.compress( Bitmap.CompressFormat.PNG, 100, new FileOutputStream( FileUtil.getInternalFile( Setting.FILE_PROFILE_AVATAR ) ) );
			} catch ( FileNotFoundException e ) {
				e.printStackTrace();
				BugsUtil.onFatalError( "ActivityProfileHome.showResizeImage()-> failed to write file" );
			}
			settingAvatar.setImageBitmap( photo );
		}
	}


	@Override
	protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
		if ( resultCode != RESULT_OK ) {
			return;
		} else {
			switch ( requestCode ) {
				case IMAGE_REQUEST_CODE:
					resizeImage( data.getData() );
					break;
				case RESIZE_REQUEST_CODE:
					showResizeImage( data );
					break;
			}
		}
		super.onActivityResult( requestCode, resultCode, data );
	}

	@Override
	protected void onStop () {
		super.onStop();
	}

}
