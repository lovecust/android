package com.lovecust.modules.app.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.utils.AppProfileHelper;
import com.lovecust.utils.NetworkManager;
import com.lovecust.network.entities.AppProfile;
import com.lovecust.app.BaseActivity;
import com.lovecust.app.Setting;
import com.lovecust.app.R;
import com.lovecust.surfaces.DialogEdittext;
import com.lovecust.surfaces.DialogListView;
import com.fisher.utils.BugsUtil;
import com.fisher.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;


public class ActivityProfileHome extends BaseActivity implements View.OnClickListener {
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;

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

	private AppProfileHelper mAppProfile;

	@Override
	public int getLayout() {
		return R.layout.activity_profile_home;
	}

	@Override
	public void init() {
		setTitle(getString(R.string.title_profile)).setOnBackListener();

		mAppProfile = AppProfileHelper.getInstance();
		File temp = AppProfileHelper.getAvatarFile();
		if (null != temp && temp.exists()) {
			settingAvatar.setImageURI(Uri.fromFile(temp));
			if (null == mAppProfile.getAvatar() || "".equals(mAppProfile.getAvatar())) {
				NetworkManager.getLovecustNetworkManager()
						.updateProfileAvatar(temp)
						.subscribe(result -> {
							mAppProfile.setAvatar(result.getAvatar());
						}, Throwable::printStackTrace);
			}
		}
		renderViews();
	}

	public void renderViews() {
		mNickname.setText(mAppProfile.getNick());
		mUid.setText(mAppProfile.getUid());
		settingNickname.setText(mAppProfile.getNick());
		settingMoto.setText(mAppProfile.getMotto());
		settingMajor.setText(mAppProfile.getMajor());
		settingGender.setText(mAppProfile.getGenderText());
		settingBirthday.setText(mAppProfile.getBirthday());
		settingName.setText(mAppProfile.getName());
		settingPhoneNumber.setText(mAppProfile.getPhone());
		settingEmail.setText(mAppProfile.getEmail());
	}

	public void btnNick(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_nickname))
				.setEdittext(mAppProfile.getNick()).setHintText(getString(R.string.hint_profile_nick))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setNick(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnUid(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_uid)).toInputMethodNumber().setDigitsAndAlphabets()
				.setEdittext(mAppProfile.getUid()).setHintText(getString(R.string.hint_profile_uid))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						// TODO check uid form
						mAppProfile.setUid(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnMajor(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_major))
				.setEdittext(mAppProfile.getMajor()).setHintText(getString(R.string.hint_profile_major))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						// TODO check uid form
						mAppProfile.setMajor(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnMoto(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_moto))
				.setEdittext(mAppProfile.getMotto()).setHintText(getString(R.string.hint_profile_moto))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setMotto(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnGender(View view) {
		DialogListView.newDialog(this)
				.init(mAppProfile.getGenderTextArray())
				.setOnActionResultListener(new DialogListView.OnActionResultListener() {
					@Override
					public void onActionCommit(final int index) {
						mAppProfile.setGender(index);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnBirthday(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_birthday)).toInputMethodDate()
				.setEdittext(mAppProfile.getBirthday()).setHintText(getString(R.string.hint_profile_birthday))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setBirthday(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnPhone(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_phone_number)).toInputMethodPhone()
				.setEdittext(mAppProfile.getPhone()).setHintText(getString(R.string.hint_profile_phone))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setPhone(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnName(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_name))
				.setEdittext(mAppProfile.getName()).setHintText(getString(R.string.hint_profile_name))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setName(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnEmail(View view) {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_email)).toInputMethodWebEmail()
				.setEdittext(mAppProfile.getEmail()).setHintText(getString(R.string.hint_profile_email))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						mAppProfile.setEmail(input);
						renderViews();
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	public void btnImage(View view) {
		choosePhoto();
	}

	public void choosePhoto() {
		Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
		galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
		galleryIntent.setType("image/*");
		startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
	}


	private void resizeImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		if (null == data)
			return;
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			if (null == photo) {
				return;
			}
			try {
				photo.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(FileUtil.getInternalFile(Setting.FILE_PROFILE_AVATAR)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				BugsUtil.onFatalError("ActivityProfileHome.showResizeImage()-> failed to write file");
			}
			settingAvatar.setImageBitmap(photo);
			File temp = mAppProfile.getAvatarFile();
			if (null != temp && temp.exists()) {
				NetworkManager.getLovecustNetworkManager().updateProfileAvatar(temp)
						.subscribe(result -> {
							mAppProfile.setAvatar(result.getAvatar());
						}, err -> {
							err.printStackTrace();
						});
			}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode) {
				case IMAGE_REQUEST_CODE:
					resizeImage(data.getData());
					break;
				case RESIZE_REQUEST_CODE:
					showResizeImage(data);
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
