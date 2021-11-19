package com.bilgeadam.aliergul.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private DatabaseInformation info = new DatabaseInformation();;
	private Connection conn;
	
	static {
		
	}
	
	private DatabaseConnection() {
		
	}
	
	public static DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	public Connection getConn(String tag) {
		
		try {
			if (conn == null || conn.isClosed()) {
				try {
					Class.forName(info.getFor_name_data());
				} catch (ClassNotFoundException e) {
					System.out.println("HATA: ClassNotFoundException: " + e.getMessage());
				}
				
				this.conn = DriverManager.getConnection(info.getUrl() + info.getDatabase(), info.getUserName(),
						info.getPassword());
				// System.out.println("LOG: " + tag + ": " + conn.isClosed());
			}
		} catch (SQLException e) {
			System.out.println("HATA: getConn: " + info.getDatabase() + " " + e.getMessage());
		}
		return this.conn;
	}
	
	public Connection getConnPostgreSql(String tag) {
		
		try {
			Class.forName(info.getFor_name_data());
			
			this.conn = DriverManager.getConnection(info.getUrl(), info.getUserName(), info.getPassword());
			System.out.println("LOG: " + tag + ": " + conn.isClosed());
			
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("HATA: getConnPostgreSql: " + e.getMessage());
		}
		return this.conn;
	}
	
}
