package com.lovecust.app.ecust.jwc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lovecust.app.R;
import com.lovecust.app.api.ApiManager;
import com.lovecust.app.entity.EcustJwcNews;
import com.lovecust.app.lovecust.AlphaActivity;
import com.fisher.utils.NetUtil;

import butterknife.Bind;


public class ActivityEcustJWCHome extends AlphaActivity {

	@Bind( R.id.mRecyclerView )
	RecyclerView mRecyclerView;

	private AdapterJwcNews mAdapter;
	private EcustJwcNews[] jwcNewses;
	private LinearLayoutManager mLayoutManager;
	private ApiManager api;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_ecust_jwc_home;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_ecust_jwc );
		api = ApiManager.getInstance();

		jwcNewses = EcustJwcNews.getCachedNews();

		mLayoutManager = new LinearLayoutManager( this );
		mRecyclerView.setLayoutManager( mLayoutManager );
		mAdapter = new AdapterJwcNews( this );
		mAdapter.setLists( jwcNewses );
		mRecyclerView.setAdapter( mAdapter );
		flush();
	}

	private void flush ( ) {
		if ( NetUtil.isOfflineAndToast() )
			return;

		api.ecustJwcList()
				.subscribe( newses -> {
					jwcNewses = newses;
					mAdapter.setLists( jwcNewses );
				}, err -> {
					err.printStackTrace();
					toast( "server error" );
				} );
	}

}
