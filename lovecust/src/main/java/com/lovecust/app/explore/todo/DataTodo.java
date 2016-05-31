package com.lovecust.app.explore.todo;

import com.google.gson.Gson;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class DataTodo extends DataSupport {
	public static final String TAG_TODO = "todo";
	public static final String TAG_DEBUG = "debug";
	public static final String TAG_DEFAULT = TAG_TODO;

	public static ArrayList< DataTodo > getTodos ( String tag ) {
		return ( ArrayList< DataTodo > ) DataSupport.where( "tag=? AND state=?", tag, String.valueOf( STATE_OKAY ) ).limit( AdapterTodo.limit ).order( "ctime desc" ).find( DataTodo.class );
	}

	public static int amount ( String tag ) {
		return DataSupport.where( "tag=? AND state=?", tag, String.valueOf( STATE_OKAY ) ).count( DataTodo.class );
	}

	// item that is still available
	private static final int STATE_OKAY = -10;
	// item that is removed without done
	private static final int STATE_REMOVED = -11;
	// item that is done
	private static final int STATE_DONE = -12;

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

	public String changeText(String text){
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
