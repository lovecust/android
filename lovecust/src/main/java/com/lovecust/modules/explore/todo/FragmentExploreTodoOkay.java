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
public class FragmentExploreTodoOkay extends BaseFragment {
	@Bind( R.id.mCount )
	TextView tvAmount;
	@Bind( R.id.mTag )
	TextView tvTag;
	@Bind( R.id.mRecyclerView )
	RecyclerView rvTodoList;

	private String tag = DataTodo.TAG_TODO;
	private int state = DataTodo.STATE_OKAY;
	private AdapterTodo adapter;

	@Override
	public int getLayoutID ( ) {
		return R.layout.fragment_explore_todo_done;
	}

	@Override
	public void init ( ) {
		tvTag.setText( "-" + tag + "-" );
		int total = DataTodo.amount( tag );
		if ( total == 0 ) {
			DataTodo todo = new DataTodo( tag, "Click the 'More' Button -> Show-Float-Icon and have your todo-list started *_*" );
			todo.saveThrows();
			todo = new DataTodo( tag, "点击右上角->显示悬浮图标 尝试下吧哈哈哈 *_*" );
			todo.saveThrows();
			todo = new DataTodo( tag, "Check the permission if you found nothing happened after your operation -.-" );
			todo.saveThrows();
			todo = new DataTodo( tag, "(没发现悬浮图标？)小米等手机需要打开显示悬浮窗权限哦-.-" );
			todo.saveThrows();
			total = 4;
			/* flushData the data done */
			todo = new DataTodo( tag, "Items done will be here:-)" );
			todo.saveThrows();
			todo.fnDone();
			todo = new DataTodo( tag, "完成的内容会在这里哦:-)" );
			todo.saveThrows();
			todo.fnDone();
			/* flushData the data removed */
			todo = new DataTodo( tag, "Removed items will be here:-)" );
			todo.saveThrows();
			todo.fnRemove();
			todo = new DataTodo( tag, "移除的内容会在这里哦:-)" );
			todo.saveThrows();
			todo.fnRemove();
		}
		total = DataTodo.amount( tag, state );
		tvAmount.setText( Math.min( total, AdapterTodo.limit ) + "/" + ( total ) );

		// use a linear layout manager
		LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
		rvTodoList.setLayoutManager( mLayoutManager );
		rvTodoList.setAdapter( adapter = new AdapterTodo( tag, state ).setContext( getContext() ) );
	}
}
