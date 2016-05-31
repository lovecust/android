package com.lovecust.app.ecust.jwc;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.api.ApiManager;
import com.lovecust.app.entity.AppProfile;
import com.lovecust.app.entity.UtilComment;
import com.lovecust.app.lovecust.AlphaFragment;
import com.lovecust.app.utils.NetUtil;

import java.util.ArrayList;

import butterknife.Bind;

public class FragmentJwcComments extends AlphaFragment {

	private EditText input;
	private TextView submit;

	@Bind( R.id.comments )
	RecyclerView commentsHolder;

	private LinearLayoutManager mLayoutManager;
	private ArrayList< UtilComment > jwcCommentList = new ArrayList<>();
	private AdapterJwcComments adapter;
	private String md5;
	private ApiManager api = ApiManager.getInstance();
	;

	@Override
	public int getLayoutID ( ) {
		return R.layout.module_ecust_jwc_comments;
	}

	@Override
	public void init ( ) {
		mLayoutManager = new LinearLayoutManager( getActivity() );
		commentsHolder.setLayoutManager( mLayoutManager );
		adapter = new AdapterJwcComments( getActivity() );
		commentsHolder.setAdapter( adapter );
	}

	public void postComment ( String text ) {
		final UtilComment post = UtilComment.getInstance( md5, text ).setNick( AppProfile.getInstance().getNick() );
		if ( NetUtil.isOfflineAndToast() )
			return;
		api.ecustJwcCommentCreate( post )
				.subscribe( res -> {
					post.setCtime( res.getCtime() );
					jwcCommentList.add( 0, post );
					adapter.setLists( jwcCommentList );
				}, err -> {
					toast( R.string.toast_server_error );
				} );
	}

	public void init ( String md5 ) {
		this.md5 = md5;
		if ( NetUtil.isOfflineAndToast() )
			return;
		api.ecustJwcCommentList( md5 )
				.subscribe( list -> {
					jwcCommentList = convertToList( list );
					adapter.setLists( jwcCommentList );
				}, err->{
					toast( R.string.toast_server_error );
				});
	}


	private ArrayList< UtilComment > convertToList ( UtilComment[] commentsArray ) {
		jwcCommentList = new ArrayList<>();
		for ( UtilComment jwcComment : commentsArray ) {
			jwcCommentList.add( jwcComment );
		}
		return jwcCommentList;
	}

}
