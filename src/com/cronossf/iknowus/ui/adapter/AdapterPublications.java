package com.cronossf.iknowus.ui.adapter;

import java.util.List;

import com.androidquery.AQuery;
import com.cronossf.iknowus.App;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.components.CircularImageView;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.ui.activity.ActivityProfile;
import com.cronossf.iknowus.utils.Functions;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterPublications extends BaseAdapter {
	private List<Publicacion> lstPublicaciones;
	private AQuery aq;
	private Activity activity;

	public AdapterPublications(Activity activity, List<Publicacion> lstPublicaciones) {
		this.activity = activity;
		this.lstPublicaciones = lstPublicaciones;
		aq = new AQuery(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return lstPublicaciones.size();
	}

	@Override
	public Object getItem(int position) {
		return lstPublicaciones.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Publicacion publicacion = lstPublicaciones.get(position);
		String autores = "";
		for (Usuario user : publicacion.getLstAutores()) {
			autores += (user.getNombre() + user.getApellido());
		}
		switch (publicacion.getTipo()) {
		case 1:
			convertView = activity.getLayoutInflater().inflate(R.layout.item_article, null);
			CircularImageView ivPub = (CircularImageView) convertView.findViewById(R.id.ivPub);
			TextView tvTitlePub = (TextView) convertView.findViewById(R.id.tvTitlePub);
			TextView tvNameAutPub = (TextView) convertView.findViewById(R.id.tvNameAut);
			TextView tvDescPub = (TextView) convertView.findViewById(R.id.tvDesc);
			
			aq.id(ivPub).image(App.SERVER_IMAGEN + publicacion.getImagen());
			tvTitlePub.setText(publicacion.getTitulo());
			tvDescPub.setText(publicacion.getDescripcion());						
			tvNameAutPub.setText(autores);
			break;
		case 2:
			convertView = activity.getLayoutInflater().inflate(R.layout.item_forum, null);
			TextView tvTitleForum = (TextView) convertView.findViewById(R.id.tvTitleForum);
			TextView tvNameAut = (TextView) convertView.findViewById(R.id.tvNameAut);
			TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			tvTitleForum.setText(publicacion.getTitulo());
			tvDesc.setText(publicacion.getDescripcion());
			tvDate.setText(Functions.getFormattedDate(publicacion.getFecha_publicacion()));			
			tvNameAut.setText(autores);
			break;
		case 3:
			convertView = activity.getLayoutInflater().inflate(R.layout.item_calendar, null);
			TextView vtMonth = (TextView) convertView.findViewById(R.id.vtMonth);
			TextView btnDay = (Button) convertView.findViewById(R.id.btnDay);
			TextView vtEvent = (TextView) convertView.findViewById(R.id.vtEvent);
			if (position == 0 || Functions.getMonthInt(lstPublicaciones.get(position - 1).getFecha_referencia()) != Functions.getMonthInt(publicacion.getFecha_referencia())) {
				vtMonth.setVisibility(View.VISIBLE);
				vtMonth.setText(Functions.getMonth(publicacion.getFecha_referencia()));
			}			
			btnDay.setText(Functions.getDay(publicacion.getFecha_referencia()));
			vtEvent.setText(publicacion.getTitulo());
			break;
		default:
			break;
		}
		convertView.setTag(publicacion); 
		return convertView;
	}

}
