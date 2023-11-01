package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class POSTS_REP_STATUS implements Serializable {
	
	private String rType;
	
	private String rDate;
	
	private String rLoc;
	
	public POSTS_REP_STATUS(String rType,String rDate,String rLoc) {
		super();
		// TODO Auto-generated constructor stub
		this.rType = rType;
		this.rDate = rDate;
		this.rLoc = rLoc;
	}

	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	public String getrDate() {
		return rDate;
	}

	public void setrDate(String rDate) {
		this.rDate = rDate;
	}

	public String getrLoc() {
		return rLoc;
	}

	public void setrLoc(String rLoc) {
		this.rLoc = rLoc;
	}
	
	
	
}
