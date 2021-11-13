package com.bilgeadam.aliergul.dto;

import java.io.Serializable;
import java.sql.Date;

public class DtoMessage extends CommonPropery implements Serializable {
	private static final long serialVersionUID = 1746285882143986687L;
	private int userId;
	private int messageId;
	private String message;
	private String nameAndSurname;
	private String email;
	
	public DtoMessage(int userId, int messageId, String message) {
		super();
		this.userId = userId;
		this.messageId = messageId;
		this.message = message;
	}
	
	public DtoMessage(int userId, int messageId) {
		super();
		this.userId = userId;
		this.messageId = messageId;
	}
	
	public DtoMessage(int id, Date createDate, int userId, int messageId, String message, String nameAndSurname,
			String email) {
		super(id, createDate);
		this.userId = userId;
		this.messageId = messageId;
		this.message = message;
		this.nameAndSurname = nameAndSurname;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "DtoMessage [nameAnSurname=" + nameAndSurname + ", message=" + message + ", userID=" + userId
				+ ", email=" + email + ", getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + ", messageId="
				+ messageId + "]";
	}
	
	public DtoMessage(String message) {
		super();
		this.message = message;
	}
	
	public String getNameAndSurname() {
		return nameAndSurname;
	}
	
	public void setNameAndSurname(String nameAndSurname) {
		this.nameAndSurname = nameAndSurname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getMessageId() {
		return messageId;
	}
	
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
