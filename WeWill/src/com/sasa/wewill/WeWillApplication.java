package com.sasa.wewill;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.sasa.wewill.databaseEntity.LoginDataBaseEntity;
import com.sasa.wewill.http.DataTranslator;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class WeWillApplication extends Application implements DbUpdateListener {

	public static WeWillApplication instance;
	private static Gson gson; //gson解析
	private static FinalDb finalDb;
	private static DataTranslator<HashSet> dataTranslator;
	private static Toast mytoast; //提示
	
	public static LoginDataBaseEntity userLogin;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	/**
	 * 得到Json解析器对象
	 * @return
	 */
	public static Gson getGson(){
		if(gson == null)
			gson = new Gson();
		return gson;
	}
	
	/**
	 * 得到数据库操作对象
	 * @return
	 */
	public static FinalDb getFinalDb(){
		if(finalDb == null)
			finalDb = FinalDb.create(instance,"ceair_fresh_air.db",true,1,instance);
		return finalDb;
	}
	
	 /**
     * 获取数据解析类
     * @return
     */
	public static DataTranslator<?> getDataTranslator(){
		if(dataTranslator == null){
			dataTranslator = new DataTranslator();	
		}
		return dataTranslator;
		
	}
	
	/**
	 * 得到一个Toast对象(解决Toast 长时间队列显示 BUG)
	 * @return
	 */
	public static Toast getToast(){
		//事先定义变量，而且只使用这一个 
		if(mytoast == null)
		{
			mytoast = new Toast(instance); 
		}
		return mytoast;
	}
	
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	// 注销
	private static List<Activity> activityList = new LinkedList<Activity>();
	
	public static void addActivity(Activity activity){
	   activityList.add(activity);
    }
	
    public static void exit(){
	   for(Activity activity:activityList)
	    {
	      activity.finish();
	    }
	     System.exit(0);
    }
}
