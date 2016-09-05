package com.lovecust.modules.app.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovecust.app.R;

import java.util.List;

public class AdapterListBooks extends RecyclerView.Adapter< ViewHolderBooks > {

	private List< Book > books;
	private ViewHolderBooks.OnBookItemClick onBookItemClick;


	public AdapterListBooks init ( List< Book > books, ViewHolderBooks.OnBookItemClick onBookItemClick ) {
		this.books = books;
		this.onBookItemClick = onBookItemClick;
		return this;
	}

	@Override
	public ViewHolderBooks onCreateViewHolder ( ViewGroup parent, int viewType ) {
		View view = View.inflate( parent.getContext(), R.layout.item_book_list, null );
		return new ViewHolderBooks( view );
	}

	@Override
	public void onBindViewHolder ( ViewHolderBooks holder, int position ) {
		holder.init( books.get( position ), onBookItemClick );
	}

	@Override
	public int getItemCount () {
		return books.size();
	}

}
