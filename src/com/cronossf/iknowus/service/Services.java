package com.cronossf.iknowus.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.entity.Asistencia;
import com.cronossf.iknowus.entity.Comentario;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.utils.Functions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("deprecation")
public class Services {

	private Context context;
	private Gson builder;

	public Services(Context context) {
		this.context = context;
		builder = new GsonBuilder().setDateFormat("MMM dd,yyyy").create();
	}

	public Usuario startSession(String user, String password) {
		Usuario result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient = Functions.getHttpsClient(httpClient);
			HttpPost httpPost = new HttpPost(App.WS + "/StartSession");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user", user));
			params.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				result = builder.fromJson(responseBody, Usuario.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Publicacion> getLastPublications(int tipo, String filter) {
		List<Publicacion> result = new ArrayList<Publicacion>(); 
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient = Functions.getHttpsClient(httpClient);
			HttpGet httpGet = new HttpGet(App.WS + "/GetLastPublications/" + tipo + "/" + filter);						
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				Type listType = new TypeToken<ArrayList<Publicacion>>() {}.getType();
				result = builder.fromJson(responseBody, listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Publicacion saveComment(String comentario, Long id_usuario, Long id_publicacion) {
		Publicacion result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient = Functions.getHttpsClient(httpClient);
			HttpPost httpPost = new HttpPost(App.WS + "/saveComment");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("comentario", comentario));
			params.add(new BasicNameValuePair("id_publicacion", id_publicacion.toString()));
			params.add(new BasicNameValuePair("id_usuario", id_usuario.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				result = builder.fromJson(responseBody, Publicacion.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public Publicacion saveAsistencia(String detalle, Long id_usuario, Long id_publicacion) {
		Publicacion result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient = Functions.getHttpsClient(httpClient);
			HttpPost httpPost = new HttpPost(App.WS + "/saveAsistencia");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("detalle", detalle));
			params.add(new BasicNameValuePair("id_publicacion", id_publicacion.toString()));
			params.add(new BasicNameValuePair("id_usuario", id_usuario.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				result = builder.fromJson(responseBody, Publicacion.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
