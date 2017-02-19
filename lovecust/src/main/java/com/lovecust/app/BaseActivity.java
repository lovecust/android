package com.lovecust.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.fisher.utils.BugsUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener {

	private boolean exit = true;
	protected boolean isSwipeExit = true;

	public abstract int getLayout();

	public abstract void init();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		ButterKnife.bind(this);
		if (isSwipeExit) {
			getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		} else {
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
	public void setTitle(int id) {
		setTitle(getString(id));
	}

	public BaseActivity setTitle(String title) {
		TextView titleTextView = (TextView) findViewById(R.id.titleBarTitle);
		if (null == titleTextView) {
			BugsUtil.onFatalError("BaseActivity.setTitle()-> not found the title text view!");
			return this;
		}
		titleTextView.setText(title);
		return this;
	}

	public BaseActivity setOnBackListener() {
//		View left = findViewById( R.id.titleBarOptionLeft );
//		if ( null != left ) {
//			left.setOnClickListener( this );
//		}
		return this;
	}

	public BaseActivity setOptionText(String text) {
		TextView titleTextView = (TextView) findViewById(R.id.titleBarOptionRight);
		if (null == titleTextView) {
			BugsUtil.onFatalError("BaseActivity.setTitle()-> not found the title text view!");
			return this;
		}
		titleTextView.setText(text);
		return this;
	}


	protected String toast(int stringId) {
		return toast(getString(stringId));
	}

	protected String toast(String msg) {
		return ToastUtil.toastLong(this, msg);
	}


	protected String log(Object obj) {
		return ConsoleUtil.console(obj);
	}

	protected String log(String msg) {
		ConsoleUtil.console(msg);
		return msg;
	}

	public boolean isExit() {
		return exit;
	}

	public BaseActivity setExit(boolean exit) {
		this.exit = exit;
		return this;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!exit) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				// on pressed the back button -> action hide not exit
//			if ( drawerLayout.isDrawerOpen( menu ) ) {
//				drawerLayout.closeDrawer( menu );
//			}
//			else if ( !Setting.getSetting().isExitAppOnBack() ) {
				moveTaskToBack(false);
//			}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.titleBarOptionLeft:
				finish();
				break;
		}

	}

	@Nullable
	@OnClick(R.id.titleBarOptionLeft)
	void btnFinish() {
		finish();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.activity_push_in_from_left, R.anim.activity_push_out_to_right);
	}
}
