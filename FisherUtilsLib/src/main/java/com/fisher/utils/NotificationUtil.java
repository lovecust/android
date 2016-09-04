package com.fisher.utils;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;




public class NotificationUtil {
	public static final int NOTIFICATION_DEFAULT_ID = 50920;


//	public static void sendNotification ( String title, String text, long showTime ) {
//		new NotificationThread( title, text, showTime ).start();
//	}

	public static void sendNotification ( String title, String text ) {
		sendNotification( title, text, R.mipmap.icon_launcher, null );
	}

	public static void sendNotification ( String title, String text, PendingIntent intent ) {
		sendNotification( title, text, R.mipmap.icon_launcher, intent );
	}

	public static void sendNotification ( String title, String text, int icon, PendingIntent intent ) {
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder( AppUtil.getContext() )
						.setSmallIcon( icon )
						.setContentTitle( title )
						.setContentText( text );
		if ( null != intent )
			mBuilder.setContentIntent( intent );
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = ( NotificationManager ) AppUtil.getContext().getSystemService( Context.NOTIFICATION_SERVICE );
		// Builds the notification and issues it.
		mNotifyMgr.notify( NOTIFICATION_DEFAULT_ID, mBuilder.build() );
	}

	public static void cancel ( ) {
		cancel( NOTIFICATION_DEFAULT_ID );
	}

	public static void cancel ( int id ) {
		NotificationManager mNotifyMgr = ( NotificationManager ) AppUtil.getContext().getSystemService( Context.NOTIFICATION_SERVICE );
		mNotifyMgr.cancel( id );
	}

	public static void cancelAll ( ) {
		NotificationManager mNotifyMgr = ( NotificationManager ) AppUtil.getContext().getSystemService( Context.NOTIFICATION_SERVICE );
		mNotifyMgr.cancelAll();
	}


	public static class NotificationThread extends Thread {
		private final String title;
		private final String text;
		private long time;

		public NotificationThread ( String title, String text, long time ) {
			this.title = title;
			this.text = text;
			this.time = time;
		}

		@Override
		public void run ( ) {
			try {
				for (; time > 0; time -= 1000 ) {
					sendNotification( title, text );
					Thread.sleep( 1000 );
				}
				cancel();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

}
