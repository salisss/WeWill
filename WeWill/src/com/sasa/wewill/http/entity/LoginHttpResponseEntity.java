package com.sasa.wewill.http.entity;

import com.google.gson.annotations.Expose;


public class LoginHttpResponseEntity{
	
		
		@Expose
		private String status;
		@Expose
		private LoginHttpResponseDataEntity data;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public LoginHttpResponseDataEntity getData() {
			return data;
		}
		public void setData(LoginHttpResponseDataEntity data) {
			this.data = data;
		}

	
}
