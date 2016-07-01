package com.sasa.wewill.ui;

import com.sasa.wewill.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ProductListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //去除标题栏
		setContentView(R.layout.productlist);
	}
	
}
