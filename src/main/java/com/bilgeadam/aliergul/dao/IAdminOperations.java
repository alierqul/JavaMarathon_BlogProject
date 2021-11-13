package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.Map;

import com.bilgeadam.aliergul.dto.DtoBlogRole;
import com.bilgeadam.aliergul.utils.database.DatabaseConnection;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;

public interface IAdminOperations<T> {
	
	public DtoBlogRole getUserRole(T dto);
	
	public boolean deleteAccount(T dto) throws ExceptionNotAuthorizedError;
	
	public boolean changedUserRoleStatus(T dto) throws ExceptionNotAuthorizedError;
	
	public boolean changedUserActiveStatus(T dto) throws ExceptionNotAuthorizedError;
	
	public Map<Date, Integer> countOfRecordByDay() throws ExceptionNotAuthorizedError;
	
	public Map<String, Integer> countOfRecordByRoles() throws ExceptionNotAuthorizedError;
	
	default Connection getInterfaceConnection() {
		return DatabaseConnection.getInstance().getConn();
	};
}
