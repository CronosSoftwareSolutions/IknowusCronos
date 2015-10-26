package com.cronossf.iknowus.ui.activity;

import com.cronossf.iknowus.R;
import com.cronossf.iknowus.ui.dialog.DialogLogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ActivityFavorites extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
	}
}
