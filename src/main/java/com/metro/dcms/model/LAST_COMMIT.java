package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class LAST_COMMIT implements Serializable {
	
	private String origin;
	
	private String origin_time;
	
	private String dest_commit_time;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOrigin_time() {
		return origin_time;
	}

	public void setOrigin_time(String origin_time) {
		this.origin_time = origin_time;
	}

	public String getDest_commit_time() {
		return dest_commit_time;
	}

	public void setDest_commit_time(String dest_commit_time) {
		this.dest_commit_time = dest_commit_time;
	}

	public LAST_COMMIT(String origin, String origin_time, String dest_commit_time) {
		super();
		this.origin = origin;
		this.origin_time = origin_time;
		this.dest_commit_time = dest_commit_time;
	}

		
}
