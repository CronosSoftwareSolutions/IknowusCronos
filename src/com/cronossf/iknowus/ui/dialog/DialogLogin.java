package com.cronossf.iknowus.ui.dialog;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.components.FloatingEditText;
import com.cronossf.iknowus.task.TaskStartSession;
import com.cronossf.iknowus.ui.activity.ActivityMain;
import com.cronossf.iknowus.utils.Functions;

import android.R.style;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class DialogLogin extends Dialog {

	private Activity activity;
	private PreferenceManager prefs;
	private FloatingEditText etLoginEmail;
	private FloatingEditText etLoginPassword;
	private Button btnLogin;

	public DialogLogin(Activity activity) {
		super(activity, style.Theme_DeviceDefault_Dialog_MinWidth);
		this.activity = activity;		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_login);
		setCancelable(true); 
		initComponents();
		prefs = new PreferenceManager(activity);
		btnLogin.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startSesion();
			}
		});
		ViewGroup vg = (ViewGroup) getWindow().getDecorView();
		Functions.applyTypeFaceViewGroup(activity, vg, App.FONT_REGULAR);		
	}
	
	private void initComponents() {	
		etLoginEmail = (FloatingEditText) findViewById(R.id.etLoginEmail);
		etLoginPassword = (FloatingEditText) findViewById(R.id.etLoginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
	}
	
	public void startSesion(){
		String email = etLoginEmail.getText().toString();
		etLoginEmail.setText(email); 
		String password = etLoginPassword.getText().toString();
		if(email.length()>1 ){
				TaskStartSession.OnStartSessionComplete onStartSessionComplete = new TaskStartSession.OnStartSessionComplete() {					
					@Override
					public void onStartSessionCompleted(String result) {
						if (result.equals("OK")) { 							
							Toast.makeText(activity, "Sesion iniciada correctamente.", Toast.LENGTH_SHORT).show();
							activity.startActivity(new Intent(activity, ActivityMain.class));
							cancel();
							activity.finish();							
						} else if(result.equals("PASSWORD")){ 
							etLoginEmail.setValidateResult(false, "Usuario o contraseña no validos.");
							etLoginPassword.setText(""); 
						}else{
							etLoginEmail.setValidateResult(false, "Usuario o contraseña no validos.");
							etLoginPassword.setText("");
							Toast.makeText(activity, "No se logro iniciar sesion, verifique sus datos y vuelva a intentar.", Toast.LENGTH_LONG).show();
						}
					}
				};				
				new TaskStartSession(activity, email, password, onStartSessionComplete).execute();
		}else{
			etLoginEmail.setValidateResult(false, "Usuario o contraseña no validos.");
			etLoginPassword.setText(""); 
		}
	}
}
