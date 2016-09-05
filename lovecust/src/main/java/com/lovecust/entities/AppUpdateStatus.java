package com.lovecust.entities;

public class AppUpdateStatus {
	private String name;
	private String ename;
	private String app;
	private String api;
	private String url;
	private String md5;
	private String sha1;
	private String size;
	private String date;
	private String about;
	private String eabout;
	private String desc;
	private String edesc;
	private String detail;
	private String edetail;

	@Override
	public String toString ( ) {
		String info = "";
		info += "Name: " + name + "\n";
		info += "App: " + app + "\n";
		info += "Url: " + url + "\n";
		info += "Hash md5: " + md5 + "\n";
		info += "Hash sha1: " + sha1 + "\n";
		info += "Size: " + size + "\n";
		info += "Release Date: " + date + "\n\n";
		info += "About:\n"
				+ about + "\n"
				+ eabout + "\n\n";
		info += "What's New:\n"
				+ desc + "\n"
				+ edesc + "\n\n";
		info += "Details:\n"
				+ detail + "\n"
				+ edetail + "\n\n";
		return info;
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getEname ( ) {
		return ename;
	}

	public void setEname ( String ename ) {
		this.ename = ename;
	}

	public String getApp ( ) {
		return app;
	}

	public void setApp ( String app ) {
		this.app = app;
	}

	public String getApi ( ) {
		return api;
	}

	public void setApi ( String api ) {
		this.api = api;
	}

	public String getUrl ( ) {
		return url;
	}

	public void setUrl ( String url ) {
		this.url = url;
	}

	public String getMd5 ( ) {
		return md5;
	}

	public void setMd5 ( String md5 ) {
		this.md5 = md5;
	}

	public String getSha1 ( ) {
		return sha1;
	}

	public void setSha1 ( String sha1 ) {
		this.sha1 = sha1;
	}

	public String getSize ( ) {
		return size;
	}

	public void setSize ( String size ) {
		this.size = size;
	}

	public String getDate ( ) {
		return date;
	}

	public void setDate ( String date ) {
		this.date = date;
	}

	public String getAbout ( ) {
		return about;
	}

	public void setAbout ( String about ) {
		this.about = about;
	}

	public String getEabout ( ) {
		return eabout;
	}

	public void setEabout ( String eabout ) {
		this.eabout = eabout;
	}

	public String getDesc ( ) {
		return desc;
	}

	public void setDesc ( String desc ) {
		this.desc = desc;
	}

	public String getEdesc ( ) {
		return edesc;
	}

	public void setEdesc ( String edesc ) {
		this.edesc = edesc;
	}

	public String getDetail ( ) {
		return detail;
	}

	public void setDetail ( String detail ) {
		this.detail = detail;
	}

	public String getEdetail ( ) {
		return edetail;
	}

	public void setEdetail ( String edetail ) {
		this.edetail = edetail;
	}
}
