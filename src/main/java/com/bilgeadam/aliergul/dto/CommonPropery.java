package com.bilgeadam.aliergul.dto;

import java.io.Serializable;
import java.sql.Date;

abstract class CommonPropery implements Serializable {
	
	private static final long serialVersionUID = 2510988565202060460L;
	private int id;
	private Date createDate;
	
	public CommonPropery() {
		
	}
	
	public CommonPropery(int id, Date createDate) {
		super();
		this.id = id;
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "CommonPropery [id=" + id + ", create_date=" + createDate + "]";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date create_date) {
		this.createDate = create_date;
	}
	
}
