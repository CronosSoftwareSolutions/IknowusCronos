package com.cronossf.iknowus;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.cronossf.iknowus.entity.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PreferenceManager {
	private Context context;
	private Gson builder;

	public static final String PREFERENCE_GCM_NAME = "GCM_PREFERENCES";
	public static final String PROPERTY_REG_ID = "registration_id";
	public static final String PROPERTY_APP_VERSION = "appVersion";
	
	public static final String PREFERENCE_APP = "APP_PREFERENCES";
	public static final String PROPERTY_USER = "user";
	
	public PreferenceManager(Context context) {
		this.context = context;
		builder = new GsonBuilder().setDateFormat("MMM dd,yyyy HH:mm:ss").create();
	}

	private SharedPreferences getPreference(String preference_name) {
		return context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
	}

	private Editor getPreferenceEditor(String preference_name) {
		SharedPreferences prefs = getPreference(preference_name);
		return prefs.edit();
	}

	public String getRegistration_GCM_Id() {
		SharedPreferences prefs = getPreference(PREFERENCE_GCM_NAME);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(App.TAG, "No se encontro ningun registro.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(App.TAG, "App version cambiada.");
			return "";
		}
		return registrationId;
	}

	public void setRegistration_GCM_Id(Context context, String regId) {
		int appVersion = getAppVersion(context);
		Editor editor = getPreferenceEditor(PREFERENCE_GCM_NAME);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
	

	public Usuario getUser() {
		SharedPreferences prefs = getPreference(PREFERENCE_APP);
		String sUser = prefs.getString(PROPERTY_USER, "");		
		return sUser.isEmpty()?null:builder.fromJson(sUser, Usuario.class); 
	}

	public void setUser(Usuario user) {		
		Editor editor = getPreferenceEditor(PREFERENCE_APP);
		editor.putString(PROPERTY_USER, builder.toJson(user)); 
		editor.commit();
	}
	

	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static String getAppStringVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "0.0.1";
	}
}
