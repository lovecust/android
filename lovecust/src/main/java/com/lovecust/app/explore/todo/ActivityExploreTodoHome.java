package com.lovecust.app.explore.todo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.lovecust.AlphaActivity;

import org.litepal.crud.DataSupport;

import butterknife.Bind;

/**
 * Created by Fisher on 5/9/2016 at 23:06
 */
public class ActivityExploreTodoHome extends AlphaActivity {

	@Bind( R.id.mCount )
	TextView tvAmount;
	@Bind( R.id.mTag )
	TextView tvTag;
	@Bind( R.id.mRecyclerView )
	RecyclerView rvTodoList;

	private String tag = DataTodo.TAG_DEBUG;
	private AdapterTodo adapter;

	@Override
	public int getLayout ( ) {
		return R.layout.activity_explore_todo_home;
	}

	@Override
	public void init ( ) {
		setOnBackListener().setTitle( R.string.title_explore_todo );

		tvTag.setText( "-" + tag + "-" );
		int total = DataTodo.amount( tag );
		tvAmount.setText( Math.min( total, AdapterTodo.limit ) + "/" + ( total ) );

		// use a linear layout manager
		LinearLayoutManager mLayoutManager = new LinearLayoutManager( this );
		rvTodoList.setLayoutManager( mLayoutManager );
		rvTodoList.setAdapter( adapter = new AdapterTodo( tag ).setContext( this ) );
	}
}
