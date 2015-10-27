package com.cronossf.iknowus.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.cronossf.iknowus.App;
import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.entity.Asistencia;
import com.cronossf.iknowus.entity.Comentario;
import com.cronossf.iknowus.entity.DetallePublicacion;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.task.TaskSaveAsistencia;
import com.cronossf.iknowus.task.TaskSaveAsistencia.OnComplete;
import com.cronossf.iknowus.task.TaskSaveComment;
import com.cronossf.iknowus.ui.dialog.DialogComment;
import com.cronossf.iknowus.ui.dialog.DialogLogin;
import com.cronossf.iknowus.utils.Functions;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityDetail extends Activity {

	private Publicacion publication;
	private TextView tvDetailTitle;
	private TextView tvDetailDescription;
	private ImageView ivDetailImage;
	private LinearLayout lyDetailPublication;
	private LinearLayout lyDetailComments;

	private Button btnComment;
	private Button btnAsist;
	private ImageView ivTypoPublication;

	private int tipo = 1;

	private AQuery aq;
	private Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		usuario = new PreferenceManager(this).getUser();
		aq = new AQuery(getApplicationContext());
		tipo = getIntent().getExtras().getInt("tipo");
		publication = new Gson().fromJson(getIntent().getExtras().getString("publicacion"), Publicacion.class);
		initComponents();
		tvDetailTitle.setText(publication.getTitulo());
		tvDetailDescription.setText(tvDetailDescription.getText().toString() + publication.getDescripcion());
		aq.id(ivDetailImage).image(App.SERVER_IMAGEN + publication.getImagen(), true, true);
		fillPublicationDetail();
		fillPublicationComments();
		validateAsist();		
	}

	private void initComponents() {
		tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
		tvDetailDescription = (TextView) findViewById(R.id.tvDetailDescription);
		ivDetailImage = (ImageView) findViewById(R.id.ivDetailImage);
		lyDetailPublication = (LinearLayout) findViewById(R.id.lyDetailPublication);
		lyDetailComments = (LinearLayout) findViewById(R.id.lyDetailComments);
		btnComment = (Button) findViewById(R.id.btnComment);
		btnAsist = (Button) findViewById(R.id.btnAsist);
		ivTypoPublication = (ImageView) findViewById(R.id.ivTypoPublication);
		if (tipo == 1) {// Articulo
			btnAsist.setVisibility(View.GONE);
			ivTypoPublication.setBackgroundResource(R.drawable.ic_articulo);
		} else if (tipo == 2) {// Foro
			btnAsist.setVisibility(View.GONE);
			ivTypoPublication.setBackgroundResource(R.drawable.ic_foro);
		} else if (tipo == 3) {// Calendario
			tvDetailDescription.setText(publication.getLstAsistentes().size() + " Asistirán - ");
			ivTypoPublication.setBackgroundResource(R.drawable.ic_calendar);
		}

		btnComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DialogComment(ActivityDetail.this, "", onComment).show();								
			}
		});
		
		btnAsist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OnComplete onComplete = new OnComplete() {
					@Override
					public void onCompleted(Publicacion result) {
						publication = result;
						lyDetailComments.removeAllViews();
						lyDetailPublication.removeAllViews();
						fillPublicationDetail();
						fillPublicationComments();
						validateAsist();
					}
				};
				new TaskSaveAsistencia(ActivityDetail.this, "Asistire", publication.getId(), usuario.getId(), onComplete).execute();
			}
		});
	}
	
	OnComment onComment = new OnComment() {		
		@Override
		public void onComment(String text) {
			TaskSaveComment.OnComplete onComplete = new TaskSaveComment.OnComplete() {
				@Override
				public void onCompleted(Publicacion result) {
					publication = result;
					lyDetailComments.removeAllViews();
					lyDetailPublication.removeAllViews();
					fillPublicationDetail();
					fillPublicationComments();
				}
			};
			new TaskSaveComment(ActivityDetail.this, text, publication.getId(), usuario.getId(), onComplete).execute();
		}
	};
	public interface OnComment{
		public void onComment(String text);
	}

	public void fillPublicationDetail() {
		for (int i = 0; i < publication.getLstDetallePublicacion().size(); i++) {
			DetallePublicacion hdt = publication.getLstDetallePublicacion().get(i);
			View view = getLayoutInflater().inflate(R.layout.item_help_principal, null, true);
			TextView tvHelpDetailTitle = (TextView) view.findViewById(R.id.tvHelpDetailTitle);
			TextView tvHelpDetailDescription = (TextView) view.findViewById(R.id.tvHelpDetailDescription);
			ImageView ivHelpDetailImage = (ImageView) view.findViewById(R.id.ivHelpDetailImage);
			ImageView ivHelpDetailVideo = (ImageView) view.findViewById(R.id.ivHelpDetailVideo);
			RelativeLayout lyHelpDetailVideo = (RelativeLayout) view.findViewById(R.id.lyHelpDetailVideo);
			tvHelpDetailTitle.setText(hdt.getTitulo());
			tvHelpDetailDescription.setText(hdt.getTexto());
			if (Functions.isValidString(hdt.getImagen())) {
				aq.id(ivHelpDetailImage).image(App.SERVER_IMAGEN + hdt.getImagen(), true, true);
			}
			if (Functions.isValidString(hdt.getVideo())) {
				lyHelpDetailVideo.setVisibility(View.VISIBLE);
				aq.id(ivHelpDetailVideo).image(Functions.getUrlThumbnailYoutube(hdt.getVideo()), true, true);
				ivHelpDetailVideo.setTag(hdt.getVideo());
				ivHelpDetailVideo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(v.getTag().toString())));
					}
				});
			}
			lyDetailPublication.addView(view);
		}

	}

	private void fillPublicationComments() {
		for (Comentario comentario : publication.getLstComentarios()) {
			View view = getLayoutInflater().inflate(R.layout.item_comment, null, true);
			TextView tvComment = (TextView) view.findViewById(R.id.tvComment);
			TextView tvDateComment = (TextView) view.findViewById(R.id.tvDateComment);
			TextView tvNameComment = (TextView) view.findViewById(R.id.tvNameComment);
			tvNameComment.setText(comentario.getUsuario().getNombre());
			String c = comentario.getTitulo();
			if (comentario.getDescripcion() != null)
				c += ("" + comentario.getDescripcion());
			tvComment.setText(comentario.getTitulo());
			tvDateComment.setText(Functions.getFormattedDate(comentario.getFecha()));
			lyDetailComments.addView(view);
		}
	}

	private void validateAsist() {
		if (tipo == 3) {
			for (Asistencia asist : publication.getLstAsistentes()) {
				if (asist!=null && asist.getId_usuario().equals(usuario.getId())) {
					btnAsist.setVisibility(View.GONE);
					break;
				}
			}
		}
	}
}
