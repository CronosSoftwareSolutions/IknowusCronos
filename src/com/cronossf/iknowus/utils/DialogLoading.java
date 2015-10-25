package com.cronossf.iknowus.utils;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.R;

import android.R.style;
import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class DialogLoading extends Dialog {

	public Dialog dialog;
	
	private TextView tvLoadingTitle;
	private TextView tvLoadingDescription;
//	private View viewLoading;

	public DialogLoading(Activity context, String title,String description) {
		super(context, style.Theme_DeviceDefault_Dialog_MinWidth);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog = this;
		setContentView(R.layout.dialog_loading);
		setCanceledOnTouchOutside(false);
//		
//		InputStream stream = null;
//	    try {
//	       stream = context.getAssets().open("loading.gif");
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    viewLoading = new LoadingView(context, stream);	    
	    
		tvLoadingTitle 			= (TextView) findViewById(R.id.tvLoadingTitle);
		tvLoadingDescription		= (TextView) findViewById(R.id.tvLoadingDescription);
//		viewLoading = (View) findViewById(R.id.viewLoading);
//		
		tvLoadingTitle.setText(title);
		tvLoadingDescription.setText(description); 
		
		
		
		ViewGroup vg = (ViewGroup) getWindow().getDecorView();
		Functions.applyTypeFaceViewGroup(context, vg, App.FONT_REGULAR); 
	}
	
	
	
	public void setTitle(String title){
		tvLoadingTitle.setText(title);
	}
	public void setDescription(String description){
		tvLoadingDescription.setText(description);
	}	
}
