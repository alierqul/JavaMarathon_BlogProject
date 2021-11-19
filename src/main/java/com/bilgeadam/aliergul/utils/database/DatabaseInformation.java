package com.bilgeadam.aliergul.utils.database;

public class DatabaseInformation {
	private String url;
	private String userName;
	private String password;
	private String for_name_data;
	private String database;
	
	public DatabaseInformation() {
		this.database = "aliergul_blog";
		this.url = "jdbc:postgresql://localhost:5432/";
		this.userName = "postgres";
		this.password = "root";
		this.for_name_data = "org.postgresql.Driver";
	}
	
	@Override
	public String toString() {
		return "DatabaseInformation [url=" + url + ", userName=" + userName + ", password=" + password
				+ ", for_name_data=" + for_name_data + ", database=" + database + "]";
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFor_name_data() {
		return for_name_data;
	}
	
	public void setFor_name_data(String for_name_data) {
		this.for_name_data = for_name_data;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
}
