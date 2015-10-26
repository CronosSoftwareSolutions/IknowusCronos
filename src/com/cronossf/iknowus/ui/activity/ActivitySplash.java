package com.cronossf.iknowus.ui.activity;

import com.cronossf.iknowus.R;
import com.cronossf.iknowus.ui.dialog.DialogLogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class ActivitySplash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
	}
	
	public void showLogin(View view){		
		new DialogLogin(this).show();
	}
}
