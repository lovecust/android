package com.lovecust.modules.ecust.jwc;


import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.lovecust.app.R;
import com.lovecust.network.entities.UtilComment;
import com.fisher.utils.app.BaseRecyclerViewAdapter;
import com.fisher.utils.app.BaseRecyclerViewHolder;
import com.fisher.utils.TimeUtil;
import com.squareup.picasso.Picasso;

public class AdapterJwcComments extends BaseRecyclerViewAdapter< UtilComment > {
	private Context context;

	public AdapterJwcComments ( Context context ) {
		this.context = context;
	}

	@Override
	public int layoutId ( int viewType ) {
		return R.layout.item_ecust_jwc_comment;
	}

	@Override
	public void onBindViewHolder ( BaseRecyclerViewHolder holder, int position ) {
		UtilComment comment = getItem( position );

		holder.setText( R.id.nick, comment.getNick() );
		if ( null != comment.getHtml() && !"".equals( comment.getHtml() ) ) {
			holder.setText( R.id.text, Html.fromHtml( comment.getHtml() ) );
		} else {
			holder.setText( R.id.text, comment.getValue() );
		}
		holder.setText( R.id.date, TimeUtil.getReadableTime( comment.getCtime() ) );
		if ( null != comment.getAvatar() && !"".equals( comment.getAvatar() ) ) {
			//		TODO set avatars
			 	Picasso.with( context ).load( comment.getAvatar() ).into( ( ImageView ) holder.get( R.id.avatar ) );
		}
	}
}
