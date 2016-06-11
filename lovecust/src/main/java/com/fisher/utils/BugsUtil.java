package com.fisher.utils;

import com.lovecust.app.lovecust.AppContext;

public class BugsUtil {
	public static final String FILE_NAME = "bugs.log";
	private static PermanentUtil util;


	public static String onFatalError(String tag, String bug) {
		return onFatalError(TimeUtil.fnFormatTime() + "\n" + tag + "\n" + bug + "\n\n");
	}

	public static String onFatalError(String bug) {
		ConsoleUtil.error(bug);
		if (!AppContext.mDebug)
			return bug;
		if (null == util)
			util = PermanentUtil.get(FILE_NAME);
		util.write(TimeUtil.fnFormatTime() + " -> " + bug + "\r\n");
		return bug;
	}

	public static void release() {
		util.close();
		util = null;
	}
}
