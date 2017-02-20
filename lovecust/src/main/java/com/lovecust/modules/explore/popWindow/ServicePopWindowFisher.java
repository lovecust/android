package com.lovecust.modules.explore.popWindow;

import android.content.Intent;
import android.os.IBinder;

import com.fisher.utils.app.BaseService;


public class ServicePopWindowFisher extends BaseService {


	private FloatView floatView;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flush();
		return START_STICKY;
	}


	@Override
	public void onDestroy() {
		floatView.fnDestroyFloatView();
		super.onDestroy();
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	private void init() {
		floatView = new FloatView(this)
				.init();
	}

	private void flush() {
		floatView.flushImage();
	}

}
