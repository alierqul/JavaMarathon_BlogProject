package com.bilgeadam.aliergul.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private DatabaseInformation info;
	private Connection conn;
	
	static {
		
	}
	
	private DatabaseConnection() {
		info = new DatabaseInformation();
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
					Class.forName(info.getFOR_NAME_DATA());
				} catch (ClassNotFoundException e) {
					System.out.println("HATA: ClassNotFoundException: " + e.getMessage());
				}
				
				this.conn = DriverManager.getConnection(info.getURL(), info.getUSER_NAME(), info.getPASSWORD());
				// System.out.println("LOG: " + tag + ": " + conn.isClosed());
			}
		} catch (SQLException e) {
			System.out.println("HATA: getConn: " + e.getMessage());
		}
		return this.conn;
	}
	
}
