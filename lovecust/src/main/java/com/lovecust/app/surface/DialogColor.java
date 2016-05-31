package com.lovecust.app.surface;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;


public class DialogColor extends AlertDialog implements View.OnClickListener {


	public static int mRecent = 0xFF663300;

	public interface OnActionResultListener {
		void onActionCommit ( long time );
		void onActionCancel ();
	}

	private OnActionResultListener listener;
	private TextView mTitle;
	private EditText mEdittext;
	private TextView mColorCurrent;
	private int mValue = 0;

	public static DialogColor newDialog( Context context ) {
		return new DialogColor( context );
	}

	private DialogColor( Context context ) {
		super( context );
	}

	public DialogColor init( String title ) {
		View view = View.inflate( getContext(), R.layout.dialog_color, null );
		mTitle = (TextView) view.findViewById( R.id.textTitle );
		mEdittext = (EditText) view.findViewById( R.id.et_text);
		mEdittext.addTextChangedListener( new TextWatcher() {
			@Override
			public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

			}
			@Override
			public void onTextChanged( CharSequence s, int start, int before, int count ) {
				String color = s.toString().trim();
				if ( !color.startsWith( "#" ) ) {
					mEdittext.setText( "#" + color );
					return;
				}
				try {
					mValue = Integer.valueOf( s.toString().trim().substring( 1 ) );
					mColorCurrent.setBackgroundColor( mValue );
				} catch ( NumberFormatException e ) {
					e.printStackTrace();
				}
			}
			@Override
			public void afterTextChanged( Editable s ) {

			}
		} );
		view.findViewById( R.id.mActionCommit ).setOnClickListener( this );
		view.findViewById( R.id.mActionCancel ).setOnClickListener( this );
		mColorCurrent = (TextView) view.findViewById( R.id.textColorCurrent );
		TextView mColorRecent = (TextView) view.findViewById( R.id.textColorRecent );
		mColorRecent.setBackgroundColor( mRecent );
		mColorRecent.setOnClickListener( this );
		view.findViewById( R.id.textColorBlue ).setOnClickListener( this );
		view.findViewById( R.id.textColorBrown ).setOnClickListener( this );
		view.findViewById( R.id.textColorGreen ).setOnClickListener( this );
		view.findViewById( R.id.textColorPurple ).setOnClickListener( this );
		view.findViewById( R.id.textColorRed ).setOnClickListener( this );
		view.findViewById( R.id.textColorYellow ).setOnClickListener( this );
		if ( null == title )
			title = "";
		mTitle.setText( title );
		setView( view );
		return this;
	}

	public DialogColor setTitle( String title ) {
		if ( null != mTitle )
			mTitle.setText( title );
		return this;
	}
	public DialogColor setEdittext( int time ) {
		mValue = time;
		mEdittext.setText( "#" + String.valueOf( time ) );
		mColorCurrent.setBackgroundColor( mValue );
		return this;
	}

	public DialogColor setOnActionResultListener( OnActionResultListener listener ) {
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
						mValue = Integer.valueOf( mEdittext.getText().toString().substring( 1 ).trim() );
					} catch ( NumberFormatException e ) {
						e.printStackTrace();
					}
					listener.onActionCommit( mValue );
				}
				mRecent = mValue;
				break;
			case R.id.mActionCancel:
				dismiss();
				if ( null != listener )
					listener.onActionCancel();
				break;
			case R.id.textColorRecent:
				mValue = mRecent;
				break;
			case R.id.textColorBlue:
				mValue = 0xFF0000FF;
				break;
			case R.id.textColorBrown:
				mValue = 0xFF663300;
				break;
			case R.id.textColorGreen:
				mValue = 0xFF00FF00;
				break;
			case R.id.textColorPurple:
				mValue = 0xFF9900FF;
				break;
			case R.id.textColorRed:
				mValue = 0xFFFF0000;
				break;
			case R.id.textColorYellow:
				mValue = 0xFFFFFF00;
				break;
		}
		mEdittext.setText( "#" + Integer.toString( mValue, 16 ) );
		mEdittext.setSelection( mEdittext.getText().length() );
	}
}
