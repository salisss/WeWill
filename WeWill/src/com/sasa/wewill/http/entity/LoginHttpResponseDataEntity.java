package com.sasa.wewill.http.entity;

import com.google.gson.annotations.Expose;

public class LoginHttpResponseDataEntity {
	@Expose
	private String F_USERID;     //工号
	@Expose
	private String F_USERNAME;     //姓名
	@Expose
	private String F_COMPANYID;   //公司名称
	
	public String getF_USERID() {
		return F_USERID;
	}
	public void setF_USERID(String f_USERID) {
		F_USERID = f_USERID;
	}
	public String getF_USERNAME() {
		return F_USERNAME;
	}
	public void setF_USERNAME(String f_USERNAME) {
		F_USERNAME = f_USERNAME;
	}
	public String getF_COMPANYID() {
		return F_COMPANYID;
	}
	public void setF_COMPANYID(String f_COMPANYID) {
		F_COMPANYID = f_COMPANYID;
	}
}
