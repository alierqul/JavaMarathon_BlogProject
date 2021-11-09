package com.bilgeadam.aliergul.dto;

import java.io.Serializable;
import java.sql.Date;

public class DtoUser extends CommonPropery implements Serializable {
	
	private static final long serialVersionUID = -7360970544462181013L;
	private String email;
	private String passwod;
	private boolean active;
	
	public DtoUser() {
		super();
		
	}
	
	public DtoUser(int id, Date createDate, String email, String passwod, boolean active) {
		super(id, createDate);
		this.email = email;
		this.passwod = passwod;
		this.active = active;
	}
	
	public DtoUser(String email, String passwod) {
		super();
		this.email = email;
		this.passwod = passwod;
	}
	
	@Override
	public String toString() {
		return "DtoUser [email=" + email + ", passwod=" + passwod + ", active=" + active + ", toString()="
				+ super.toString() + "]";
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswod() {
		return passwod;
	}
	
	public void setPasswod(String passwod) {
		this.passwod = passwod;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
