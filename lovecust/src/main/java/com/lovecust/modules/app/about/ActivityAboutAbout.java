package com.lovecust.modules.app.about;

import android.graphics.Typeface;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.BaseActivity;
import com.lovecust.app.Setting;
import com.fisher.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;


public class ActivityAboutAbout extends BaseActivity {


	@Bind(R.id.text)
	TextView text;

	@Override
	public int getLayout() {
		return R.layout.activity_about_about;
	}

	@Override
	public void init () {
		setTitle( getString( R.string.title_about_about ) ).setOnBackListener();

		Typeface face = Typeface.createFromAsset( getAssets(), Setting.FILE_FONT_CONSOLA );
		text.setTypeface( face );
		text.setText( "" );
		try {
			InputStream in = getResources().getAssets().open( Setting.FILE_TEXT_ABOUT_ABOUT );
			String contact = FileUtil.getString( in ).replaceAll( "\t", "    " );
			String[] lines = contact.split( "\n" );
			for ( int i=0; i<lines.length; i++ ){
				if ( lines[i].trim().startsWith( "△" )||lines[i].trim().startsWith( "#" )||lines[i].trim().startsWith( "*" ) ){
					continue;
				}
				text.append( lines[i] );
				text.append( "\n" );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
