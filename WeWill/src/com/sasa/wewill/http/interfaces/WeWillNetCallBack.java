package com.sasa.wewill.http.interfaces;

/**
 * @Description: Http请求回调接口
 */

public interface WeWillNetCallBack {

	void onRequest(int requestId, Object result);
	
	void onNetErrCarllback(int requestId);
}
