package com.lovecust.tests.test;

import android.app.Activity;

import com.fisher.utils.AppUtil;

/**
 * Created by Fisher on 5/16/2016 at 13:51
 */
public class TestRouter {
	public final String START = "$# ";
	public final String COMMAND_STARTX = "startx";
	private Activity context;

	public TestRouter ( Activity context ) {
		this.context = context;
	}


	public void check ( String msg ) {
		if ( !msg.startsWith( START ) ) {
			return;
		}
		msg = msg.substring( 3 );
		switch ( msg ) {
			case COMMAND_STARTX:
				start( ActivityLauncherTestHome.class );
				break;
		}
	}

	private void start ( Class activity ) {
		AppUtil.startActivityFromCenter( context, activity );
	}
}
