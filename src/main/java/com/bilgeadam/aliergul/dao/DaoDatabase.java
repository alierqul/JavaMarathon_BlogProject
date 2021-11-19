package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bilgeadam.aliergul.utils.database.DatabaseConnection;
import com.bilgeadam.aliergul.utils.database.DatabaseInformation;
import com.bilgeadam.aliergul.utils.helper.FileReaderHelper;

public enum DaoDatabase {
	getInstance;
	
	DatabaseInformation info = new DatabaseInformation();
	
	public void run() {
		
		if (!isThereDatabase()) {
			System.err.println("Database Bulunamadı. Yükleniyor.");
			createdNewDatabase();
			createdNewTables();
		} else {
			System.err.println("Database yüklü");
		}
	}
	
	private boolean createdNewTables() {
		
		try (Connection conn = DatabaseConnection.getInstance().getConn("createdNewTables")) {
			
			Statement st = conn.createStatement();
			String query = FileReaderHelper.getInstance
					.readFiletoString("./src/main/java/com/bilgeadam/aliergul/sql/sql_codes.sql");
			st.executeUpdate(query);
			System.out.println("createdNewTables " + info.getDatabase() + " Tablolar Yüklendi.");
			return true;
		} catch (SQLException e) {
			System.out.println("createdNewTables ERR: " + e.getMessage());
			return false;
		}
		
	}
	
	private boolean createdNewDatabase() {
		try (Connection conn = DatabaseConnection.getInstance().getConnPostgreSql("createdNewDatabase")) {
			// movieId,title,genres
			
			final String query = "CREATE DATABASE  " + info.getDatabase()
					+ " WITH OWNER = postgres ENCODING = 'UTF8' LC_CTYPE = 'Turkish_Turkey.1254' CONNECTION LIMIT = -1;";
			Statement st = conn.createStatement();
			st.executeUpdate(query);
			System.out.println("createdNewTables " + info.getDatabase() + " Yüklendi.");
			
			Thread.sleep(1000);
			return true;
		} catch (SQLException | InterruptedException e) {
			System.out.println("createdNewDatabase ERR: " + e.getMessage());
			return false;
			
		}
		
	}
	
	private boolean isThereDatabase() {
		final String query = "SELECT * FROM pg_database WHERE datname=?;";
		try (Connection conn = DatabaseConnection.getInstance().getConnPostgreSql("isThereDatabase")) {
			// movieId,title,genres
			
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, info.getDatabase());
			ResultSet resut = st.executeQuery();
			
			while (resut.next()) {
				
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("'isThereDatabase' Hata: " + e.getMessage());
			// language.LOG_IS_NOT_SUCCESSFUL()
			// + "\n" + e.getMessage());
			return false;
		}
		
		return false;
	}
}
