package com.lovecust.surfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;


import com.lovecust.app.R;
import com.fisher.utils.TimeUtil;

import java.util.Calendar;


public class DialogTimeClock extends AlertDialog implements View.OnClickListener {


	public interface OnActionResultListener {
		void onActionCommit ( long time );
		void onActionCancel ();
	}

	private OnActionResultListener listener;
	private TextView mTitle;
	private TextView mTimeIndicator;
	private TimePicker mTimePicker;
	private Calendar calendar;

	public static DialogTimeClock newDialog( Context context ) {
		return new DialogTimeClock( context );
	}

	private DialogTimeClock( Context context ) {
		super( context );
	}

	public DialogTimeClock init( String title ) {
		View view = View.inflate( getContext(), R.layout.dialog_time_clock, null );
		mTitle = (TextView) view.findViewById( R.id.textTitle );
		mTimeIndicator = (TextView) view.findViewById( R.id.mTimeIndicator );

		mTimePicker = (TimePicker) view.findViewById( R.id.mTimePicker );
		mTimePicker.setIs24HourView( true );
		mTimePicker.setOnTimeChangedListener( new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged( TimePicker view, int hour, int minute ) {
				flush(hour, minute);
			}
		} );
		view.findViewById( R.id.mActionCommit ).setOnClickListener( this );
		view.findViewById( R.id.mActionCancel ).setOnClickListener( this );
		if ( null == title )
			title = "";
		mTitle.setText( title );
		setView( view );
		return this;
	}
	private void flush( int hour, int minute ) {
		int cHour = TimeUtil.getHour();
		int cMinute = TimeUtil.getMinute();
		calendar = Calendar.getInstance();
		calendar.set( Calendar.SECOND, 0 );
		calendar.set( Calendar.MILLISECOND, 0 );
		calendar.set( Calendar.HOUR_OF_DAY, hour );
		calendar.set( Calendar.MINUTE, minute );
		if ( hour < cHour || ( hour == cHour && minute <= cMinute ) ) {
			calendar.add( Calendar.DAY_OF_MONTH, 1 );
		}
		mTimeIndicator.setText( "alarm at: " + TimeUtil.fnFormatTime( calendar.getTimeInMillis() ) );
		mTimeIndicator.append( "\nalarm after: " + TimeUtil.fnFormatIntervalTime( calendar.getTimeInMillis() - System.currentTimeMillis() ) );
	}

	public DialogTimeClock setTitle( String title ) {
		if ( null != mTitle )
			mTitle.setText( title );
		return this;
	}
	public DialogTimeClock setTime( long time ) {
		mTimePicker.setCurrentHour( TimeUtil.getHour( time ) );
		mTimePicker.setCurrentMinute( TimeUtil.getMinute( time ) );
		return this;
	}

	public DialogTimeClock setOnActionResultListener( OnActionResultListener listener ) {
		this.listener = listener;
		return this;
	}


	@Override
	public void onClick( View v ) {
		dismiss();
		switch ( v.getId() ) {
			case R.id.mActionCommit:
				if ( null != listener ) {
					flush( mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute() );
					listener.onActionCommit( calendar.getTimeInMillis());
				}
				break;
			case R.id.mActionCancel:
				if ( null != listener )
					listener.onActionCancel();
				break;
		}
	}
}
