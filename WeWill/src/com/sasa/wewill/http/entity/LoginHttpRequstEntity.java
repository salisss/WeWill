package com.sasa.wewill.http.entity;

import com.google.gson.annotations.Expose;

public class LoginHttpRequstEntity {
	@Expose
	private String UserCode;
	@Expose
	private String Password;
	public String getUserCode() {
		return UserCode;
	}
	public void setUserCode(String userCode) {
		UserCode = userCode;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
