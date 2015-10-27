package com.cronossf.iknowus.ui.dialog;

import android.R.style;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.R;
import com.cronossf.iknowus.components.FloatingEditText;
import com.cronossf.iknowus.ui.activity.ActivityDetail.OnComment;
import com.cronossf.iknowus.utils.Functions;

public class DialogComment extends Dialog {

	private FloatingEditText etSearchMyPublications;
	private Button btnSearchMyPubCancel;
	private Button btnSearchMyPubSearch;
	private TextView tvTitle;

	private Activity activity;
	private String text;
	private OnComment onComment;

	public DialogComment(Activity activity, String text, OnComment onComment) {
		super(activity, style.Theme_DeviceDefault_Dialog_MinWidth);
		this.activity = activity;
		this.text = text;
		this.onComment = onComment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_search_my_publications);
		setCancelable(true);

		initComponents();
	}

	private void initComponents() {
		etSearchMyPublications = (FloatingEditText) findViewById(R.id.etSearchMyPublications);
		btnSearchMyPubCancel = (Button) findViewById(R.id.btnSearchMyPubCancel);
		btnSearchMyPubSearch = (Button) findViewById(R.id.btnSearchMyPubSearch);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Comentario");
		btnSearchMyPubSearch.setText("Comentar"); 
		etSearchMyPublications.setText(text);
		btnSearchMyPubSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String new_text = Functions.trim(etSearchMyPublications.getText().toString());
				etSearchMyPublications.setText(new_text);
				if (new_text.length() > 2) {
					onComment.onComment(new_text); 
					InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(etSearchMyPublications.getWindowToken(), 0);
					cancel();
				} else {
					etSearchMyPublications.setValidateResult(false, "comentarios con al menos 3 caracteres");
				}				
			}
		});
		btnSearchMyPubCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(etSearchMyPublications.getWindowToken(), 0);
				cancel();
			}
		});

		ViewGroup vg = (ViewGroup) getWindow().getDecorView();
		Functions.applyTypeFaceViewGroup(activity, vg, App.FONT_BOLD);
	}

}