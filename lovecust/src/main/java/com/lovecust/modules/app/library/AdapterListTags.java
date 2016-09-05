package com.lovecust.modules.app.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovecust.app.R;

import java.util.List;

public class AdapterListTags extends RecyclerView.Adapter< ViewHolderTags > {

	private List< Tag > tags;
	private ViewHolderTags.OnTagItemClick onTagItemClick;


	public AdapterListTags init ( List< Tag > tags ) {
		this.tags = tags;
		return this;
	}

	public AdapterListTags setListener ( ViewHolderTags.OnTagItemClick onTagItemClick ) {
		if ( null != onTagItemClick )
			this.onTagItemClick = onTagItemClick;
		return this;
	}

	@Override
	public ViewHolderTags onCreateViewHolder ( ViewGroup parent, int viewType ) {
		View view = View.inflate( parent.getContext(), R.layout.item_tag_list, null );
		return new ViewHolderTags( view );
	}

	@Override
	public void onBindViewHolder ( ViewHolderTags holder, int position ) {
		holder.init( tags.get( position ), onTagItemClick );
	}


	@Override
	public int getItemCount () {
		return tags.size();
	}

}
