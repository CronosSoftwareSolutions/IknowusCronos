package com.cronossf.iknowus.task;

import java.util.List;

import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.service.Services;
import com.cronossf.iknowus.utils.DialogLoading;
import com.cronossf.iknowus.utils.Functions;

import android.app.Activity;
import android.os.AsyncTask;

public class TaskLoadLastPublications extends AsyncTask<String, String, String> {

	private Activity activity;
	private OnComplete onComplete;	
	private List<Publicacion> lstPublications;
	private String type;
	private String filter;

	public TaskLoadLastPublications(Activity activity, List<Publicacion> lstPublications, String type, String filter,OnComplete onComplete) {
		this.activity = activity;
		this.onComplete = onComplete;
		this.lstPublications = lstPublications;	
		this.type = type;
		this.filter = filter;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Services services = new Services(activity);
			if (Functions.isOnline(activity)) {
				if(type.equals("todos")){
					lstPublications = services.getLastPublications(0, filter);
				}else if(type.equals("articulos")){
					lstPublications = services.getLastPublications(1, filter);
				}else if(type.equals("foros")){
					lstPublications = services.getLastPublications(2, filter);
				}else if(type.equals("calendario")){
					lstPublications = services.getLastPublications(3, filter);
				}
				
				if (lstPublications != null) {
					return "OK";
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
		onComplete.onCompleted(lstPublications);
	}

	public interface OnComplete {
		public void onCompleted(List<Publicacion> publications);
	}

}