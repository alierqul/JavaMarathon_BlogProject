package com.bilgeadam.aliergul.utils.database;

public class DatabaseInformation {
	private String URL;
	private String USER_NAME;
	private String PASSWORD;
	private String FOR_NAME_DATA;
	
	public DatabaseInformation() {
		
		URL = "jdbc:postgresql://localhost:5432/MarathonBlog";
		USER_NAME = "boost";
		PASSWORD = "boost";
		FOR_NAME_DATA = "org.postgresql.Driver";
	}
	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	public String getUSER_NAME() {
		return USER_NAME;
	}
	
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	
	public String getPASSWORD() {
		return PASSWORD;
	}
	
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	
	public String getFOR_NAME_DATA() {
		return FOR_NAME_DATA;
	}
	
	public void setFOR_NAME_DATA(String fOR_NAME_DATA) {
		FOR_NAME_DATA = fOR_NAME_DATA;
	}
	
}
