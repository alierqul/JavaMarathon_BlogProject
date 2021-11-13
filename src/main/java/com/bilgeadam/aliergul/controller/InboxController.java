package com.bilgeadam.aliergul.controller;

import java.util.List;

import com.bilgeadam.aliergul.dao.DaoMessage;
import com.bilgeadam.aliergul.dao.IMessageOperations;
import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;

public enum InboxController implements IMessageOperations {
	getInstance;
	
	private DaoMessage dao;
	
	private InboxController() {
		this.dao = new DaoMessage();
	}
	
	@Override
	public boolean appendMessageDatabase(DtoMessage msg) {
		
		return dao.appendMessageDatabase(msg);
	}
	
	@Override
	public List<DtoMessage> getListMessage(DtoUserDetails user, DtoUserDetails friend) {
		
		return dao.getListMessage(user, friend);
	}
	
	@Override
	public List<DtoUserDetails> getListUserMessage(DtoUserDetails dto) {
		
		return dao.getListUserMessage(dto);
	}
	
}
