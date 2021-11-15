package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.util.List;

import com.bilgeadam.aliergul.utils.database.DatabaseConnection;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionDeletedAccount;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;

public interface IUserOperations<T> {
	// CRUD Bileşenleri
	// database de DML Yapısı
	public boolean createAccount(T dto);
	
	public boolean logIn(T dto) throws ExceptionIncorrectPasswordBlockedStatus, ExceptionDeletedAccount;
	
	public boolean updateUser(T dto);
	
	public T getUserDetails(T dto);
	
	public List<T> getFindUser(T dto);
	
	default Connection getInterfaceConnection(String tag) {
		return DatabaseConnection.getInstance().getConn("IUserOperations/" + tag);
	};
	
}
