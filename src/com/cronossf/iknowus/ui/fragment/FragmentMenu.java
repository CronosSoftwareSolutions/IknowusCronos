package com.cronossf.iknowus.ui.fragment;

import com.androidquery.AQuery;
import com.cronossf.iknowus.App;
import com.cronossf.iknowus.PreferenceManager;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.entity.Usuario;
import com.cronossf.iknowus.ui.activity.ActivityArea;
import com.cronossf.iknowus.ui.activity.ActivityArticle;
import com.cronossf.iknowus.ui.activity.ActivityCalendar;
import com.cronossf.iknowus.ui.activity.ActivityFavorites;
import com.cronossf.iknowus.ui.activity.ActivityForum;
import com.cronossf.iknowus.ui.activity.ActivityMain;
import com.cronossf.iknowus.ui.activity.ActivityPersons;
import com.cronossf.iknowus.ui.activity.ActivityProfile;
import com.cronossf.iknowus.ui.activity.ActivitySplash;
import com.cronossf.iknowus.utils.DialogConfirmation;
import com.cronossf.iknowus.utils.Functions;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentMenu extends Fragment{

	private ActivityMain activity;
	private AQuery aq;
	private PreferenceManager prefs;
	private Usuario user;
	private ImageView ivPhotoUser;
	private TextView tvMenuPlace;
	private TextView tvMenuCompany;
	private TextView tvMenuNameUser;
	
	public static FragmentMenu static_;
		
	
	public static FragmentMenu newInstance(ActivityMain activity, AQuery aq) {
		FragmentMenu fragment = new FragmentMenu();
		fragment.activity = activity;
		fragment.aq = aq;
		fragment.prefs = new PreferenceManager(activity);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (ActivityMain)getActivity();
		aq = new AQuery(activity.getApplicationContext());
		prefs = new PreferenceManager(activity);
		static_ = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		activity = (ActivityMain)getActivity();
		aq = new AQuery(activity.getApplicationContext());
		prefs = new PreferenceManager(activity);

		View view = inflater.inflate(R.layout.fragment_menu, container, false);		
		LinearLayout lyItemMenuMyProfile = (LinearLayout) view.findViewById(R.id.lyItemMenuMyProfile);
		LinearLayout lyItemMenuArticle = (LinearLayout) view.findViewById(R.id.lyItemMenuArticle);
		LinearLayout lyItemMenuForum = (LinearLayout) view.findViewById(R.id.lyItemMenuForum);
		LinearLayout lyItemMenuFavorites = (LinearLayout) view.findViewById(R.id.lyItemMenuFavorites);
		LinearLayout lyItemMenuArea = (LinearLayout) view.findViewById(R.id.lyItemMenuArea);
		LinearLayout lyItemMenuNotifications = (LinearLayout) view.findViewById(R.id.lyItemMenuNotifications);				
		LinearLayout lyItemMenuCalendar = (LinearLayout) view.findViewById(R.id.lyItemMenuCalendar);
		LinearLayout lyItemMenuPersons = (LinearLayout) view.findViewById(R.id.lyItemMenuPersons);
		LinearLayout lyItemMenuCloseSession = (LinearLayout) view.findViewById(R.id.lyItemMenuCloseSession);				

		ivPhotoUser = (ImageView) view.findViewById(R.id.ivPhotoUser);
		tvMenuNameUser = (TextView) view.findViewById(R.id.tvMenuNameUser);		
		
		tvMenuPlace = (TextView) view.findViewById(R.id.tvMenuPlace);		
		tvMenuCompany = (TextView) view.findViewById(R.id.tvMenuCompany);

		user = prefs.getUser();		
		if(Functions.isValidString(user.getImagen())){
			aq.id(ivPhotoUser).image(App.SERVER_IMAGEN + user.getImagen(), false, true, 500, 0);
		}	 
		tvMenuNameUser.setText(user.getNombre()); 	
	
		lyItemMenuMyProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				activity.onClickMenu(null);
//				activity.startActivity(new Intent(activity, ActivityProfile.class));			  			
			}
		});
		
		lyItemMenuArticle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
					activity.onClickMenu(null);
					activity.startActivity(new Intent(activity, ActivityArticle.class));
				
			}
		});		

		lyItemMenuForum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
				activity.startActivity(new Intent(activity, ActivityForum.class));
			}
		});		
		
		lyItemMenuFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
//				activity.startActivity(new Intent(activity, ActivityFavorites.class));
			}
		});
		
		lyItemMenuArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
//				startActivity(new Intent(activity, ActivityArea.class)); 
			}
		});

		lyItemMenuNotifications.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
//				activity.startActivity(new Intent(activity, ActivityNotFoundException.class));
			}
		});				
		
		lyItemMenuCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
				startActivity(new Intent(activity, ActivityCalendar.class)); 
			}
		});
		
		lyItemMenuPersons.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.onClickMenu(null);
//				startActivity(new Intent(activity, ActivityPersons.class)); 
			}
		});
		
		lyItemMenuCloseSession.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				activity.onClickMenu(null);
				final DialogConfirmation dc = new DialogConfirmation(activity, DialogConfirmation.YES_NO, "Cerrar sesion", "Esta seguro de cerrar sesion? ");
				dc.setOnClickPositiveButton(new OnClickListener() {					
					@Override
					public void onClick(View v) {						
						prefs.setUser(null);						
						startActivity(new Intent(activity, ActivitySplash.class));
						dc.cancel();
						activity.finish();						
					}
				});
				dc.show();
			}
		});

		Functions.applyTypeFaceViewGroup(activity, (RelativeLayout) view, App.FONT_REGULAR);

		return view;
	}

}
