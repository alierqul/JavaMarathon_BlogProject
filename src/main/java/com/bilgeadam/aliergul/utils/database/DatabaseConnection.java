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
	
	public Connection getConn() {
		
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(info.getFOR_NAME_DATA());
				conn = DriverManager.getConnection(info.getURL(), info.getUSER_NAME(), info.getPASSWORD());
				
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("HATA: " + e.getMessage());
		}
		return conn;
	}
	
}
