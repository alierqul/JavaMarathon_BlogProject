package com.bilgeadam.aliergul.dao;

import java.sql.Connection;

import com.bilgeadam.aliergul.utils.DatabaseConnection;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;

public interface IUserOperations<T> {
	// CRUD Bileşenleri
	// database de DML Yapısı
	public boolean createAccount(T dto);
	
	public boolean logIn(T dto) throws ExceptionIncorrectPasswordBlockedStatus;
	
	public boolean updateUser(T dto);
	
	public T getUserDetails(T dto);
	
	default Connection getInterfaceConnection() {
		return DatabaseConnection.getInstance().getConn();
	};
	
}
