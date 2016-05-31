package com.lovecust.app.ecust.morning;


import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.utils.EdittextUtil;
import com.lovecust.app.utils.InstantStoreUtil;
import com.lovecust.app.utils.NetUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Fisher on 5/7/2016 at 22:48
 */
public class ActivityEcustMorningExercise extends AlphaActivity {
	private final String KEY_CID = getClass().getName() + ".cid";
	private final String KEY_SCORE = getClass().getName() + ".score";

	@Bind( R.id.btnCommit )
	TextView btnCommit;
	@Bind( R.id.et_cid )
	EditText etCid;
	@Bind( R.id.et_pwd )
	EditText etPwd;
	@Bind( R.id.tv_score )
	TextView tvScore;

	private SportScore sportScore;
	private String score = "";
	private Handler handler;
	private InstantStoreUtil instantStore;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_ecust_morning_exercise;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_ecust_morning_exercise );
		instantStore = InstantStoreUtil.getInstance();

		EdittextUtil.setDigitsAndAlphabets( etCid );
		EdittextUtil.setText( etCid, instantStore.getString( KEY_CID ) );
		tvScore.setText( instantStore.getString( KEY_SCORE ) );

		handler = new Handler( getMainLooper() ) {
			@Override
			public void handleMessage ( Message msg ) {
				switch ( msg.what ) {
					case 200:
						tvScore.setText( score );
						break;
					default:
						toast( "Error happened! check and try later!" );
						break;
				}
				super.handleMessage( msg );
			}
		};
	}


	@OnClick( R.id.btnCommit )
	void btnCommit ( ) {
		final String cid = etCid.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		if ( !NetUtil.isConnected ) {
			toast( R.string.toast_global_device_offline_check_internet );
			return;
		}
		if ( "".equals( cid ) ) {
			toast( R.string.toast_check_cid_and_pwd );
			return;
		}
		if ( "".equals( pwd ) ) {
			pwd = cid;
		}
		// set the campus id to cache
		instantStore.putString( KEY_CID, cid );
		final String finalPwd = pwd;

		tvScore.setText( "--" );
		new Thread() {
			@Override
			public void run ( ) {
				try {
					SportScore exercise = new SportScore( cid, finalPwd );
					boolean t = exercise.init();
					if ( !t ) {
						throw new Exception( "Error at parsing html -.-" );
					}
					score = exercise.getMorningRun();
					instantStore.putString( KEY_SCORE, score );
					handler.sendEmptyMessage( 200 );
				} catch ( Exception e ) {
					e.printStackTrace();
					handler.sendEmptyMessage( -1 );
				}
			}
		}.start();
	}


	@OnClick( R.id.tv_score )
	void btnScore ( ) {
		toast( "You get " + score + " and keep moving *_* " );
	}

}
