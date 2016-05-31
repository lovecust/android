package com.lovecust.app.view;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lovecust.app.R;
import com.lovecust.app.utils.AppUtil;

/*
* TODO customized icon
* set icon color and background
* */
public class CustomImageView extends ImageView {
	private Drawable drawable;

	public CustomImageView ( Context context ) {
		super( context );
	}

	public CustomImageView ( Context context, AttributeSet attrs ) {
		super( context, attrs );
	}

	public CustomImageView ( Context context, AttributeSet attrs, int defStyleAttr ) {
		super( context, attrs, defStyleAttr );
		setColorFilter( AppUtil.getColor(R.color.app), PorterDuff.Mode.SRC_IN);
	}

}
