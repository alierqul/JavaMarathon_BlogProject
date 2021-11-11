package com.bilgeadam.aliergul.dto;

import java.io.Serializable;
import java.sql.Date;

public class DtoUserDetails extends DtoUser implements Serializable {
	
	private static final long serialVersionUID = -2393051694348915808L;
	private String name;
	private String surName;
	private String phone;
	private String hescode;
	private int roleId;
	
	public DtoUserDetails() {
		super();
		
	}
	
	public DtoUserDetails(String email, String passwod) {
		super(email, passwod);
		this.metaData = name + surName + hescode + email.substring(0, email.indexOf("@"));
	}
	
	public DtoUserDetails(String email, String passwod, String name, String surName, String phone, String hescode) {
		super(email, passwod);
		this.name = name;
		this.surName = surName;
		this.phone = phone;
		this.hescode = hescode;
		this.metaData = name + surName + hescode + email.substring(0, email.indexOf("@"));
		
	}
	
	public DtoUserDetails(int id, Date createDate, String email, String passwod, String metaData, boolean active,
			String name, String surName, String phone, String hescode, int roleId) {
		super(id, createDate, email, passwod, metaData, active);
		this.name = name;
		this.surName = surName;
		this.phone = phone;
		this.hescode = hescode;
		this.roleId = roleId;
		this.metaData = name + surName + hescode + email.substring(0, email.indexOf("@"));
	}
	
	@Override
	public String toString() {
		return super.toString() + " ,DtoUserDetails [name=" + name + ", surName=" + surName + ", telNumber=" + phone
				+ ", hescode=" + hescode + ", role=" + roleId + "]";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurName() {
		return surName;
	}
	
	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getHescode() {
		return hescode;
	}
	
	public void setHescode(String hescode) {
		this.hescode = hescode;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
