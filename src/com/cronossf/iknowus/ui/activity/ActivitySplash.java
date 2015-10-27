package com.cronossf.iknowus.ui.activity;

import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.ui.dialog.DialogLogin;

import android.app.Activity;
import android.content.Intent;
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
		if(new PreferenceManager(this).getUser()!=null){
			startActivity(new Intent(this, ActivityMain.class)); 
			finish();
		}
	}
	
	public void showLogin(View view){		
		new DialogLogin(this).show();
	}
}
