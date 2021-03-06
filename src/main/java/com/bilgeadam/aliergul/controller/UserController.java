package com.bilgeadam.aliergul.controller;

import java.util.List;

import com.bilgeadam.aliergul.dao.DaoUserDetails;
import com.bilgeadam.aliergul.dao.IUserOperations;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionDeletedAccount;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;

public enum UserController implements IUserOperations<DtoUserDetails> {
	getInstance;
	
	private DaoUserDetails dao;
	
	private UserController() {
		this.dao = new DaoUserDetails();
	}
	
	@Override
	public boolean createAccount(DtoUserDetails dto) {
		
		return dao.createAccount((DtoUserDetails) dto);
	}
	
	@Override
	public boolean logIn(DtoUserDetails dto) throws ExceptionIncorrectPasswordBlockedStatus, ExceptionDeletedAccount {
		
		return dao.logIn(dto);
	}
	
	@Override
	public boolean updateUser(DtoUserDetails dto) {
		
		return dao.updateUser(dto);
	}
	
	@Override
	public DtoUserDetails getUserDetails(DtoUserDetails dto) {
		
		return dao.getUserDetails(dto);
	}
	
	@Override
	public List<DtoUserDetails> getFindUser(DtoUserDetails dto) {
		
		return dao.getFindUser(dto);
	}
	
}
