package com.lovecust.app.library;

import org.litepal.crud.DataSupport;


public class Images extends DataSupport {
	private String large;
	private String medium;
	private String small;

	public String getLarge () {
		return large;
	}

	public void setLarge ( String large ) {
		this.large = large;
	}

	public String getMedium () {
		return medium;
	}

	public void setMedium ( String medium ) {
		this.medium = medium;
	}

	public String getSmall () {
		return small;
	}

	public void setSmall ( String small ) {
		this.small = small;
	}
}