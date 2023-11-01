package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class NETWORK_STATUS implements Serializable {

	private String terminal;
	
	private String location;
	
	private String device;
	
	private String ip;
	
	private String status;
	
	private String update_time;

	public String getTerminal() {
		return terminal;
	}
    
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public NETWORK_STATUS(String terminal, String location, String device, String ip, String status, String update_time) {
		super();
		this.terminal = terminal;
		this.location = location;
		this.device = device;
		this.ip = ip;
		this.status = status;
		this.update_time = update_time;
	}
	
	
	

	
	
	
}
