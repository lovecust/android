package com.lovecust.modules.ecust.calendar;

import android.net.Uri;
import android.widget.ImageView;

import com.lovecust.app.R;
import com.lovecust.app.AlphaActivity;
import com.lovecust.app.Setting;
import com.fisher.utils.FileUtil;
import com.fisher.utils.HTTPUtil;

import java.io.File;

import butterknife.Bind;


public class ActivityEcustCalendarHome extends AlphaActivity {

	@Bind(R.id.image)
	ImageView image;

	@Override
	public int getLayout() {
		return R.layout.activity_ecust_calendar_home;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle( R.string.title_ecust_calendar );

		image = (ImageView) findViewById( R.id.image );
		flush();
	}


	private void flush () {
		String name = "2016A.png";
		File file = FileUtil.getExternalFile( Setting.FILE_EXTERNAL_DIR_APP_IMAGE + Setting.separator + name );
		if ( file.exists() && file.length() > 10 ) {
			image.setImageURI( Uri.fromFile( file ) );
			image.setScaleType( ImageView.ScaleType.FIT_CENTER );
			return;
		}

		String url = "https://raw.githubusercontent.com/fisher1995/lovecust-assets/master/images/ecust-calendar-2016a.png";
		HTTPUtil.download( url, file, new HTTPUtil.OnDownloadCompleteListener() {
			@Override
			public void onCompleted ( File file ) {
				image.setImageURI( Uri.fromFile( file ) );
				image.setScaleType( ImageView.ScaleType.FIT_CENTER );
			}

			@Override
			public void onFailed () {
				toast( "Please wait. .." );
			}
		} );
	}

}
