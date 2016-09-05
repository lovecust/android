package com.lovecust.modules.explore.todo;

import com.google.gson.Gson;
import com.fisher.utils.FileUtil;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataTodo extends DataSupport {
	public static final String TAG_TODO = "todo";
	public static final String TAG_DEBUG = "debug";
	public static final String TAG_DEFAULT = TAG_TODO;

	public static ArrayList< DataTodo > getTodos ( String tag ) {
		return getTodos( tag, STATE_OKAY );
	}

	public static ArrayList< DataTodo > getTodos ( String tag, int state ) {
		return ( ArrayList< DataTodo > ) DataSupport.where( "tag=? AND state=?", tag, String.valueOf( state ) ).limit( AdapterTodo.limit ).order( "ltime desc" ).find( DataTodo.class );
	}

	public static int amount ( String tag ) {
		return DataSupport.where( "tag=?", tag ).count( DataTodo.class );
	}

	public static int amount ( String tag, int state ) {
		return DataSupport.where( "tag=? AND state=?", tag, String.valueOf( state ) ).count( DataTodo.class );
	}

	public static void fnExport ( File file ) throws IOException {
		StringBuilder sb = new StringBuilder();
		ArrayList< DataTodo > todos = ( ArrayList< DataTodo > ) DataSupport.order( "ltime desc" ).find( DataTodo.class );
		for ( int i = 0; i < todos.size(); i++ ) {
			sb.append( todos.get( i ).toString() );
			sb.append( "\n" );
		}
		FileUtil.writeFile( file, sb.toString() );
	}

	public static int fnDelete ( String tag, int state ) {
		return DataSupport.deleteAll( DataTodo.class, "tag=? AND state=?", tag, String.valueOf( state ) );
	}

	public static int fnDelete ( String tag ) {
		return DataSupport.deleteAll( DataTodo.class, "tag=?", tag );
	}

	// item that is still available
	public static final int STATE_OKAY = -10;
	// item that is removed without done
	public static final int STATE_REMOVED = -11;
	// item that is done
	public static final int STATE_DONE = -12;

	private long ctime;
	private long ltime;
	private String tag;
	private String text;
	private int state;

	public DataTodo ( String text ) {
		this( TAG_DEFAULT, text );
	}

	public DataTodo ( String tag, String text ) {
		this.tag = tag;
		this.text = text;
		this.ctime = System.currentTimeMillis();
		this.ltime = ctime;
		this.state = STATE_OKAY;
	}

	// make the change to the db
	public void touch ( int state ) {
		this.state = state;
		ltime = System.currentTimeMillis();
		saveThrows();
	}

	public void fnRemove ( ) {
		touch( STATE_REMOVED );
	}

	public void fnDone ( ) {
		touch( STATE_DONE );
	}

	public String changeText ( String text ) {
		String origin = getText();
		setText( text );
		touch( STATE_OKAY );
		return origin;
	}


	public String getText ( ) {
		return text;
	}

	public void setText ( String text ) {
		this.text = text;
	}

	public long getCtime ( ) {
		return ctime;
	}

	public void setCtime ( long ctime ) {
		this.ctime = ctime;
	}

	public String getTag ( ) {
		return tag;
	}

	public void setTag ( String tag ) {
		this.tag = tag;
	}

	public int getState ( ) {
		return state;
	}

	public void setState ( int state ) {
		this.state = state;
	}


	@Override
	public String toString ( ) {
		return new Gson().toJson( this );
	}

	public long getLtime ( ) {
		return ltime;
	}

	public void setLtime ( long ltime ) {
		this.ltime = ltime;
	}
}
