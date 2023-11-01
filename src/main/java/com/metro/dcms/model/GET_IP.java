package com.metro.dcms.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class GET_IP implements Serializable {
	
	private String ip;
	
	private String terminal;
	
	public GET_IP(String ip,String terminal) {
		super();
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.terminal = terminal;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	
	
	
	
}
