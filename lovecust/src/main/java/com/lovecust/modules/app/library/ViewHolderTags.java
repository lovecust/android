package com.lovecust.modules.app.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;

public class ViewHolderTags extends RecyclerView.ViewHolder {

	private final View view;
	private final TextView tagTextView;

	public ViewHolderTags ( View itemView ) {
		super( itemView );
		this.view = itemView;
		this.tagTextView = (TextView)view.findViewById( R.id.tag );
	}

	public void init ( final Tag tag, final OnTagItemClick onTagItemClick ) {
		tagTextView.setText( tag.getTag() );
		view.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick ( View v ) {
				tagTextView.setTextColor( 0xffffff00 );
				onTagItemClick.onClick( tag.getId());
			}
		} );
	}

	public interface OnTagItemClick {
		void onClick ( long tagId );
	}


}
