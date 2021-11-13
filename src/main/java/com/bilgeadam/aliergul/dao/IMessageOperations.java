package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.util.List;

import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.database.DatabaseConnection;

public interface IMessageOperations {
	public boolean appendMessageDatabase(DtoMessage msg);
	
	public List<DtoMessage> getListMessage(DtoUserDetails user, DtoUserDetails friend);
	
	public List<DtoUserDetails> getListUserMessage(DtoUserDetails dto);
	
	default Connection getInterfaceConnection() {
		return DatabaseConnection.getInstance().getConn();
	};
}
