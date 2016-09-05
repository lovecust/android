package com.lovecust.modules.explore.todo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.BaseFragment;

import butterknife.Bind;

/**
 * Created on 6/5/2016 at 16:33
 * By Fisher
 */
public class FragmentExploreTodoDone extends BaseFragment {
	@Bind( R.id.mCount )
	TextView tvAmount;
	@Bind( R.id.mTag )
	TextView tvTag;
	@Bind( R.id.mRecyclerView )
	RecyclerView rvTodoList;

	private String tag = DataTodo.TAG_TODO;
	private int state = DataTodo.STATE_DONE;
	private AdapterTodo adapter;

	@Override
	public int getLayoutID ( ) {
		return R.layout.fragment_explore_todo_done;
	}

	@Override
	public void init ( ) {
		tvTag.setText( "-" + tag + "-" );
		int total = DataTodo.amount( tag, state );
		tvAmount.setText( Math.min( total, AdapterTodo.limit ) + "/" + ( total ) );

		// use a linear layout manager
		LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
		rvTodoList.setLayoutManager( mLayoutManager );
		rvTodoList.setAdapter( adapter = new AdapterTodo( tag, state ).setContext( getContext() ) );
	}


}
