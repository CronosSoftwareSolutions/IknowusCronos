package com.cronossf.iknowus.task;

import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.entity.Asistencia;
import com.cronossf.iknowus.entity.Comentario;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.service.Services;
import com.cronossf.iknowus.utils.DialogLoading;
import com.cronossf.iknowus.utils.Functions;

import android.app.Activity;
import android.os.AsyncTask;

public class TaskSaveAsistencia extends AsyncTask<String, String, String> {

	private DialogLoading dialog;
	private Activity activity;
	private OnComplete onComplete;

	private String text;
	private Long id_publicacion;
	private Long id_usuario;
	private Publicacion publicacion;

	public TaskSaveAsistencia(Activity activity, String text, Long id_publicacion, Long id_usuario, OnComplete onComplete) {
		this.activity = activity;
		this.onComplete = onComplete;
		this.text = text;
		this.id_usuario = id_usuario;
		this.id_publicacion = id_publicacion;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new DialogLoading(activity, "Guardando tu asistencia", "Se esta guardando tu asistencia, esto podria demorar unos segundos...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Services services = new Services(activity);
			if (Functions.isOnline(activity)) {
				publicacion = services.saveAsistencia(text, id_usuario, id_publicacion);
				if (publicacion != null && publicacion.getId() != null && publicacion.getId() > 0) {
					return "OK";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	@Override
	protected void onPostExecute(String result) {		
		onComplete.onCompleted(publicacion);
		dialog.cancel();
	}

	public interface OnComplete {
		public void onCompleted(Publicacion result);
	}

}