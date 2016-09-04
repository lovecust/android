package com.fisher.utils;


/*
* Friday, December 25, 2015
* Fri, Dec. 25th, 2015
* 星期五 12月25号 2015
* 2015-12-25
* 2015/12/25
* 18:03:46
* 18:03:46pm
* 18-03-46
*
*
* */



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String FORMAT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_MM_dd_HH_mm = "MM-dd HH:mm";
	public static final String FORMAT_HH_mm = "HH:mm";
	public static final String FORMAT_yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_READABLE = "EEE, MMM dd, yyyy HH:mm:ss";

	public static final String FORMAT_ACCURACY = FORMAT_yyyy_MM_dd_HH_mm_ss_SSS;
	public static final String FORMAT_SHORT = FORMAT_MM_dd_HH_mm;

	public static final String FORMAT_DEFAULT = FORMAT_SHORT;

	public static final String TIME_06_00_00 = "06:00:00";
	public static final String TIME_21_00_00 = "21:00:00";
	private static Calendar calendar = Calendar.getInstance();

	public static long TIME_IN_MILLIS_24h = 24 * 3600_000;
	public static long TIME_IN_MILLIS_18h = 18 * 3600_000;
	public static long TIME_IN_MILLIS_12h = 12 * 3600_000;
	public static long TIME_IN_MILLIS_6h = 6 * 3600_000;
	public static long TIME_IN_MILLIS_2h = 2 * 3600_000;
	public static long TIME_IN_MILLIS_1h = 3600_000;
	public static long TIME_IN_MILLIS_30mins = 1800_000;


	public static String fnFormatTime ( ) {
		return fnFormatTime( System.currentTimeMillis(), FORMAT_DEFAULT );
	}

	public static String fnFormatTime ( long time ) {
		return fnFormatTime( time, FORMAT_DEFAULT );
	}

	public static String fnFormatTime ( String format ) {
		return fnFormatTime( System.currentTimeMillis(), format );
	}

	public static String fnFormatTime ( long time, String format ) {
		SimpleDateFormat dateFormat = new SimpleDateFormat( format );
		return dateFormat.format( new Date( time ) );
	}

	public static String fnFormatIntervalTime ( long time ) {
		boolean flag = true;
		if ( time < 0 ) {
			time = -time;
			flag = false;
		}
		long day = time / ( 24 * 60 * 60 * 1000 );
		long hour = time / ( 60 * 60 * 1000 ) % 24;
		long minute = time / ( 60 * 1000 ) % 60;
		long second = time / 1000 % 60;
		return ( flag ? "" : "-" ) + ( day > 0 ? day + "d " : "" ) + ( hour > 0 ? hour + "h " : "" ) + ( minute > 0 ? minute + "mins " : "" ) + second + "s";
	}

	public static String getReadableTime ( long time ) {
		String strTime;
		if ( isYesterday( time ) ) {
			strTime = AppUtil.getString( R.string.yesterday ) + TimeUtil.fnFormatTime( time, TimeUtil.FORMAT_HH_mm );
		} else if ( TimeUtil.isToday( time ) ) {
			strTime = AppUtil.getString( R.string.today ) + TimeUtil.fnFormatTime( time, TimeUtil.FORMAT_HH_mm );
		} else {
			strTime = TimeUtil.fnFormatTime( time, TimeUtil.FORMAT_MM_dd_HH_mm );
		}
		return strTime;
	}

	public static int getHour ( ) {
		return getHour( System.currentTimeMillis() );
	}

	public static int getHour ( long time ) {
		calendar.setTimeInMillis( time );
		return calendar.get( Calendar.HOUR_OF_DAY );
	}

	public static int getMinute ( ) {
		return getMinute( System.currentTimeMillis() );
	}

	public static int getMinute ( long time ) {
		calendar.setTimeInMillis( time );
		return calendar.get( Calendar.MINUTE );
	}

	public static int getWeekOfYear ( ) {
		return getWeekOfYear( System.currentTimeMillis() );
	}

	public static int getWeekOfYear ( long time ) {
		calendar.setTimeInMillis( time );
		int week = calendar.get( Calendar.WEEK_OF_YEAR );
		int offset = calendar.get( Calendar.DAY_OF_WEEK );
		return offset == 1 ? week - 1 : week;
	}

	public static long getTime ( String time ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set( Calendar.MILLISECOND, 0 );
		if ( time.length() == 8 || time.charAt( 2 ) == ':' && time.charAt( 5 ) == ':' ) {
			calendar.set( Calendar.HOUR_OF_DAY, Integer.valueOf( time.substring( 0, 2 ) ) );
			calendar.set( Calendar.MINUTE, Integer.valueOf( time.substring( 3, 5 ) ) );
			calendar.set( Calendar.SECOND, Integer.valueOf( time.substring( 6, 8 ) ) );
		}
		return calendar.getTimeInMillis();
	}

	public static boolean isToday ( long time ) {
		if ( time > getTime( "00:00:00" ) && time < getTime( "23:59:59" ) ) {
			return true;
		}
		return false;
	}

	public static boolean isYesterday ( long time ) {
		long today = getTime( "00:00:00" );
		if ( time > today - TIME_IN_MILLIS_24h && time < today ) {
			return true;
		}
		return false;
	}
}

