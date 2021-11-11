package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.util.Map;

import com.bilgeadam.aliergul.utils.DatabaseConnection;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;

public interface IAdminOperations<T> {
	
	public void deleteAccount(T dto) throws ExceptionNotAuthorizedError;
	
	public void changedUserRoleStatus(T dto) throws ExceptionNotAuthorizedError;
	
	public void changedUserActiveStatus(T dto) throws ExceptionNotAuthorizedError;
	
	public void addNewRole(T dto) throws ExceptionNotAuthorizedError;
	
	public Map<String, Integer> countOfRecordByRoles() throws ExceptionNotAuthorizedError;
	
	default Connection getInterfaceConnection() {
		return DatabaseConnection.getInstance().getConn();
	};
}
