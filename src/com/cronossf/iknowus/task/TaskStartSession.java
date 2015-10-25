package com.cronossf.iknowus.task;

import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.service.Services;
import com.cronossf.iknowus.utils.DialogLoading;
import com.cronossf.iknowus.utils.Functions;

import android.app.Activity;
import android.os.AsyncTask;

public class TaskStartSession extends AsyncTask<String, String, String> {

	private DialogLoading dialog;
	private Activity activity;
	private OnStartSessionComplete onOnStartSessionComplete;
	private PreferenceManager prefs;
	private String user;
	private String password;

	public TaskStartSession(Activity activity, String user, String password, OnStartSessionComplete onOnStartSessionComplete) {
		this.activity = activity;
		this.onOnStartSessionComplete = onOnStartSessionComplete;
		this.user = user;
		this.password = password;
		prefs = new PreferenceManager(activity);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new DialogLoading(activity, "Iniciando Sesión", "Se esta validando sus datos de usuario, esto podria demorar unos segundos...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Services services = new Services(activity);
			if (Functions.isOnline(activity)) { 				
//				usuario.setApn_gcm(prefs.getRegistration_GCM_Id());
				Usuario result = services.startSession(user, password);
				if (result != null) {
					if (result.getId() != null && result.getId() > 0) {
						prefs.setUser(result);
						return "OK";
					} else {
						return "PASSWORD";
					}
				} else {
					return "PROBLEM";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		onOnStartSessionComplete.onStartSessionCompleted(result);
		dialog.cancel();
	}

	public interface OnStartSessionComplete {
		public void onStartSessionCompleted(String result);
	}

}