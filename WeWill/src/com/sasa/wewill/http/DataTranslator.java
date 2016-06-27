package com.sasa.wewill.http;


import com.sasa.wewill.WeWillApplication;
import com.sasa.wewill.http.entity.LoginHttpResponseEntity;
import com.sasa.wewill.http.interfaces.Constants;

/**
 * @Description: json数据解析工具类
 */
public class DataTranslator<HashSet> implements Constants{

	public Object dataTranslator(int requesID, String json){
		switch (requesID) {
		case REQ_LOGIN:
			LoginHttpResponseEntity loginEntity = WeWillApplication.getGson().fromJson(json, LoginHttpResponseEntity.class);
			return loginEntity;

		default:
			break;
		}
		return null;
	}
}
