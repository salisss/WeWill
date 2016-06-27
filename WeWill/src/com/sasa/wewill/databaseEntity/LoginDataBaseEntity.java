package com.sasa.wewill.databaseEntity;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 存储本地数据库实体类
 */
public class LoginDataBaseEntity {
	
	@Id(column="id")
	private String id;     		//主键
	private String userId;   	//工号
	private String name;   		//姓名
	private String company;    //公司名称
	private String username;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
