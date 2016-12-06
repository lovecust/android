package com.lovecust.modules.explore.popWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lovecust.app.R;
import com.lovecust.modules.explore.todo.DataTodo;
import com.lovecust.modules.explore.todo.DialogTodo;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.DensityUtils;

public class FloatView {

	private final ServicePopWindowFisher context;
	private final int mScreenWidth;
	private final int mScreenHeight;

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams wmParams;
	private LinearLayout mFloatLayout;
	private ImageButton mFloatView;
	private Position position;
	private long touchTime;
	private Handler handler;
	private FloatViewImage floatViewImageSetting;

	public FloatView(ServicePopWindowFisher servicePopWindowFisher) {
		this.context = servicePopWindowFisher;
		mScreenWidth = DensityUtils.getScreenWidth(context);
		mScreenHeight = DensityUtils.getScreenHeight(context);
	}

	public FloatView init() {
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		floatViewImageSetting = FloatViewImage.getInstance();
		position = new Position();

		wmParams = new WindowManager.LayoutParams();
		wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		mFloatLayout = (LinearLayout) View.inflate(context, R.layout.float_pop_window, null);
		mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		mFloatView = (ImageButton) mFloatLayout.findViewById(R.id.image);
		flushImage();

		mWindowManager.addView(mFloatLayout, wmParams);


		mFloatView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						double x = event.getRawX() - mFloatView.getMeasuredWidth() / 2;
						double y = event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 50;
						position.init(x, y);
						return false;
					case MotionEvent.ACTION_MOVE:
						x = event.getRawX() - mFloatView.getMeasuredWidth() / 2;
						y = event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 50;
						position.setXY(x, y);
						wmParams.x = (int) x;
						wmParams.y = (int) y;
//						log( "time->" + System.currentTimeMillis() );
//						log( "event-> " + event );
//						log("position-> " + position);
						mWindowManager.updateViewLayout(mFloatLayout, wmParams);
						return false;
					case MotionEvent.ACTION_UP:
						touchTime = System.currentTimeMillis();
						new Thread() {
							@Override
							public void run() {
								position.run();
							}
						}.start();
						return false;
				}
				return false;
			}
		});

		handler = new Handler(context.getMainLooper()) {
			@Override
			public void handleMessage(Message inputMessage) {
				mWindowManager.updateViewLayout(mFloatLayout, wmParams);
			}
		};

		mFloatView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if ( System.currentTimeMillis() - touchTime < 100 )
//					return;
				DialogTodo.newDialog()
						.init(DataTodo.TAG_TODO).show();
			}
		});
		log("The pop window is initialized!");
		return this;
	}

	/* resize image or change image resource */
	public void flushImage() {
		if (null == mFloatView)
			return;
		int width = floatViewImageSetting.getWidth();
		int height = floatViewImageSetting.getHeight();
		int resource = floatViewImageSetting.getResource();
		mFloatView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		mFloatView.setImageResource(resource);
	}

	public void fnDestroyFloatView() {
		if (mWindowManager != null && mFloatLayout != null) {
			mWindowManager.removeView(mFloatLayout);
			handler = null;
		}
		log("The pop window is destroyed!");
	}


	class Position {
		private boolean debug = true;
		private int top = floatViewImageSetting.getTop();
		private int flag = 0;

		private double force = floatViewImageSetting.getForce();
		private double speedX;
		private double speedY;
		private long time;
		private double x;
		private double y;
		private long interval;

		public void init(double x, double y) {
			this.x = x;
			this.y = y;
			speedX = speedY = 0;
			time = System.currentTimeMillis();
		}

		public void setXY(double x, double y) {
			long time = System.currentTimeMillis();
			// pause for a while to reduce the frequency of the call of this method;
//			if (flag != top) {
//				flag++;
//				return;
//			}
			speedX = (speedX + (x - this.x) / (time - this.time)) / 2;
			speedY = (speedY + (y - this.y) / (time - this.time)) / 2;
//			if ( debug )
			log("speedX: " + speedX + " - speedY: " + speedY);

			this.x = x;
			this.y = y;
			this.time = time;
//			flag = 0;
		}

		public void run() {
			interval = System.currentTimeMillis() - this.time;
			double temp = Math.pow((speedX * speedX + speedY * speedY), 0.5);
			if (temp == 0) {
				log("the speed got is 0.0!");
				return;
			}
			double forceX = speedX / temp * force;
			double forceY = speedY / temp * force;
			log("force: " + forceX + "-" + forceY + "; temp: " + temp);
			forceX = Math.abs(forceX);
			forceY = Math.abs(forceY);
			double forceXX = forceX * 0.3;
			double forceYY = forceY * 0.3;

			FloatViewImage image = FloatViewImage.getInstance();
			int deviceBorderLeft = image.getWidth() / 2;
			int deviceBorderRight = mScreenWidth - image.getWidth() / 2;
			int deviceBorderTop = image.getHeight() / 2;
			int deviceBorderBottom = mScreenHeight - image.getHeight() / 2;
			while (true) {
				x += speedX * (interval);
				y += speedY * (interval);
				wmParams.x = (int) (x);
				wmParams.y = (int) y;
//				if ( debug )
//				log( "position-> " + this + "; " + forcex + "-" + forcey );
				if (null == handler) {
					log("handler is null and exit now!");
					return;
				}
				handler.sendEmptyMessage(200);

				if (speedX > 0)
					speedX -= forceX;
				else
					speedX += forceX;

				if (speedY > 0)
					speedY -= forceY;
				else
					speedY += forceY;

				if (y > deviceBorderBottom || y < deviceBorderTop)
					speedY = -speedY;
				if (x > deviceBorderRight || x < deviceBorderLeft)
					speedX = -speedX;
				if (Math.abs(speedX) < 0.1 && Math.abs(speedY) < 0.1) {
					break;
				} else if (Math.abs(speedX) < 1.2 && Math.abs(speedY) < 1.2) {
					forceX = forceXX;
					forceY = forceYY;
					log("forceX: " + forceX + " - forceY: " + forceY);
//					if (forceX)
				}
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public String toString() {
			try {
				return new Gson().toJson(this);
			} catch (Exception e) {
				e.printStackTrace();
				return super.toString();
			}
		}
	}


	public String log(String msg) {
		return ConsoleUtil.console(msg);
	}
}
