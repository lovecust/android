package com.lovecust.app.explore.todo;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.lovecust.AlphaRecyclerViewAdapter;
import com.lovecust.app.lovecust.AlphaRecyclerViewHolder;
import com.lovecust.app.surface.DialogEdittext;
import com.fisher.utils.AppUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Fisher on 5/9/2016 at 22:56
 */
public class AdapterTodo extends AlphaRecyclerViewAdapter< DataTodo > {
	public static int limit = 20;

	private final ArrayList< DataTodo > todos;
	private String tag;
	// if context is null, then show dialog in the system mode
	private Context context;

	private View lastViewTime;
	private View lastViewOption;

	public AdapterTodo ( String tag ) {
		this( tag, DataTodo.STATE_OKAY );
	}

	public AdapterTodo ( String tag, int state ) {
		this.tag = tag;
		todos = DataTodo.getTodos( tag, state );
		setLists( todos );
	}

	public AdapterTodo setContext ( Context context ) {
		this.context = context;
		return this;
	}


	@Override
	public int layoutId ( int viewType ) {
		return R.layout.item_listview_todo;
	}

	@Override
	public void onBindViewHolder ( final AlphaRecyclerViewHolder holder, final int position ) {
		final DataTodo todo = todos.get( position );

		holder.setText( R.id.tv_text, todo.getText() );

		long time = todo.getCtime();
		holder.setText( R.id.tv_time, TimeUtil.getReadableTime( time ) );

		long interval = System.currentTimeMillis() - time;
		int color;
		if ( interval > TimeUtil.TIME_IN_MILLIS_24h ) {
			color = 0xFF990000;
		} else if ( interval > TimeUtil.TIME_IN_MILLIS_18h ) {
			color = 0xFF660000;
		} else if ( interval > TimeUtil.TIME_IN_MILLIS_12h ) {
			color = 0xFF7700AA;
		} else if ( interval > TimeUtil.TIME_IN_MILLIS_6h ) {
			color = 0xFF009900;
		} else if ( interval > TimeUtil.TIME_IN_MILLIS_2h ) {
			color = 0xFF999900;
		} else if ( interval > TimeUtil.TIME_IN_MILLIS_1h ) {
			color = 0xFF663300;
		} else {
			color = 0xFF222200;
		}
		( ( TextView ) holder.get( R.id.tv_text ) ).setTextColor( color );

		holder.get( R.id.layout_parent ).setOnClickListener( v -> {
			if ( holder.get( R.id.layout_options ).getVisibility() == View.GONE ) {
				if ( lastViewOption != null && lastViewTime != null ) {
					lastViewOption.setVisibility( View.GONE );
					lastViewTime.setVisibility( View.VISIBLE );
				}
				( lastViewOption = holder.get( R.id.layout_options ) ).setVisibility( View.VISIBLE );
				( lastViewTime = holder.get( R.id.tv_time ) ).setVisibility( View.GONE );
			} else {
				( holder.get( R.id.layout_options ) ).setVisibility( View.GONE );
				( holder.get( R.id.tv_time ) ).setVisibility( View.VISIBLE );
			}
		} );

		holder.get( R.id.iv_remove ).setOnClickListener( v -> {
			ConsoleUtil.console( "will delete: " + ( todo ) );
			todo.fnRemove();
			todos.remove( todo );
			notifyDataSetChanged();
		} );
		holder.get( R.id.iv_done ).setOnClickListener( v -> {
			ConsoleUtil.console( "done: " + todo );
			todo.fnDone();
			todos.remove( todo );
			notifyDataSetChanged();
		} );
		holder.get( R.id.iv_edit ).setOnClickListener( v -> {
			todo.getText();
			DialogEdittext.newDialog( context )
					.init( AppUtil.getString( R.string.dialog_set_content ) ).setEdittext( todo.getText() )
					.setOnActionResultListener( new DialogEdittext.OnActionResultListener() {
						@Override
						public void onActionCommit ( String input ) {
							todo.changeText( input );
							notifyDataSetChanged();
						}

						@Override
						public void onActionCancel ( ) {

						}
					} ).show();
		} );
	}
}
