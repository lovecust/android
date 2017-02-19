package com.lovecust.modules.ecust.jwc;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.fisher.utils.NetUtil;
import com.lovecust.app.BaseFragment;
import com.lovecust.app.R;
import com.lovecust.network.EcustNetworkManager;
import com.lovecust.network.entities.UtilComment;
import com.lovecust.utils.AppProfileHelper;
import com.lovecust.utils.NetworkManager;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;

public class FragmentJwcComments extends BaseFragment {

	private EditText input;
	private TextView submit;

	@Bind(R.id.comments)
	RecyclerView commentsHolder;

	private LinearLayoutManager mLayoutManager;
	private ArrayList<UtilComment> jwcCommentList = new ArrayList<>();
	private AdapterJwcComments adapter;
	private String md5;
	private EcustNetworkManager mNetworkManager = NetworkManager.getEcustNetworkManager();

	@Override
	public int getLayoutID() {
		return R.layout.module_ecust_jwc_comments;
	}

	@Override
	public void init() {
		mLayoutManager = new LinearLayoutManager(getActivity());
		commentsHolder.setLayoutManager(mLayoutManager);
		adapter = new AdapterJwcComments(getActivity());
		commentsHolder.setAdapter(adapter);
		this.md5 = getArguments().getString(ActivityEcustJwcDetail.KEY_NEWS_ID);
		flushData();
	}

	public void postComment(String text) {
		if (NetUtil.isOfflineAndToast()) {return;}
		mNetworkManager.postJwcNewsComment(md5, text, AppProfileHelper.getInstance().getNick(), AppProfileHelper.getInstance().getAvatar())
				.subscribe(res -> {
					flushData();
				}, err -> {
					err.printStackTrace();
					toast(R.string.toast_server_error);
				});
	}

	public void flushData() {
		if (NetUtil.isOfflineAndToast()) {return;}
		mNetworkManager.getJwcNewsComments(md5)
				.subscribe(list -> {
					jwcCommentList = convertToList(list);
					adapter.setLists(jwcCommentList);
				}, err -> {
					toast(R.string.toast_server_error);
				});
	}


	private ArrayList<UtilComment> convertToList(UtilComment[] commentsArray) {
		jwcCommentList = new ArrayList<>();
		Collections.addAll(jwcCommentList, commentsArray);
		return jwcCommentList;
	}

}
