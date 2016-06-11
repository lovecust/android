package com.lovecust.app.entity;


import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.lovecust.app.R;
import com.fisher.utils.AppUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

public class AppFeedback extends DataSupport implements Serializable {
	public static final String LOVECUST_ADMIN = "lovecust-admin";

	private static int colorName = AppUtil.getColor( R.color.text_feedback_name );
	private static int colorContent = AppUtil.getColor( R.color.text_feedback_content );
	private static int colorSystem = AppUtil.getColor( R.color.text_feedback_system );

	@SerializedName( "notExist" )
	private int id;
	// value is the key constant in sql syntax.
	@SerializedName( "value" )
	private String dataValue;
	// ctime is got from server time
	@SerializedName( "ctime" )
	private long dataCtime;
	@SerializedName( "nick" )
	private String dataNick;
	@SerializedName( "from" )
	private String dataFrom;
	@SerializedName( "to" )
	private String dataTo;
	@SerializedName( "read" )
	private boolean dataRead = false;

	public AppFeedback ( String nick, String value ) {
		this.dataNick = nick;
		this.dataValue = value;
		this.dataTo = LOVECUST_ADMIN;
	}

	public SpannableString getSpannableString ( ) {
		int a = dataNick.length() + 2;
		int b = dataNick.length() + 2 + dataValue.length();
		SpannableString spannableString = new SpannableString( dataNick + ": " + dataValue + "\n" );
		spannableString.setSpan( new ForegroundColorSpan( colorName ), 0, a, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
		if ( LOVECUST_ADMIN.equals( dataFrom ) )
			spannableString.setSpan( new ForegroundColorSpan( colorSystem ), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
		else
			spannableString.setSpan( new ForegroundColorSpan( colorContent ), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
		return spannableString;
	}

	public static void renderHistory ( TextView textView ) {
		List< AppFeedback > feedbacks = DataSupport.findAll( AppFeedback.class );
		if ( null == feedbacks )
			return;
		for ( int i = 0; i < feedbacks.size(); i++ ) {
			textView.append( feedbacks.get( i ).getSpannableString() );
		}
	}

	public static SpannableString getSystemSpannable ( String text ) {
		SpannableString message = new SpannableString( text + "\n" );
		message.setSpan( new ForegroundColorSpan( colorSystem ), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
		return message;
	}

	public int getId ( ) {
		return id;
	}

	public void setId ( int id ) {
		this.id = id;
	}

	public String getDataValue ( ) {
		return dataValue;
	}

	public void setDataValue ( String dataValue ) {
		this.dataValue = dataValue;
	}

	public long getDataCtime ( ) {
		return dataCtime;
	}

	public AppFeedback setDataCtime ( long dataCtime ) {
		this.dataCtime = dataCtime;
		return this;
	}

	public String getDataNick ( ) {
		return dataNick;
	}

	public void setDataNick ( String dataNick ) {
		this.dataNick = dataNick;
	}

	public String getDataFrom ( ) {
		return dataFrom;
	}

	public void setDataFrom ( String dataFrom ) {
		this.dataFrom = dataFrom;
	}

	public String getDataTo ( ) {
		return dataTo;
	}

	public void setDataTo ( String dataTo ) {
		this.dataTo = dataTo;
	}

	public boolean isDataRead ( ) {
		return dataRead;
	}

	public void setDataRead ( boolean dataRead ) {
		this.dataRead = dataRead;
	}
}
