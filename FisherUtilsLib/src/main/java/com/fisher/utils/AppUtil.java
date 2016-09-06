package com.fisher.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;

import java.util.Locale;

public class AppUtil {

	/**
	 * Hold the application context object.
	 */
	private static Context sContext;
	/**
	 * debug flag
	 */
	private static boolean sDebug = false;

	public static void init( Context context){
		sContext = context;
	}

	/**
	 * get application context
	 *
	 * @return context
	 */
	public static Context getContext ( ) {
		return sContext;
	}

	public static boolean isDebug ( ) {
		return sDebug;
	}

	public static void setDebug ( boolean sDebug ) {
		AppUtil.sDebug = sDebug;
	}

	/**
	 * No initialization.
	 */
	private AppUtil() {
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void fnSendBroadcast(Context context, String action) {
		context.sendBroadcast(new Intent(action));
	}

	public static void fnStartService(Context context, Intent intent) {
		context.startService(intent);
	}

	public static void fnStartService(Context context, String pkg, String cls) {
		try {
			Intent i = new Intent();
			i.setComponent(new ComponentName(pkg, cls));
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fnStartActivity(Context context, String action) {
		try {
			Intent i = new Intent(action);
			i.addCategory(Intent.CATEGORY_DEFAULT);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fnStartActivity(Context context, String pkg, String cls) {
		try {
			Intent i = new Intent();
			i.setComponent(new ComponentName(pkg, cls));
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static boolean fnCheckService(Context context, Class ServiceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (ServiceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static void fnLockScreen() {
		DevicePolicyManager manager = (DevicePolicyManager) AppUtil.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
		manager.lockNow();
	}

	public static void fnUnlockScreen() {
		// TODO UNLOCK SCREEN
//		Intent intent = new Intent(DevicePolicyManager
//				.ACTION_ADD_DEVICE_ADMIN);
//		intent.putExtra( DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName );
//		intent.putExtra( DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//				"Additional text explaining why this needs to be added." );
//		WindowManager windowManager = (WindowManager) AppUtil.getContext().getSystemService( Context.WINDOW_SERVICE );
//		windowManager.;
//				window.addFlags(wm.LayoutParams.FLAG_DISMISS_KEYGUARD);
//		Activity activity=null;
//		Activity
//		activity.startActivityForResult( intent, RESULT_ENABLE );
	}

	public static void fnGetRunningApps() {
		ActivityManager manager = (ActivityManager) AppUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		ConsoleUtil.console("----------------------------running-apps------------------------------");
		for (ActivityManager.RunningAppProcessInfo info : manager.getRunningAppProcesses()) {
			ConsoleUtil.console(new Gson().toJson(info));
		}
	}

	public static void fnKillRunningApps() {
		ActivityManager manager = (ActivityManager) AppUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		ConsoleUtil.console("----------------------------installed-apps------------------------------");
		for (ActivityManager.RunningAppProcessInfo info : manager.getRunningAppProcesses()) {
			ConsoleUtil.console(new Gson().toJson(info));
		}
	}

	public static void fnListInstalledAppInfo() {
		PackageManager manager = AppUtil.getContext().getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		ConsoleUtil.console("----------------------------installed-apps------------------------------");
		for (ResolveInfo info : manager.queryIntentActivities(mainIntent, 0)) {
			ConsoleUtil.console(new Gson().toJson(info));
		}

	}

	/**
	 * Information you can retrieve about a running process.
	 */
//	public static class RunningAppProcessInfo implements Parcelable {
//		/**
//		 * The name of the process that this object is associated with
//		 */
//		public String processName;
//
//		/**
//		 * The pid of this process; 0 if none
//		 */
//		public int pid;
//
//		/**
//		 * The user id of this process.
//		 */
//		public int uid;
//
//		/**
//		 * All packages that have been loaded into the process.
//		 */
//		public String pkgList[];
//
//		/**
//		 * Constant for {@link #flags}: this is an app that is unable to
//		 * correctly save its state when going to the background,
//		 * so it can not be killed while in the background.
//		 * @hide
//		 */
//		public static final int FLAG_CANT_SAVE_STATE = 1<<0;
//
//		/**
//		 * Constant for {@link #flags}: this process is associated with a
//		 * persistent system app.
//		 * @hide
//		 */
//		public static final int FLAG_PERSISTENT = 1<<1;
//
//		/**
//		 * Constant for {@link #flags}: this process is associated with a
//		 * persistent system app.
//		 * @hide
//		 */
//		public static final int FLAG_HAS_ACTIVITIES = 1<<2;
//
//		/**
//		 * Flags of information.  May be any of
//		 * {@link #FLAG_CANT_SAVE_STATE}.
//		 * @hide
//		 */
//		public int flags;
//
//		/**
//		 * Last memory trim level reported to the process: corresponds to
//		 * the values supplied to {@link android.content.ComponentCallbacks2#onTrimMemory(int)
//		 * ComponentCallbacks2.onTrimMemory(int)}.
//		 */
//		public int lastTrimLevel;
//
//		/**
//		 * Constant for {@link #importance}: This process is running the
//		 * foreground UI; that is, it is the thing currently at the top of the screen
//		 * that the user is interacting with.
//		 */
//		public static final int IMPORTANCE_FOREGROUND = 100;
//
//		/**
//		 * Constant for {@link #importance}: This process is running a foreground
//		 * service, for example to perform music playback even while the user is
//		 * not immediately in the app.  This generally indicates that the process
//		 * is doing something the user actively cares about.
//		 */
//		public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
//
//		/**
//		 * Constant for {@link #importance}: This process is running the foreground
//		 * UI, but the device is asleep so it is not visible to the user.  This means
//		 * the user is not really aware of the process, because they can not see or
//		 * interact with it, but it is quite important because it what they expect to
//		 * return to once unlocking the device.
//		 */
//		public static final int IMPORTANCE_TOP_SLEEPING = 150;
//
//		/**
//		 * Constant for {@link #importance}: This process is running something
//		 * that is actively visible to the user, though not in the immediate
//		 * foreground.  This may be running a window that is behind the current
//		 * foreground (so paused and with its state saved, not interacting with
//		 * the user, but visible to them to some degree); it may also be running
//		 * other services under the system's control that it inconsiders important.
//		 */
//		public static final int IMPORTANCE_VISIBLE = 200;
//
//		/**
//		 * Constant for {@link #importance}: This process is not something the user
//		 * is directly aware of, but is otherwise perceptable to them to some degree.
//		 */
//		public static final int IMPORTANCE_PERCEPTIBLE = 130;
//
//		/**
//		 * Constant for {@link #importance}: This process is running an
//		 * application that can not save its state, and thus can't be killed
//		 * while in the background.
//		 * @hide
//		 */
//		public static final int IMPORTANCE_CANT_SAVE_STATE = 170;
//
//		/**
//		 * Constant for {@link #importance}: This process is contains services
//		 * that should remain running.  These are background services apps have
//		 * started, not something the user is aware of, so they may be killed by
//		 * the system relatively freely (though it is generally desired that they
//		 * stay running as long as they want to).
//		 */
//		public static final int IMPORTANCE_SERVICE = 300;
//
//		/**
//		 * Constant for {@link #importance}: This process process contains
//		 * background code that is expendable.
//		 */
//		public static final int IMPORTANCE_BACKGROUND = 400;
//
//		/**
//		 * Constant for {@link #importance}: This process is empty of any
//		 * actively running code.
//		 */
//		public static final int IMPORTANCE_EMPTY = 500;
//
//		/**
//		 * Constant for {@link #importance}: This process does not exist.
//		 */
//		public static final int IMPORTANCE_GONE = 1000;
//
//		/** @hide */
//		public static int procStateToImportance(int procState) {
//			if (procState == PROCESS_STATE_NONEXISTENT) {
//				return IMPORTANCE_GONE;
//			} else if (procState >= PROCESS_STATE_HOME) {
//				return IMPORTANCE_BACKGROUND;
//			} else if (procState >= PROCESS_STATE_SERVICE) {
//				return IMPORTANCE_SERVICE;
//			} else if (procState > PROCESS_STATE_HEAVY_WEIGHT) {
//				return IMPORTANCE_CANT_SAVE_STATE;
//			} else if (procState >= PROCESS_STATE_IMPORTANT_BACKGROUND) {
//				return IMPORTANCE_PERCEPTIBLE;
//			} else if (procState >= PROCESS_STATE_IMPORTANT_FOREGROUND) {
//				return IMPORTANCE_VISIBLE;
//			} else if (procState >= PROCESS_STATE_TOP_SLEEPING) {
//				return IMPORTANCE_TOP_SLEEPING;
//			} else if (procState >= PROCESS_STATE_FOREGROUND_SERVICE) {
//				return IMPORTANCE_FOREGROUND_SERVICE;
//			} else {
//				return IMPORTANCE_FOREGROUND;
//			}
//		}
//
//		/**
//		 * The relative importance level that the system places on this
//		 * process.  May be one of {@link #IMPORTANCE_FOREGROUND},
//		 * {@link #IMPORTANCE_VISIBLE}, {@link #IMPORTANCE_SERVICE},
//		 * {@link #IMPORTANCE_BACKGROUND}, or {@link #IMPORTANCE_EMPTY}.  These
//		 * constants are numbered so that "more important" values are always
//		 * smaller than "less important" values.
//		 */
//		public int importance;
//
//		/**
//		 * An additional ordering within a particular {@link #importance}
//		 * category, providing finer-grained information about the relative
//		 * utility of processes within a category.  This number means nothing
//		 * except that a smaller values are more recently used (and thus
//		 * more important).  Currently an LRU value is only maintained for
//		 * the {@link #IMPORTANCE_BACKGROUND} category, though others may
//		 * be maintained in the future.
//		 */
//		public int lru;
//
//		/**
//		 * Constant for {@link #importanceReasonCode}: nothing special has
//		 * been specified for the reason for this level.
//		 */
//		public static final int REASON_UNKNOWN = 0;
//
//		/**
//		 * Constant for {@link #importanceReasonCode}: one of the application's
//		 * content providers is being used by another process.  The pid of
//		 * the client process is in {@link #importanceReasonPid} and the
//		 * target provider in this process is in
//		 * {@link #importanceReasonComponent}.
//		 */
//		public static final int REASON_PROVIDER_IN_USE = 1;
//
//		/**
//		 * Constant for {@link #importanceReasonCode}: one of the application's
//		 * content providers is being used by another process.  The pid of
//		 * the client process is in {@link #importanceReasonPid} and the
//		 * target provider in this process is in
//		 * {@link #importanceReasonComponent}.
//		 */
//		public static final int REASON_SERVICE_IN_USE = 2;
//
//		/**
//		 * The reason for {@link #importance}, if any.
//		 */
//		public int importanceReasonCode;
//
//		/**
//		 * For the specified values of {@link #importanceReasonCode}, this
//		 * is the process ID of the other process that is a client of this
//		 * process.  This will be 0 if no other process is using this one.
//		 */
//		public int importanceReasonPid;
//
//		/**
//		 * For the specified values of {@link #importanceReasonCode}, this
//		 * is the name of the component that is being used in this process.
//		 */
//		public ComponentName importanceReasonComponent;
//
//		/**
//		 * When {@link #importanceReasonPid} is non-0, this is the importance
//		 * of the other pid. @hide
//		 */
//		public int importanceReasonImportance;
//
//		/**
//		 * Current process state, as per PROCESS_STATE_* constants.
//		 * @hide
//		 */
//		public int processState;
//
//		public RunningAppProcessInfo() {
//			importance = IMPORTANCE_FOREGROUND;
//			importanceReasonCode = REASON_UNKNOWN;
//			processState = PROCESS_STATE_IMPORTANT_FOREGROUND;
//		}
//
//		public RunningAppProcessInfo(String pProcessName, int pPid, String pArr[]) {
//			processName = pProcessName;
//			pid = pPid;
//			pkgList = pArr;
//		}
//
//		public int describeContents() {
//			return 0;
//		}
//
//		public void writeToParcel(Parcel dest, int flags) {
//			dest.writeString(processName);
//			dest.writeInt(pid);
//			dest.writeInt(uid);
//			dest.writeStringArray(pkgList);
//			dest.writeInt(this.flags);
//			dest.writeInt(lastTrimLevel);
//			dest.writeInt(importance);
//			dest.writeInt(lru);
//			dest.writeInt(importanceReasonCode);
//			dest.writeInt(importanceReasonPid);
//			ComponentName.writeToParcel(importanceReasonComponent, dest);
//			dest.writeInt(importanceReasonImportance);
//			dest.writeInt(processState);
//		}
//
//		public void readFromParcel(Parcel source) {
//			processName = source.readString();
//			pid = source.readInt();
//			uid = source.readInt();
//			pkgList = source.readStringArray();
//			flags = source.readInt();
//			lastTrimLevel = source.readInt();
//			importance = source.readInt();
//			lru = source.readInt();
//			importanceReasonCode = source.readInt();
//			importanceReasonPid = source.readInt();
//			importanceReasonComponent = ComponentName.readFromParcel(source);
//			importanceReasonImportance = source.readInt();
//			processState = source.readInt();
//		}
//
//		public static final Creator<RunningAppProcessInfo> CREATOR =
//				new Creator<RunningAppProcessInfo>() {
//					public RunningAppProcessInfo createFromParcel(Parcel source) {
//						return new RunningAppProcessInfo(source);
//					}
//					public RunningAppProcessInfo[] newArray(int size) {
//						return new RunningAppProcessInfo[size];
//					}
//				};
//
//		private RunningAppProcessInfo(Parcel source) {
//			readFromParcel(source);
//		}
//	}
//	public static void fnAddShortcut(String title) {
//		//Adding shortcut for MainActivity
//		//on Home screen
//		Intent shortcutIntent = new Intent(AppUtil.getContext(), AppUtil.class);
////		shortcutIntent.setAction( Intent.ACTION_MAIN );
//		Intent addIntent = new Intent();
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(AppUtil.getContext(), R.mipmap.icon_launcher));
//		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//		AppUtil.getContext().sendBroadcast(addIntent);
//	}


	// set app language
	public static void changeLanguageMode(Locale locale) {
		Context context = AppUtil.getContext();
		Configuration configuration = context.getResources().getConfiguration();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			configuration.setLocale(locale);
		}
		context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
	}

	public static void getPhoneId() {
		// need permission android.permission.READ_PHONE_STATE
		TelephonyManager mTelephonyMgr = (TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		String imei = mTelephonyMgr.getDeviceId();
		ConsoleUtil.console("imsi: " + imsi + "; imei: " + imei + ";");
	}

	public static int getColor(int colorResourceId) {
		return AppUtil.getContext().getResources().getColor(colorResourceId);
	}

	public static String getString(@StringRes int id) {
		return AppUtil.getContext().getString(id);
	}

	public static String getAndroidInfo() {
		String info = "Android - " + Build.MANUFACTURER + " - " + Build.MODEL + " - " + Build.VERSION.SDK_INT + " - " + Build.VERSION.RELEASE;
//		ClassUtil.getFields( Build.class );
//		ClassUtil.getFields( Build.VERSION.class );
		return info;
	}


	public static String[] getStringArray(int id) {
		return AppUtil.getContext().getResources().getStringArray(id);
	}

	/*
	* start activities with animation
	* */
	public static void startActivityFromCenter(Activity activity, Class activityClass) {
		activity.startActivity(new Intent(activity, activityClass));
		activity.overridePendingTransition(R.anim.activity_zoom_in_from_center, R.anim.activity_zoom_out_to_center );
	}

	public static void startActivityFromRight(Activity activity, Class activityClass) {
		activity.startActivity(new Intent(activity, activityClass));
		activity.overridePendingTransition(R.anim.activity_push_in_from_right, R.anim.activity_push_out_to_left );
	}




}
