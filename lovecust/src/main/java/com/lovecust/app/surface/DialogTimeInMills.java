package com.lovecust.app.surface;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;
import com.fisher.utils.TimeUtil;


public class DialogTimeInMills extends AlertDialog implements View.OnClickListener {

	public interface OnActionResultListener {
		void onActionCommit ( long time );
		void onActionCancel ();
	}

	private OnActionResultListener listener;
	private TextView mTitle;
	private EditText mEdittext;
	private TextView mReadableTime;
	private long mValue = 0;

	public static DialogTimeInMills newDialog( Context context ) {
		return new DialogTimeInMills( context );
	}

	private DialogTimeInMills( Context context ) {
		super( context );
	}

	public DialogTimeInMills init( String title ) {
		View view = View.inflate( getContext(), R.layout.dialog_time_in_millis, null );
		mTitle = (TextView) view.findViewById( R.id.textTitle );
		mEdittext = (EditText) view.findViewById( R.id.et_text);
		mEdittext.addTextChangedListener( new TextWatcher() {
			@Override
			public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

			}
			@Override
			public void onTextChanged( CharSequence s, int start, int before, int count ) {
				try {
					mValue = Long.valueOf( s.toString().trim() );
				} catch ( NumberFormatException e ) {
					e.printStackTrace();
				}
				mReadableTime.setText( TimeUtil.fnFormatIntervalTime( mValue ) );
			}
			@Override
			public void afterTextChanged( Editable s ) {

			}
		} );
		mReadableTime = (TextView) view.findViewById( R.id.textReadableTime );
		view.findViewById( R.id.mActionCommit ).setOnClickListener( this );
		view.findViewById( R.id.mActionCancel ).setOnClickListener( this );
		view.findViewById( R.id.textTime6h ).setOnClickListener( this );
		view.findViewById( R.id.textTime1h ).setOnClickListener( this );
		view.findViewById( R.id.textTime30mins ).setOnClickListener( this );
		view.findViewById( R.id.textTime10mins ).setOnClickListener( this );
		view.findViewById( R.id.textTime1min ).setOnClickListener( this );
		if ( null == title )
			title = "";
		mTitle.setText( title );
		setView( view );
		return this;
	}

	public DialogTimeInMills setTitle( String title ) {
		if ( null != mTitle )
			mTitle.setText( title );
		return this;
	}
	public DialogTimeInMills setEdittext( long time ) {
		mValue = time;
		mEdittext.setText( String.valueOf( time ) );
		mEdittext.setSelection( mEdittext.getText().length() );
		return this;
	}

	public DialogTimeInMills setOnActionResultListener( OnActionResultListener listener ) {
		this.listener = listener;
		return this;
	}


	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.mActionCommit:
				dismiss();
				if ( null != listener ) {
					try {
						mValue = Long.valueOf( mEdittext.getText().toString().trim() );
					} catch ( NumberFormatException e ) {
						e.printStackTrace();
					}
					listener.onActionCommit( mValue );
				}
				break;
			case R.id.mActionCancel:
				dismiss();
				if ( null != listener )
					listener.onActionCancel();
				break;
			case R.id.textTime6h:
				mValue = 6 * 60 * 60 * 1000;
				mEdittext.setText( String.valueOf( mValue ) );
				break;
			case R.id.textTime1h:
				mValue = 60 * 60 * 1000;
				mEdittext.setText( String.valueOf( mValue ) );
				break;
			case R.id.textTime30mins:
				mValue = 30 * 60 * 1000;
				mEdittext.setText( String.valueOf( mValue ) );
				break;
			case R.id.textTime10mins:
				mValue = 10 * 60 * 1000;
				mEdittext.setText( String.valueOf( mValue ) );
				break;
			case R.id.textTime1min:
				mValue = 60 * 1000;
				mEdittext.setText( String.valueOf( mValue ) );
				break;
		}
		mEdittext.setSelection( mEdittext.getText().length() );
	}
}
