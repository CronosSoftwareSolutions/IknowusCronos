package com.cronossf.iknowus.ui.activity;

import static android.view.Gravity.START;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.components.DrawerArrowDrawable;
import com.cronossf.iknowus.entity.Publicacion;
import com.cronossf.iknowus.task.TaskLoadLastPublications;
import com.cronossf.iknowus.task.TaskLoadLastPublications.OnComplete;
import com.cronossf.iknowus.ui.adapter.AdapterPublications;
import com.cronossf.iknowus.ui.fragment.FragmentMenu;
import com.cronossf.iknowus.utils.DialogConfirmation;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityMain extends Activity {
	/*
	 * navigation drawer
	 */
	private DrawerLayout mDrawerLayout;
	private DrawerArrowDrawable drawerArrowDrawable;
	private float offset;
	private boolean flipped;
	private ImageView ivMenuIcon;
	private LinearLayout lyLeftMenu;

	private ListView lvPrincipal;
	private ProgressBar pbPrincipal;
	public AQuery aq;
	private List<Publicacion> lstPublications = new ArrayList<Publicacion>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		lyLeftMenu = (LinearLayout) findViewById(R.id.lyLeftMenu);
		ivMenuIcon = (ImageView) findViewById(R.id.ivMenuIcon);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lvPrincipal = (ListView) findViewById(R.id.lvPrincipal);
		pbPrincipal = (ProgressBar) findViewById(R.id.pbPrincipal);
		Resources resources = getResources();
		drawerArrowDrawable = new DrawerArrowDrawable(resources);
		drawerArrowDrawable.setStrokeColor(resources.getColor(android.R.color.white));
		loadMenuFragment();
		OnComplete onComplete = new OnComplete() {
			@Override
			public void onCompleted(List<Publicacion> lstPublications) {
				ActivityMain.this.lstPublications = lstPublications;
				lvPrincipal.setAdapter(new AdapterPublications(ActivityMain.this, lstPublications, true));
				pbPrincipal.setVisibility(View.GONE);
			}
		};
		new TaskLoadLastPublications(this, lstPublications, "todos", "x",onComplete).execute();
		lvPrincipal.setOnItemClickListener(onitem); 
	}
	
	OnItemClickListener onitem = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Publicacion p = (Publicacion) view.getTag();
			Intent intent = new Intent(ActivityMain.this, ActivityDetail.class);
			intent.putExtra("tipo", p.getTipo());
			intent.putExtra("publicacion", new Gson().toJson(p)); 
			startActivity(intent);
		}
	};

	private void loadMenuFragment() {
		lyLeftMenu.destroyDrawingCache();
		getFragmentManager().beginTransaction().replace(R.id.lyLeftMenu, FragmentMenu.newInstance(this, aq)).commit();
		mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				offset = slideOffset;
				// Sometimes slideOffset ends up so close to but not quite 1 or 0.
				if (slideOffset >= .995) {
					flipped = true;
					drawerArrowDrawable.setFlip(flipped);
				} else if (slideOffset <= .005) {
					flipped = false;
					drawerArrowDrawable.setFlip(flipped);
				}
				drawerArrowDrawable.setParameter(offset);
			}
		});
		ivMenuIcon.setImageDrawable(drawerArrowDrawable);
	}

	public void onClickMenu(View view) {
		if (mDrawerLayout.isDrawerVisible(START)) {
			mDrawerLayout.closeDrawer(START);
		} else {
			mDrawerLayout.openDrawer(START);
		}
	}

	public void hideMenu() {
		if (mDrawerLayout.isDrawerVisible(START)) {
			mDrawerLayout.closeDrawer(START);
		}
	}

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerVisible(START)) {
			mDrawerLayout.closeDrawer(START);
		} else {
			final DialogConfirmation dialog = new DialogConfirmation(this, DialogConfirmation.YES_NO, "Salir de Iknowus", "Seguro que desea salir de la aplicacion?");
			dialog.setOnClickPositiveButton(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					finish();
				}
			});

			dialog.setOnClickNegativeButton(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}
	
	
}
