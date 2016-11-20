package com.lovecust.modules.ecust.jwc;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.entities.EcustJwcNews;
import com.fisher.utils.app.BaseRecyclerViewAdapter;
import com.fisher.utils.app.BaseRecyclerViewHolder;
import com.lovecust.app.Setting;
import com.fisher.utils.ConsoleUtil;

public class AdapterJwcNews extends BaseRecyclerViewAdapter< EcustJwcNews > {
	private Context context;

	public AdapterJwcNews ( Context context ) {
		this.context = context;
	}

	@Override
	public int layoutId ( int viewType ) {
		return R.layout.item_ecust_jwc_news;
	}

	@Override
	public void onBindViewHolder ( BaseRecyclerViewHolder holder, int position ) {
		EcustJwcNews news = getItem( position );
		holder.setText( R.id.title, news.getName() );
		holder.setText( R.id.date, news.getDate() );
		if ( news.isRed() ) {
			( ( TextView ) holder.get( R.id.title ) ).setTextColor( Color.RED );
			ConsoleUtil.console( "set to red: " + position + " - " + news );
		} else {
			( ( TextView ) holder.get( R.id.title ) ).setTextColor( Color.BLACK );
		}
		if ( news.isBold() ) {
			( ( TextView ) holder.get( R.id.title ) ).setTypeface( Typeface.DEFAULT_BOLD );
			ConsoleUtil.console( "set to bold: " + position + " - " + news );
		} else {
			// TODO why this will cause chaos
			( ( TextView ) holder.get( R.id.title ) ).setTypeface( Typeface.DEFAULT );
		}
		holder.getView().setOnClickListener( v -> {
			Intent intent = new Intent( context, ActivityEcustJwcDetail.class )
					.putExtra( Setting.INTENT_KEY_DEFAULT, news.getMd5() )
					.putExtra( Setting.INTENT_KEY_TITLE, news.getName() )
					.putExtra( Setting.INTENT_KEY_DATE, news.getDate() );
			context.startActivity( intent );
		} );
	}
}
