package com.bilgeadam.aliergul.main.chatapp;

import java.util.Date;
import java.util.UUID;

public class PortInfo {
	private static final int DEFAULT_PORT = 7777;
	private static final String DEFAULT_IP_ADRESS = "localhost";
	private String id;
	private int portNumber;
	private String ipAdress;
	private Date logsDate;
	
	public PortInfo() {
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		this.id = uuid;
		this.portNumber = DEFAULT_PORT;
		this.ipAdress = DEFAULT_IP_ADRESS;
		this.logsDate = new Date(System.currentTimeMillis());
	}
	
	public PortInfo(int port, String idAdress) {
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		this.id = uuid;
		this.portNumber = port;
		this.ipAdress = idAdress;
		this.logsDate = new Date(System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		return "ClientServerTuto [id=" + id + ", port=" + portNumber + ", idAdress=" + ipAdress + ", logsDate="
				+ logsDate + "]";
	}
	
	public String getId() {
		return id;
	}
	
	public int getPort() {
		return portNumber;
	}
	
	public void setPort(int port) {
		this.portNumber = port;
	}
	
	public String getIdAdress() {
		return ipAdress;
	}
	
	public void setIdAdress(String idAdress) {
		this.ipAdress = idAdress;
	}
	
	public Date getLogsDate() {
		return logsDate;
	}
	
	public void setLogsDate(Date logsDate) {
		this.logsDate = logsDate;
	}
	
}
