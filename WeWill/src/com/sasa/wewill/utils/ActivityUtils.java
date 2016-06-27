/**
 * Copyright (C) 2012 TookitForAndroid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sasa.wewill.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Stack;

import com.sasa.wewill.R;
import com.sasa.wewill.WeWillApplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @Description: Activity特性辅助工具
 * @author ChenHao
 * @email chenhao27@ceair.com
 */
public class ActivityUtils {

	/** Activity引用  */
	public Activity self;
	
	/** Context引用 */
	public Context context;
	
	/**  双击退出模块 */
	private DClickExit mDClickExit;
	
	/** Activity栈 **/
	private static Stack<Activity> ActivityStack = new Stack<Activity>();
	
	/**
	 * <b>description : 		TODO
	 *  time : 		2012-8-22 下午11:01:55
	 * @param self
	 */
	public ActivityUtils(Activity act){
		self = act;
		context = self;
	}
	
	/**
	 *  description : 启用双击返回键退出程序
	 *  time : 		2012-7-18 下午9:24:41
	 */
	public void enabledDClickExit(){
		mDClickExit = new DClickExit(self);
	}

	/**
	 * <b>description : 		检查按键
	 *  time : 		2012-8-22 下午11:03:57
	 * @param keyCode
	 * @return
	 */
	public boolean checkExist(int keyCode) {
		if( null != mDClickExit ){
			return mDClickExit.doubleClickExit(keyCode);
		}else{
			return false;
		}
	}
	
	/**
	 * <b>description : 		将Activity压力栈中
	 *  time : 		2012-9-12 下午3:59:13
	 * @param activity
	 */
	public static void pushActivity(Activity activity){
		ActivityStack.add(activity);
	}
	
	/**
	 * <b>description : 		将顶端的Activity弹出栈顶
	 *  time : 		2012-9-12 下午3:59:55
	 */
	public static Activity popActivity(){
		return ActivityStack.lastElement();
	}
	
	/**
	 * <b>description : 		Activity栈回退到某个Activity
	 *  time : 		2012-9-12 下午4:01:19
	 * @param target
	 */
	public static void rollbackTo(Class<? extends Activity> target){
		for(Activity activity : ActivityStack){
			if(activity.getClass().equals(target)){
				//已经是目标Activity，退出循环
				break;
			}else{
				//不是目标栈，关闭它
				activity.finish();
			}
		}
	}
	
	/**
	 *  description : 设置Activity全屏显示。
	 * @param activity 			Activity引用
	 * @param isFull 			true为全屏，false为非全屏
	 */
	public static void setFullScreen(Activity activity,boolean isFull){
		hideTitleBar(activity);
		Window window = activity.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		if (isFull) {
			params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			window.setAttributes(params);
			window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			window.setAttributes(params);
			window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
	/**
	 * <b>description : 		获取系统状态栏高度
	 *  time : 		2012-9-5 下午7:23:12
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity){
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
		    int dpHeight = Integer.parseInt(field.get(obj).toString());
		    int pxHeight = activity.getResources().getDimensionPixelSize(dpHeight);
		    return pxHeight;
		} catch (Exception e1) {
		    e1.printStackTrace();
		    return 0;
		} 
		
	}
	
	/**
	 *  description : 隐藏Activity的系统默认标题栏
	 * @param activity Activity对象
	 */
	public static void hideTitleBar(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 *  description : 强制设置Actiity的显示方向为垂直方向。
	 * @param activity 			Activity对象
	 */
	public static void setScreenVertical(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	/**
	 *  description : 强制设置Actiity的显示方向为横向。
	 * @param activity 			Activity对象
	 */
	public static void setScreenHorizontal(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	/**
     *  description : 隐藏软件输入法
     *  time :        2012-7-12 下午7:20:00
	 * @param activity
	 */
	public static void hideSoftInput(Activity activity){
	    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	/**
	 *  description : 使UI适配输入法
	 *  time : 		2012-7-17 下午10:21:26
	 * @param activity
	 */
	public static void adjustSoftInput(Activity activity) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	
	/**
	 *  description : 跳转到某个Activity
	 *  time : 		2012-7-8 下午3:20:00
	 * @param activity			本Activity
	 * @param targetActivity	目标Activity的Class
	 */
	public static void switchTo(Activity activity,Class<? extends Activity> targetActivity){
		switchTo(activity, new Intent(activity,targetActivity),-1,-1);
	}
	
	/**
	 *  description : 跳转到某个Activity
	 *  time : 		2012-7-8 下午3:20:00
	 * @param activity			本Activity
	 * @param targetActivity	目标Activity的Class
	 */
	public static void switchTo(Activity activity,Class<? extends Activity> targetActivity,int aninOne,int aninTwo){
		switchTo(activity, new Intent(activity,targetActivity),aninOne,aninTwo);
	}
	
	/**
	 *  description : 根据给定的Intent进行Activity跳转
	 *  time : 		2012-7-8 下午3:22:23
	 * @param activity			Activity对象
	 * @param intent			要传递的Intent对象
	 */
	public static void switchTo(Activity activity,Intent intent,int aninOne,int aninTwo){
		activity.startActivity(intent);
		
		if(aninOne==-1 || aninTwo == -1){
			return;
		}
		activity.overridePendingTransition(aninOne,aninTwo);
	}
	
	public static void switchTo(Activity activity,Intent intent){
		activity.startActivity(intent);
		
	}
	
	/**
	 *  description : 带参数进行Activity跳转
	 *  time : 		2012-7-8 下午3:24:54
	 * @param activity			Activity对象
	 * @param targetActivity	目标Activity的Class
	 * @param params			跳转所带的参数
	 */
	public static void switchTo(Activity activity,Class<? extends Activity> targetActivity,
	        Map<String,Object> params,int aninOne,int aninTwo){
			Intent intent = new Intent(activity,targetActivity);
			if( null != params ){
				for(Map.Entry<String, Object> entry : params.entrySet()){
					IntentUtil.setValueToIntent(intent, entry.getKey(), entry.getValue());
				}
			}
			switchTo(activity, intent,aninOne,aninTwo);
	}
	
	/**
	 *  description : 显示Toast消息，并保证运行在UI线程中
	 *  time : 		2012-7-10 下午08:36:02
	 * @param activity
	 * @param message
	 */
	public static void show(final Activity activity,final String message){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	/**
	 *  description : 显示Toast消息，并保证运行在UI线程中,isNoBUG 解决Taost 长时间显示 BUG，此方法会覆盖掉上一个Taost
	 *  time : 		2012-7-10 下午08:36:02
	 * @param activity
	 * @param message
	 */
	public static void show(final Activity activity,final String message,boolean isNoBUG){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				LayoutInflater inflate = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				View layout = inflate.inflate(R.layout.my_toast, null);
				//实例化TextView对象
				TextView tv_toast_message = (TextView) layout.findViewById(R.id.tv_toast_message);
				tv_toast_message.setText(message);
				//持续时间 
				WeWillApplication.getToast().setDuration(Toast.LENGTH_SHORT);
				//这里的setView不能少，当然set的view也可以是从自己写的布局xml中构造
				WeWillApplication.getToast().setView(layout); 
				//show出来 
				WeWillApplication.getToast().show();
			}
		});
	}
	
	/**
	 *  解决ScrollView嵌套ListView的冲突方法
	 * @param listView
	 * @param viewBox 
	 */
	public static void setListViewHeight(ListView listView, LinearLayout viewBox) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		
		ViewGroup.LayoutParams paramsViewPage = viewBox.getLayoutParams();
		paramsViewPage.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))*2;
		viewBox.setLayoutParams(paramsViewPage);
	}
	
	/**
	 * 检测是否有网络
	 * @param context
	 * @return
	 */
	public static boolean netIsOk(Context context){
		Context mContext = context.getApplicationContext();
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null){
            return false;
        }else{
        	 // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0){
            	 for (int i = 0; i < networkInfo.length; i++){
            		 if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
            			 return true;
            		 }
            	 }
            }
            return false;
        }
	}
	
}
