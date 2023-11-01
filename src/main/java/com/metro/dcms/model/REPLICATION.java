package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class REPLICATION implements Serializable {
	
	private String rep_name;
	
	private String rep_details;
	
	private String status_time;

	
	
	public REPLICATION(String rep_name, String rep_details, String status_time) {
		super();
		this.rep_name = rep_name;
		this.rep_details = rep_details;
		this.status_time = status_time;
	}

	public String getRep_name() {
		return rep_name;
	}

	public void setRep_name(String rep_name) {
		this.rep_name = rep_name;
	}

	public String getRep_details() {
		return rep_details;
	}

	public void setRep_details(String rep_details) {
		this.rep_details = rep_details;
	}

	public String getStatus_time() {
		return status_time;
	}

	public void setStatus_time(String status_time) {
		this.status_time = status_time;
	}
	
	
	
	
	
}
