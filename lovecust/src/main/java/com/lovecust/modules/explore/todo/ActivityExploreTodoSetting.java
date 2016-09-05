package com.lovecust.modules.explore.todo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.modules.explore.popWindow.FloatViewImage;
import com.lovecust.modules.explore.popWindow.ServicePopWindowFisher;
import com.lovecust.surfaces.DialogEdittext;
import com.lovecust.surfaces.DialogSelectorIcon;

import butterknife.Bind;
import butterknife.OnClick;


public class ActivityExploreTodoSetting extends BaseActivity {

	@Bind( R.id.settingImageSize )
	TextView settingImageSize;
	@Bind( R.id.iv_icon_selector )
	ImageView ivIcon;

	FloatViewImage mImageSetting;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_explore_todo_setting;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_explore_todo );

		mImageSetting = FloatViewImage.getInstance();

		btnStartPopWindowService( null );
		flush();
	}

	private void flush ( ) {
		settingImageSize.setText( mImageSetting.getWidthString() );
		ivIcon.setImageResource( mImageSetting.getResource() );
		btnStartPopWindowService( null );
	}

	public void btnStartPopWindowService ( View view ) {
		Intent intent = new Intent( this, ServicePopWindowFisher.class );
		startService( intent );
	}

	public void btnStopPopWindowService ( View view ) {
		Intent intent = new Intent( this, ServicePopWindowFisher.class );
		stopService( intent );
	}

	public void btnImageSize ( View view ) {
		DialogEdittext.newDialog( this ).init( "Set Float Image Size:" ).toInputMethodNumber()
				.setEdittext( String.valueOf( mImageSetting.getWidth() ) ).setHintText( "40" )
				.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit ( String input ) {
						try {
							int size = Integer.valueOf( input );
							mImageSetting.setWidth( size );
							mImageSetting.setHeight( size );
							flush();
						} catch ( NumberFormatException e ) {
							e.printStackTrace();
							toast( "Make sure your input is number" );
						}
					}

					@Override
					public void onActionCancel ( ) {

					}
				} ).show();
	}

	@OnClick( R.id.btn_icon_selector )
	void btnSelector ( ) {
		DialogSelectorIcon.newDialog( this ).init( null )
				.setOnActionResultListener( new DialogSelectorIcon.OnActionResultListener() {
					@Override
					public void onActionCommit ( int res ) {
						mImageSetting.setResource( res );
						flush();
					}
				} ).show();
	}

	void export ( ) {
//		TODO export data -> DataTodo
	}

	@Override
	protected void onDestroy ( ) {
		super.onDestroy();
	}
}
