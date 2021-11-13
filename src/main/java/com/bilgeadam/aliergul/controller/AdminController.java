package com.bilgeadam.aliergul.controller;

import java.util.Map;

import com.bilgeadam.aliergul.dao.DaoAdmin;
import com.bilgeadam.aliergul.dao.IAdminOperations;
import com.bilgeadam.aliergul.dto.DtoBlogRole;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;

public class AdminController implements IAdminOperations<DtoUserDetails> {
	private static AdminController instance;
	private DaoAdmin dao;
	
	private AdminController(DtoUserDetails dto) {
		this.dao = new DaoAdmin(dto);
		
	}
	
	public static AdminController getInstance(DtoUserDetails dto) {
		if (instance == null) {
			instance = new AdminController(dto);
		}
		return instance;
	}
	
	@Override
	public boolean deleteAccount(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		
		return dao.deleteAccount(dto);
	}
	
	@Override
	public boolean changedUserRoleStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		
		return dao.changedUserRoleStatus(dto);
	}
	
	@Override
	public boolean changedUserActiveStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		
		return dao.changedUserActiveStatus(dto);
	}
	
	@Override
	public Map<String, Integer> countOfRecordByRoles() throws ExceptionNotAuthorizedError {
		
		return dao.countOfRecordByRoles();
	}
	
	@Override
	public DtoBlogRole getUserRole(DtoUserDetails dto) {
		
		return dao.getUserRole(dto);
	}
	
}
