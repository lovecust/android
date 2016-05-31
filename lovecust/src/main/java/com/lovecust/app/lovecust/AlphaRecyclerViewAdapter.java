package com.lovecust.app.lovecust;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Fisher on 5/9/2016 at 4:29
 */
public abstract class AlphaRecyclerViewAdapter< T > extends RecyclerView.Adapter< AlphaRecyclerViewHolder > {

	public ArrayList< T > lists = new ArrayList<>();
	private boolean withHeader = false;

	public AlphaRecyclerViewAdapter ( ) {
	}

	public AlphaRecyclerViewAdapter ( boolean withHeader ) {
		this.withHeader = withHeader;
	}

	public AlphaRecyclerViewAdapter ( ArrayList< T > lists, boolean withHeader ) {
		this.lists = lists;
		this.withHeader = withHeader;
	}

	public AlphaRecyclerViewAdapter ( ArrayList< T > lists ) {
		this.lists = lists;
	}

	public ArrayList< T > getLists ( ) {
		return lists;
	}

	public void setLists ( ArrayList< T > lists ) {
		if ( null == lists )
			return;
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void setLists ( T[] lists ) {
		if ( null == lists )
			return;
		ArrayList< T > arr = new ArrayList< T >( lists.length );
		for ( int i = 0; i < lists.length; i++ ) {
			arr.add( lists[ i ] );
		}
		this.lists = arr;
		notifyDataSetChanged();
	}

	public T getItem ( int position ) {
		return lists.get( position );
	}

	public abstract int layoutId ( int viewType );


	@Override
	public AlphaRecyclerViewHolder onCreateViewHolder ( ViewGroup parent, int viewType ) {
		return AlphaRecyclerViewHolder.getItemView( parent, layoutId( viewType ) );
	}

	@Override
	public int getItemCount ( ) {
		int add = withHeader ? 1 : 0;
		return lists == null ? add : lists.size() + add;
	}


}