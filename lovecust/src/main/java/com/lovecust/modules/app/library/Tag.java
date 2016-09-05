package com.lovecust.modules.app.library;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Tag extends DataSupport {

	private long id;
	@Column( unique = true )
	private String tag;
	@Column( defaultValue = "" )
	private String note;
	private long ctime;

	public Tag () {
		this.ctime = System.currentTimeMillis();
	}


	public long getId () {
		return id;
	}

	public void setId ( long id ) {
		this.id = id;
	}

	public String getTag () {
		return tag;
	}

	public void setTag ( String tag ) {
		this.tag = tag;
	}

	public String getNote () {
		return note;
	}

	public void setNote ( String note ) {
		this.note = note;
	}

	public long getCtime () {
		return ctime;
	}

	public void setCtime ( long ctime ) {
		this.ctime = ctime;
	}

	@Override
	public String toString () {
		return new Gson().toJson( this );
	}
}
