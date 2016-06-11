package com.lovecust.app.ecust.jwc;


import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.lovecust.app.R;
import com.lovecust.app.entity.UtilComment;
import com.lovecust.app.lovecust.AlphaRecyclerViewAdapter;
import com.lovecust.app.lovecust.AlphaRecyclerViewHolder;
import com.fisher.utils.TimeUtil;
import com.squareup.picasso.Picasso;

public class AdapterJwcComments extends AlphaRecyclerViewAdapter< UtilComment > {
	private Context context;

	public AdapterJwcComments ( Context context ) {
		this.context = context;
	}

	@Override
	public int layoutId ( int viewType ) {
		return R.layout.item_ecust_jwc_comment;
	}

	@Override
	public void onBindViewHolder ( AlphaRecyclerViewHolder holder, int position ) {
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
