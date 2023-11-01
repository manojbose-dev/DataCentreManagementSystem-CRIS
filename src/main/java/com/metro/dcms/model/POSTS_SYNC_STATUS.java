package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class POSTS_SYNC_STATUS implements Serializable {

	private String pubName;
	
	private String lastDown;
	
	private String lastUp;
	
	
	public POSTS_SYNC_STATUS(String pubName,String lastDown,String lastUp) {
		super();
		// TODO Auto-generated constructor stub
		this.pubName = pubName;
		this.lastDown = lastDown;
		this.lastUp = lastUp;

	}


	public String getPubName() {
		return pubName;
	}


	public void setPubName(String pubName) {
		this.pubName = pubName;
	}


	public String getLastDown() {
		return lastDown;
	}


	public void setLastDown(String lastDown) {
		this.lastDown = lastDown;
	}


	public String getLastUp() {
		return lastUp;
	}


	public void setLastUp(String lastUp) {
		this.lastUp = lastUp;
	}

	
	
	
}
