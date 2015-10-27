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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityForum extends Activity {
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
			ActivityForum.this.lstPublications = lstPublications;
			lvPublications.setAdapter(new AdapterPublications(ActivityForum.this, lstPublications, false));
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
		tvTitlePublications.setText("Foros"); 
		
		new TaskLoadLastPublications(this, lstPublications, "foros", filter, onComplete).execute();
	}

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
				ActivityForum.this.filter = filter;
				lstPublications.clear();
				lvPublications.setAdapter(new AdapterPublications(ActivityForum.this, lstPublications, false));
				new TaskLoadLastPublications(ActivityForum.this, lstPublications, "foros", filter, onComplete).execute();
			}
		};
		new DialogSearchMyPublication(this, filter, ons).show();
	}
	
	public void deleteSearch(View view){
		ibtnTextSearchDelete.setVisibility(View.GONE);
		filter = "x";
		lstPublications.clear();
		lvPublications.setAdapter(new AdapterPublications(ActivityForum.this, lstPublications, false));
		new TaskLoadLastPublications(this, lstPublications, "foros", filter, onComplete).execute();
	}
	
	public void back(View view) {
		finish();
	}
}
