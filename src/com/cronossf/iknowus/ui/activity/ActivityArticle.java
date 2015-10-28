package com.cronossf.iknowus.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.task.TaskLoadLastPublications;
import com.cronossf.iknowus.task.TaskLoadLastPublications.OnComplete;
import com.cronossf.iknowus.ui.adapter.AdapterPublications;
import com.cronossf.iknowus.ui.dialog.DialogLogin;
import com.cronossf.iknowus.ui.dialog.DialogSearchMyPublication;
import com.cronossf.iknowus.utils.OnSearch;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityArticle extends Activity {

	private List<Publicacion> lstPublications = new ArrayList<Publicacion>();

	private TextView tvTitlePublications;
	private TextView tvWithoutPublications;
	private TextView tvTextSearch;
	private TextView tvTextCountResults;
	private ImageButton ibtnTextSearchDelete;
	private ImageButton ibtnTextSearchSearch;
	private ListView lvPublications;
	private ProgressBar pbPublications;
	public AQuery aq;
	private String filter = "x";
	public OnComplete onComplete = new OnComplete() {
		@Override
		public void onCompleted(List<Publicacion> lstPublications) {
			ActivityArticle.this.lstPublications = lstPublications;
			lvPublications.setAdapter(new AdapterPublications(ActivityArticle.this, lstPublications, false));
			pbPublications.setVisibility(View.GONE);
			if(!lstPublications.isEmpty())
				tvWithoutPublications.setVisibility(View.GONE); 
			tvTextCountResults.setText(lstPublications.size() +  " resultados.");
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publications);
		initComponents();
		tvTitlePublications.setText("Articulos"); 
		lvPublications.setOnItemClickListener(onitem); 
		new TaskLoadLastPublications(this, lstPublications, "articulos", filter, onComplete).execute();
	}
	
	OnItemClickListener onitem = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Publicacion p = (Publicacion) view.getTag();
			Intent intent = new Intent(ActivityArticle.this, ActivityDetail.class);
			intent.putExtra("tipo", p.getTipo());
			intent.putExtra("publicacion", new Gson().toJson(p)); 
			startActivity(intent);
		}
	};

	private void initComponents() {
		tvTitlePublications = (TextView) findViewById(R.id.tvTitlePublications);
		tvWithoutPublications = (TextView) findViewById(R.id.tvWithoutPublications);
		tvTextSearch = (TextView) findViewById(R.id.tvTextSearch);
		tvTextCountResults = (TextView) findViewById(R.id.tvTextCountResults);
		ibtnTextSearchDelete = (ImageButton) findViewById(R.id.ibtnTextSearchDelete);
		ibtnTextSearchSearch = (ImageButton) findViewById(R.id.ibtnTextSearchSearch);
		lvPublications = (ListView) findViewById(R.id.lvPublications);
		pbPublications = (ProgressBar) findViewById(R.id.pbPublications);
	}

	public void search(View view){
		OnSearch ons = new OnSearch() {			
			@Override
			public void onSearch(String filter) {
				ibtnTextSearchDelete.setVisibility(View.VISIBLE); 
				ActivityArticle.this.filter = filter;
				lstPublications.clear();
				lvPublications.setAdapter(new AdapterPublications(ActivityArticle.this, lstPublications, false));
				new TaskLoadLastPublications(ActivityArticle.this, lstPublications, "articulos", filter, onComplete).execute();
			}
		};
		new DialogSearchMyPublication(this, filter, ons).show();
	}
	
	public void deleteSearch(View view){
		ibtnTextSearchDelete.setVisibility(View.GONE);
		filter = "x";
		lstPublications.clear();
		lvPublications.setAdapter(new AdapterPublications(ActivityArticle.this, lstPublications, false));
		new TaskLoadLastPublications(this, lstPublications, "articulos", filter, onComplete).execute();
	}
	
	public void back(View view) {
		finish();
	}

}
