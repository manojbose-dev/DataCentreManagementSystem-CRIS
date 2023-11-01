package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class DATABASES implements Serializable {
	
	private String dbName;
	
	private String dbSize;
	
	private String dbReserved;

	private String dbData;	
	
	private String dbIndex;
	
	private String dbUnused;

	
	public DATABASES(String dbName, String dbSize, String dbReserved, String dbData, String dbIndex, String dbUnused) {
		super();
		this.dbName = dbName;
		this.dbSize = dbSize;
		this.dbReserved = dbReserved;
		this.dbData = dbData;
		this.dbIndex = dbIndex;
		this.dbUnused = dbUnused;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public String getDbReserved() {
		return dbReserved;
	}

	public void setDbReserved(String dbReserved) {
		this.dbReserved = dbReserved;
	}

	public String getDbData() {
		return dbData;
	}

	public void setDbData(String dbData) {
		this.dbData = dbData;
	}

	public String getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(String dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getDbUnused() {
		return dbUnused;
	}

	public void setDbUnused(String dbUnused) {
		this.dbUnused = dbUnused;
	}
	
	
	
}
