package com.lovecust.app.lovecust;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.utils.BugsUtil;
import com.lovecust.app.utils.ConsoleUtil;
import com.lovecust.app.utils.ToastUtil;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public abstract class AlphaActivity extends SwipeBackActivity implements View.OnClickListener {

	private boolean exit = true;
	protected boolean isSwipeExit = true;

	public abstract int getLayout();
	public abstract void init();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		ButterKnife.bind(this);
		if(isSwipeExit){
			getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		}else {
			getSwipeBackLayout().setEnableGesture(false);
		}
		init();
	}

	@Override
	protected void onDestroy() {
		ButterKnife.unbind(this);
		super.onDestroy();
	}

	@Override
	public void setTitle ( int id ) {
		setTitle( getString( id ) );
	}

	public AlphaActivity setTitle ( String title ) {
		TextView titleTextView = (TextView) findViewById( R.id.titleBarTitle );
		if ( null == titleTextView ) {
			BugsUtil.onFatalError( "AlphaActivity.setTitle()-> not found the title text view!" );
			return this;
		}
		titleTextView.setText( title );
		return this;
	}
	public AlphaActivity setOnBackListener(){
		View left = findViewById( R.id.titleBarOptionLeft );
		if ( null != left ) {
			left.setOnClickListener( this );
		}
		return this;
	}

	public AlphaActivity setOptionText ( String text ) {
		TextView titleTextView = (TextView) findViewById( R.id.titleBarOptionRight );
		if ( null == titleTextView ) {
			BugsUtil.onFatalError( "AlphaActivity.setTitle()-> not found the title text view!" );
			return this;
		}
		titleTextView.setText( text );
		return this;
	}


	protected String toast ( int stringId ) {
		return toast( getString( stringId ) );
	}

	protected String toast ( String msg ) {
		info( msg );
		return ToastUtil.toast( this, msg );
	}

	protected String toastLong ( int stringId ) {
		return toastLong( getString( stringId ) );
	}

	protected String toastLong ( String msg ) {
		info( msg );
		return ToastUtil.toastLong( this, msg );
	}
	protected String log ( Object obj ) {
		return ConsoleUtil.console( obj );
	}

	protected String log ( String msg ) {
		ConsoleUtil.console( msg );
		return msg;
	}

	protected String info ( String msg ) {
		TextView info = (TextView) findViewById( R.id.info );
		if ( null != info )
			info.setText( msg );
		return msg;
	}

	public boolean isExit () {
		return exit;
	}

	public AlphaActivity setExit ( boolean exit ) {
		this.exit = exit;
		return this;
	}

	@Override
	public boolean onKeyDown ( int keyCode, KeyEvent event ) {
		if ( !exit ) {
			if ( keyCode == KeyEvent.KEYCODE_BACK ) {
				// on pressed the back button -> action hide not exit
//			if ( drawerLayout.isDrawerOpen( menu ) ) {
//				drawerLayout.closeDrawer( menu );
//			}
//			else if ( !Setting.getSetting().isExitAppOnBack() ) {
				moveTaskToBack( false );
//			}
				return true;
			}
		}
		return super.onKeyDown( keyCode, event );
	}

	@Override
	public void onClick ( View v ) {
		switch ( v.getId() ) {
			case R.id.titleBarOptionLeft:
				finish();
				break;
		}

	}
}
