package com.sasa.wewill.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
	
	/**
	 * 保存数据
	 * @param context   上下文
	 * @param data   保存数据
	 * @param name  保存数据标识位
	 * @param s
	 */
	public static void saveCityData(Context context,Object data,String name,String s){
		SharedPreferences settings = context.getSharedPreferences(s, 0);
		SharedPreferences.Editor editor = settings.edit();
		if(data instanceof String){
			editor.putString(name, (String) data);
		}else if(data instanceof Boolean){
			editor.putBoolean(name, (Boolean) data);
		}else if(data instanceof Float){
			editor.putFloat(name, (Float) data);
		}
		editor.commit();
	}
	
	/**
	 * 得到数据
	 * @param context   上下文
	 * @param name   
	 * @param s
	 * @param object
	 * @return
	 */
	public static Object getCityData(Context context,String name,String s,Object object){
		SharedPreferences settings = context.getSharedPreferences(s, 0);
		if(object instanceof String){
			return settings.getString(name, "");
		}else if(object instanceof Boolean){
			return settings.getBoolean(name, false);
		}else if(object instanceof Float){
			return settings.getFloat(name, 0);
		}
		return null;
	}
	
}
