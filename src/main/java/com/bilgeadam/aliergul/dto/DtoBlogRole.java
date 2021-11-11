package com.bilgeadam.aliergul.dto;

import java.io.Serializable;
import java.sql.Date;

public class DtoBlogRole extends CommonPropery implements Serializable {
	private static final long serialVersionUID = 3379779308184530892L;
	private String roleName = "";
	private boolean userChangeActive = false;
	private boolean viewNumberOfRecord = false;
	private boolean userDeleteAccount = false;
	private boolean userChangeRole = false;
	private boolean addNewRole = false;
	
	public DtoBlogRole(int id, Date createDate, String roleName, boolean userChangeActive, boolean viewNumberOfRecord,
			boolean userDeleteAccount, boolean userChangeRole, boolean addNewRole) {
		super(id, createDate);
		this.roleName = roleName;
		this.userChangeActive = userChangeActive;
		this.viewNumberOfRecord = viewNumberOfRecord;
		this.userDeleteAccount = userDeleteAccount;
		this.userChangeRole = userChangeRole;
		this.addNewRole = addNewRole;
		
	}
	
	@Override
	public String toString() {
		return "BlogRole [roleName=" + roleName + ", userChangeActive=" + userChangeActive + ", viewNumberOfRecord="
				+ viewNumberOfRecord + ", userDeleteAccount=" + userDeleteAccount + ", userChangeRole=" + userChangeRole
				+ ", addNewRole=" + addNewRole + ", getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + "]";
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public boolean isUserChangeActive() {
		return userChangeActive;
	}
	
	public void setUserChangeActive(boolean userChangeActive) {
		this.userChangeActive = userChangeActive;
	}
	
	public boolean isViewNumberOfRecord() {
		return viewNumberOfRecord;
	}
	
	public void setViewNumberOfRecord(boolean viewNumberOfRecord) {
		this.viewNumberOfRecord = viewNumberOfRecord;
	}
	
	public boolean isUserDeleteAccount() {
		return userDeleteAccount;
	}
	
	public void setUserDeleteAccount(boolean userDeleteAccount) {
		this.userDeleteAccount = userDeleteAccount;
	}
	
	public boolean isUserChangeRole() {
		return userChangeRole;
	}
	
	public void setUserChangeRole(boolean userChangeRole) {
		this.userChangeRole = userChangeRole;
	}
	
	public boolean isAddNewRole() {
		return addNewRole;
	}
	
	public void setAddNewRole(boolean addNewRole) {
		this.addNewRole = addNewRole;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
