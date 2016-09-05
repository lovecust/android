package com.lovecust.modules.app.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.app.R;

import java.util.Arrays;

public class ViewHolderBooks extends RecyclerView.ViewHolder {

	private final View view;

	public ViewHolderBooks ( View itemView ) {
		super( itemView );
		this.view = itemView;
	}

	public void init ( final Book book, final OnBookItemClick onBookItemClick ) {
		TextView title = (TextView) view.findViewById( R.id.title );
		TextView author = (TextView) view.findViewById( R.id.author );
		TextView date = (TextView) view.findViewById( R.id.date );
		TextView publisher = (TextView) view.findViewById( R.id.publisher );
		ImageView image = (ImageView) view.findViewById( R.id.image );

		title.setText( book.getTitle() );
		author.setText( Arrays.toString( book.getAuthor() ) );
		date.setText( book.getPubdate() );
		publisher.setText( book.getPublisher() );
//		image.setImageBitmap(  );
		view.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick ( View v ) {
				onBookItemClick.onClick( book.getId() );
			}
		} );
	}


	interface OnBookItemClick {
		public void onClick ( long id );
	}


}
