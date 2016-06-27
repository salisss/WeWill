package com.sasa.wewill.ui;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.http.AjaxParams;

import com.sasa.wewill.R;
import com.sasa.wewill.R.id;
import com.sasa.wewill.R.layout;
import com.sasa.wewill.R.menu;
import com.sasa.wewill.WeWillApplication;
import com.sasa.wewill.databaseEntity.LoginDataBaseEntity;
import com.sasa.wewill.http.WeWillHttp;
import com.sasa.wewill.http.WeWillHttpCallBack;
import com.sasa.wewill.http.entity.LoginHttpRequstEntity;
import com.sasa.wewill.http.entity.LoginHttpResponseDataEntity;
import com.sasa.wewill.http.entity.LoginHttpResponseEntity;
import com.sasa.wewill.http.interfaces.Constants;
import com.sasa.wewill.http.interfaces.WeWillNetCallBack;
import com.sasa.wewill.utils.ActivityUtils;
import com.sasa.wewill.utils.SharedPreferenceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * @Description: 登陆页面
 * @author lisha
 */
public class LoginActivity extends Activity implements Constants,WeWillNetCallBack {

	private ProgressDialog progressDialog;
    private EditText username;
    private EditText password;
    private Button login_bt, login_cancel;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //去除标题栏
		setContentView(R.layout.login);
		
		WeWillApplication.addActivity(this);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login_bt = (Button) findViewById(R.id.login_bt);
		login_cancel = (Button) findViewById(R.id.login_cancel);
//		//测试数据
//		username.setText("test_new");
//		password.setText("0000");
		
		// 显示登陆账户
		if(!"".equals((CharSequence) SharedPreferenceUtil.getCityData(this, "username", "userinfo", "")))
			username.setText((CharSequence) SharedPreferenceUtil.getCityData(this, "username", "userinfo", ""));
		
		login_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				loginAction();
			}
		});
		
		
		login_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//lisha20150417 注销
				showDialog();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 登录操作
	 */
	private void loginAction(){

		String un = username.getText().toString().trim();
		String pwd = password.getText().toString().trim();
		if(null == un || "".equals(un)){
			Toast.makeText(this, this.getResources().getString(R.string.login_label_username_no), Toast.LENGTH_SHORT).show();
			return;
		}
		if(null == pwd || "".equals(pwd)){
			Toast.makeText(this, this.getResources().getString(R.string.login_label_password_no), Toast.LENGTH_SHORT).show();
			return;
		}
		
		//检测是否有网络
		if(!ActivityUtils.netIsOk(this)){
			//离线登陆
//			FinalDb finalDb = FreshAirApplication.getFinalDb();
//			List<LoginDataBaseEntity> loginDataBaseEntitys = finalDb.findAllByWhere(LoginDataBaseEntity.class,
//					"username=\'" + un + "\'");
//			if(loginDataBaseEntitys != null && loginDataBaseEntitys.size() > 0){
//				LoginDataBaseEntity loginDataBaseEntity = new LoginDataBaseEntity();
//				loginDataBaseEntity.setCompany(loginDataBaseEntitys.get(0).getCompany());
//				loginDataBaseEntity.setName(loginDataBaseEntitys.get(0).getName());
//				loginDataBaseEntity.setUserId(loginDataBaseEntitys.get(0).getUserId());
//				FreshAirApplication.userLogin = loginDataBaseEntity;
//				ActivityUtils.switchTo(LoginActivity.this, QueryWithDateAndFlightNumberActivity.class);
//			}else{
//			}
			ActivityUtils.show(this, getResources().getString(R.string.http_connect_error));
			return;
		}
		
		LoginHttpRequstEntity entity  = new LoginHttpRequstEntity();
		entity.setUserCode(un);
		entity.setPassword(pwd);
		String item = WeWillApplication.getGson().toJson(entity);
		
		AjaxParams params = new AjaxParams();
		params.put("item", item);
		params.put("method", "Interface_GetUserInfo");
		
		WeWillHttp http = new WeWillHttp();
		http.get(LOGIN_URL, params, new WeWillHttpCallBack(this, REQ_LOGIN, this));
		
		// lisha20150421 加载提示
//		login_loading.setVisibility(View.VISIBLE);
//		login_main.setVisibility(View.GONE);
		showProgressDialog();
	}

	@Override
	public void onRequest(int requestId, Object result) {
		hideProgressDialog();
		if(result != null){
			LoginHttpResponseEntity loginHttpResponseEntity = (LoginHttpResponseEntity) result;
			LoginHttpResponseDataEntity loginHttpResponseDataEntity = loginHttpResponseEntity.getData();
			//if(loginHttpResponseEntity.getCode() == HTTP_RESPONSE_SUCCESS_CODE){
				if(loginHttpResponseEntity.getStatus().equals("0")){
				//保存登陆账户
				SharedPreferenceUtil.saveCityData(this, username.getText().toString().trim(), "username", "userinfo");
				//保存数据库
				saveUser2DataBase(loginHttpResponseDataEntity);
				//页面跳转
				ActivityUtils.switchTo(LoginActivity.this, ProductListActivity.class);
			}else{
				// 登陆失败
				ActivityUtils.show(this, getResources().getString(R.string.login_failure));
				//Toast.makeText(this, loginHttpResponseEntity.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	@Override
	public void onNetErrCarllback(int requestId) {
		hideProgressDialog();
		ActivityUtils.show(this, getResources().getString(R.string.http_error));
		
	}
	
	
	/*
	 * 保存用户到本地数据库
	 */
	private void saveUser2DataBase(LoginHttpResponseDataEntity loginHttpResponseDataEntity){
		FinalDb finalDb = WeWillApplication.getFinalDb();
		LoginDataBaseEntity loginDataBaseEntity = new LoginDataBaseEntity();
		loginDataBaseEntity.setCompany(loginHttpResponseDataEntity.getF_COMPANYID());
		loginDataBaseEntity.setName(loginHttpResponseDataEntity.getF_USERNAME());
		loginDataBaseEntity.setUserId(loginHttpResponseDataEntity.getF_USERID());
		loginDataBaseEntity.setUsername(username.getText().toString().trim());
		WeWillApplication.userLogin = loginDataBaseEntity;
		List<LoginDataBaseEntity> loginDataBaseEntitys = finalDb.findAllByWhere(LoginDataBaseEntity.class,
				"userId=\'" +loginHttpResponseDataEntity.getF_USERID() + "\'");
		if(loginDataBaseEntitys != null && loginDataBaseEntitys.size() > 0){
			for(LoginDataBaseEntity entity : loginDataBaseEntitys){
				finalDb.update(loginDataBaseEntity, "userId = " + entity.getUserId());
			}
		}else{
			finalDb.save(loginDataBaseEntity);
		}
	}
	
	// 定义一个显示登陆退出消息的对话框
	public  void showDialog()	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(this.getResources().getString(R.string.login_exitdialog_title));
		builder.setMessage(this.getResources().getString(R.string.login_exitdialog_message));
        builder.setPositiveButton(this.getResources().getString(R.string.confirm),
        		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				WeWillApplication.exit();
			}
		});
        builder.setNegativeButton(this.getResources().getString(R.string.cancel),
        		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
			}
		});
	    builder.create();
	    builder.show();
	}
	
	// 提示加载
	public void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, 
					this.getResources().getString(R.string.login_loading_tip), 
					this.getResources().getString(R.string.login_loading_tip2));
			progressDialog.setCanceledOnTouchOutside(true);//dialog可被取消！
			progressDialog.show();
			return;
		}
		
		if(!progressDialog.isShowing())
			progressDialog.show();
		
	}

	public void hideProgressDialog() {

		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (progressDialog != null && progressDialog.isShowing()) {
				hideProgressDialog();
			} else {
				WeWillApplication.exit();
			}
				
			break;

		default:
			return super.onKeyDown(keyCode, event);
		}
		return false;
	}
}
