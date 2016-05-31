package com.lovecust.app.ecust.library;


import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.utils.AppUtil;
import com.lovecust.app.utils.TextUtil;

public class LibraryFloorHolder {

	private View view;
	private TextView description;
	private TextView bar;
	private TextView occupied;
	private TextView available;


	public LibraryFloorHolder ( Context context ) {
		view = View.inflate( context, R.layout.item_ecust_library, null );
		description = (TextView) view.findViewById( R.id.description );
		bar = (TextView) view.findViewById( R.id.bar );
		occupied = (TextView) view.findViewById( R.id.occupied );
		available = (TextView) view.findViewById( R.id.available );
	}

	public void render ( int floor, int occupiedAmount, int availableAmount ) {
//		description.setText( ( floor + 1 ) + AppUtil.getString( R.string.ecust_library_text_floor ) + ( availableAmount ) + AppUtil.getString( R.string.ecust_library_text_available ) );
//		description.setText( TextUtil.fnSetSpan( String.valueOf( floor + 1 ), AppUtil.getColor( R.color.red ) ));
		description.setText( (floor + 1)+AppUtil.getString( R.string.ecust_library_text_floor ) );
		description.append( TextUtil.fnSetSpan( String.valueOf( availableAmount ), AppUtil.getColor( R.color.red ) ) );
		description.append( AppUtil.getString( R.string.ecust_library_text_available ) );


		occupied.setText( String.valueOf( occupiedAmount ) );
		available.setText( String.valueOf( availableAmount ) );
		int width = (int) ( view.getWidth() * ( ( (float) occupiedAmount ) / ( occupiedAmount + availableAmount ) ) );
//		bar.setWidth( width );
		bar.setLayoutParams( new RelativeLayout.LayoutParams( width, RelativeLayout.LayoutParams.MATCH_PARENT ) );
	}

	public View getView () {
		return view;
	}
}
