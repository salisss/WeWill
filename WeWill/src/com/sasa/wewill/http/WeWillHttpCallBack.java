package com.sasa.wewill.http;


import com.sasa.wewill.WeWillApplication;
import com.sasa.wewill.http.interfaces.WeWillNetCallBack;

import android.app.Activity;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * @Description:Http请求回调
 */

public class WeWillHttpCallBack extends AjaxCallBack<String> {

	private int requestID;
	private Activity context;
	private DataTranslator<?> dataTranslator;
	private WeWillNetCallBack callBack;
	
	public WeWillHttpCallBack(Activity context, int requestID, WeWillNetCallBack callBack){
		this.context = context;
		this.requestID = requestID;
		this.callBack = callBack;
		dataTranslator = WeWillApplication.getDataTranslator();
	}
	
	@Override
	public void onLoading(long count, long current) {
		// TODO Auto-generated method stub
		super.onLoading(count, current);
	}
	
	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		super.onSuccess(t);
		callBack.onRequest(requestID, dataTranslator.dataTranslator(requestID, t));
	}
	
	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		// TODO Auto-generated method stub
		super.onFailure(t, errorNo, strMsg);
		callBack.onNetErrCarllback(requestID);
	}
	
}
