package com.lovecust.modules.ecust.jwc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lovecust.app.R;
import com.lovecust.network.entities.EcustJwcNews;
import com.lovecust.app.BaseActivity;
import com.fisher.utils.NetUtil;
import com.lovecust.network.EcustNetworkManager;
import com.lovecust.utils.NetworkManager;

import butterknife.Bind;


public class ActivityEcustJWCHome extends BaseActivity {

	@Bind(R.id.mRecyclerView)
	RecyclerView mRecyclerView;

	private AdapterJwcNews mAdapter;
	private EcustJwcNews[] jwcNewses;
	private LinearLayoutManager mLayoutManager;
	private EcustNetworkManager mNetworkManager;

	@Override
	public int getLayout() {
		return R.layout.activity_ecust_jwc_home;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle(R.string.title_ecust_jwc);
		mNetworkManager = NetworkManager.getEcustNetworkManager();

//		jwcNewses = EcustJwcNews.getCachedNews();
		// TODO cache;

		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new AdapterJwcNews(this);
		mAdapter.setLists(jwcNewses);
		mRecyclerView.setAdapter(mAdapter);
		flushData();
	}

	private void flushData() {
		if (NetUtil.isOfflineAndToast()) {return;}
		mNetworkManager.getJwcNewses()
				.subscribe(newses -> {
					jwcNewses = newses;
					mAdapter.setLists(jwcNewses);
				}, err -> {
					err.printStackTrace();
					toast("server error");
				});
	}

}
