package com.cronossf.iknowus.service;

import java.util.ArrayList;
import java.util.List;



import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.utils.Functions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Services {

	private Context context;
	private Gson builder;

	public Services(Context context) {
		this.context = context;
		builder = new GsonBuilder().setDateFormat("MMM dd,yyyy").create();
	}

	@SuppressWarnings("deprecation")
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

}
