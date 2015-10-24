package com.cronossf.iknowus;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.os.Environment;

public class App {

	public static final String TAG 				= "com.cronossf.iknowus";
	public static final String NAME 			= "Iknowus";
	public static final String version 			= "1.0.1";
	public static final int NOTIFICATION_ID 	= 12345;

	public static final String WS 		= "http://dualandroid.dualbiz.net:8780/WSIknowus/Services";
	public static final String SERVER_IMAGEN 	= "http://dualandroid.dualbiz.net:8780/imagenIknowus/";
	
	public static final int PLAY_SERVICES_RESOLUTION_REQUEST 	= 9000;	
	public static final String PROPERTY_APP_VERSION 			= "appVersion";
	public static final String PROPERTY_EXPIRATION_TIME 		= "onServerExpirationTimeMs";
	public static final String EXTRA_MESSAGE 					= "obj";	
	public static final String PROPERTY_USER 					= "user";
	
	public static final String PROJECT_GCM_NAME 				= "JPush";
	public static final String SENDER_ID 						= "624309480748";

	public static File getPhotoCache(){
		File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + NAME + File.separator  + "cache" + File.separator);
		root.mkdirs();
		return root;
	}
	
	public static File getPhotoDir(){
		File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + NAME + File.separator );
		root.mkdirs();
		return root;
	}
	
	//FONTS
	public static final String FONT_REGULAR 	=  		"fonts/Roboto-Regular.ttf";
	public static final String FONT_BOLD 		= 		"fonts/Roboto-Bold.ttf";	

	public static String TIME_ZONE="GMT-4";	
	
	public static Long getCurrentTime(){
		return Calendar.getInstance(TimeZone.getTimeZone(App.TIME_ZONE)).getTimeInMillis();
	}
	public static Date getCurrentDate(){
		return Calendar.getInstance(TimeZone.getTimeZone(App.TIME_ZONE)).getTime();
	}
}

