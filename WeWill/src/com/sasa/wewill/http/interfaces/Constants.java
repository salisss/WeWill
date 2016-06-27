package com.sasa.wewill.http.interfaces;
/**
 * @Description: Http常量
 */
public interface Constants {
	
	public int HTTP_RESPONSE_SUCCESS_CODE = 100;
	
	public int REQ_LOGIN = 408;   //登录接口
	
	public int REQ_COMMIT = 409;  //配备单提交接口
	
	public int REQ_MEAL_COMMIT = 406;  //餐食提交接口
	
	public int REQ_TOOL_COMMIT = 506; //餐具提交接口
	
	public int REQ_RECYLE = 505;   //配备单回收接口
	
	public int REQ_TOOL_RECYLE = 508; //餐具回收接口
	
	public int REQ_DETAIL_QUERY = 500;  //查询详细接口
	
	public int REQ_DETAIL_MEAL_QUERY = 501;  //查询详细接口
	
	public int REQ_MEAL_QUERY = 502;  //餐食查询
	
	public int REQ_TOOL_QUERY = 503; //餐具查询
	

	public int REQ_LIST_QUERY = 507;  //列表请求接口
	public int REQ_LIST_QUERY1 = 509;  //列表请求接口
	

	public String BASE_URL = "http://mumobiletest30.ceair.com:8011/11206260/public/ydcw_hs/";   //基本URL

//	public String BASE_URL = "http://172.20.52.176:8080/xx/xx/ydcw/";
	public String LOGIN_URL = BASE_URL + "GetUserInfo";
	
	public String REQ_DETAIL_QUERY_URL = BASE_URL + "Equ_Query";     //查询详细接口URL   配备单
	
	public String REQ_DETAIL_QUERY_MEAL_URL = BASE_URL + "Meal_AFQuery";     //查询详细接口URL  餐食
	
	public String REQ_DETAIL_QUERY_TOOL_URL = BASE_URL + "Cutlery_AFQuery"; //查询详细接口URL  餐具
	
	public String REQ_DETAIL_COMMIT_URL = BASE_URL + "queryFlightInformationJSON";    //提交接口
	
//	public String REQ_DETAIL_COMMIT_URL = "http://172.20.52.176:8080/xx/xx/ydcw_hs/queryFlightInformationJSON";    //提交接口
	
	public String REQ_MEAL_COMMIT_URL = BASE_URL + "Meal_AFUpdate";    //提交接口  餐食
	
//	public String REQ_MEAL_COMMIT_URL = "http://172.20.52.176:8080/11206260/public/ydcw_/Meal_AFUpdate";    //提交接口  餐食
	
	public String REQ_TOOL_COMMIT_URL = BASE_URL + "Cutlery_AFUpdate";    //提交接口  餐具
	
	public String REQ_RECYLE_URL = BASE_URL + "Equ_AFRec";  //回收接口URL   
	
	public String REQ_LIST_QUERY_URL = BASE_URL + "getDJList";  //列表查询URL
	
//	public String REQ_LIST_QUERY_URL = "http://172.20.52.176:8080/xx/xx/ydcw/getDJList";  //列表查询URL
	
	public String REQ_LIST_QUERY_URL1 = BASE_URL + "getDJListCW";  //列表查询URL
	
//	public String REQ_LIST_QUERY_URL1 = "http://172.20.52.176:8080/xx/xx/ydcw/getDJListCW";  //列表查询URL
	
	public String REQ_RECYLE_TOOL_URL = BASE_URL + "Cutlery_AFRec";
	
	public static int ACTIVITY_RESULT_REQUEST_CODE = 7999;
	
	public static int ACTIVITY_RESULT_RESPONSE_CODE = 8999;
	
	public static String salt = "";
	
//	public String REQ_MEAL_TOOL_LIST_QUERY_URL = BASE_URL + "Cutlery_AFQuery";  //餐具列表查询URL
}
