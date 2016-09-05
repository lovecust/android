package com.lovecust.surfaces;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lovecust.app.R;
import com.lovecust.app.AppContext;
import com.lovecust.app.AppSetting;

/*
* TODO app-style icon
* set icon color and background
* */
public class AppImageView extends ImageView {

	public static final String ICON_STYLE_APP = "app";
	public static final String ICON_STYLE_BRIGHT = "bright";
	public static final String ICON_STYLE_DARKER = "darker";
	private String iconStyle;

	public AppImageView ( Context context ) {
		this( context, null );
	}

	public AppImageView ( Context context, AttributeSet attrs ) {
		this( context, attrs, 0 );
	}

	public AppImageView ( Context context, AttributeSet attrs, int defStyleAttr ) {
		super( context, attrs, defStyleAttr );
		TypedArray a = context.getTheme().obtainStyledAttributes( attrs, R.styleable.AppImageView, defStyleAttr, 0 );
		for ( int i = 0; i < a.getIndexCount(); i++ ) {
			switch ( a.getIndex( i ) ) {
				case R.styleable.AppImageView_iconStyle:
					iconStyle = a.getString( a.getIndex( i ) );
					break;
			}
		}
	}

	@Override
	protected void onMeasure ( int widthMeasureSpec, int heightMeasureSpec ) {
		super.onMeasure( widthMeasureSpec, heightMeasureSpec );
	}

	@Override
	protected void onDraw ( Canvas canvas ) {
		super.onDraw( canvas );
		// TODO change it when release
//		setColorFilter( AppSetting.getInstance().getColorApp(), PorterDuff.Mode.SRC_IN );
		setColorFilter( getColor(), PorterDuff.Mode.SRC_IN );
//		setColorFilter( 0xff9900ff, PorterDuff.Mode.SRC_IN);
//		setOnTouchListener( ( v, event ) -> {
//			switch ( event.getAction() ){
//				case MotionEvent.ACTION_DOWN:
//					setColorFilter( getPressColor(), PorterDuff.Mode.SRC_IN );
//					break;
//				case MotionEvent.ACTION_UP:
//					setColorFilter( getColor(), PorterDuff.Mode.SRC_IN );
//					break;
//			}
//			return false;
//		} );
	}


	private int getColor ( ) {
		if ( null == iconStyle )
			iconStyle = ICON_STYLE_APP;
		int color = Color.WHITE;
		switch ( iconStyle ) {
			case ICON_STYLE_APP:
				if ( AppContext.mDebug )
					color = Color.BLUE;
				else
					color = AppSetting.getInstance().getColorApp();
				break;
			case ICON_STYLE_BRIGHT:
				color = Color.WHITE;
				break;
			case ICON_STYLE_DARKER:
				color = Color.BLACK;
				break;
		}
		return color;
	}
//
//	private int getPressColor ( ) {
//		if ( null == iconStyle )
//			iconStyle = ICON_STYLE_APP;
//		int color = Color.WHITE;
//		switch ( iconStyle ) {
//			case ICON_STYLE_APP:
//				color = Color.WHITE;
//				break;
//			case ICON_STYLE_BRIGHT:
//				color = Color.GRAY;
//				break;
//			case ICON_STYLE_DARKER:
//				color = Color.DKGRAY;
//				break;
//		}
//		return color;
//	}

}
