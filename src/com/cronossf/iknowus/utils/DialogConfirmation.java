package com.cronossf.iknowus.utils;

import com.cronossf.iknowus.App;
import com.cronossf.iknowus.R;

import android.R.style;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogConfirmation extends Dialog {

	public static final int ACEPT_CANCEL = 0;
	public static final int YES_NO = 1;
	public static final int ALERT = 2;
	public static final int DELETE = 3;
	public static final int SAVE = 4;
	public static final int UPDATE = 5;

	// private Activity context;
	public Dialog dialog;

	private TextView tvTitleExit;
	private TextView tvDescriptionExit;
	private Button btnNegative;
	private Button btnPositive;
	private View vSeparatorButtons;

	public DialogConfirmation(Activity context, int type, String title, String description) {
		super(context, style.Theme_DeviceDefault_Dialog_MinWidth);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.context=context;
		dialog = this;
		setContentView(R.layout.dialog_confirmation);
		setCanceledOnTouchOutside(false);

		tvTitleExit = (TextView) findViewById(R.id.tvConfirmationTitle);
		tvDescriptionExit = (TextView) findViewById(R.id.tvConfirmationDescription);
		btnPositive = (Button) findViewById(R.id.btnConfirmationPositive);
		btnNegative = (Button) findViewById(R.id.btnConfirmationCancel);
		vSeparatorButtons = findViewById(R.id.vSeparatorButtons);

		tvTitleExit.setText(title);
		tvDescriptionExit.setText(description);
		if (type == ACEPT_CANCEL) {
			btnPositive.setText("Aceptar");
			btnNegative.setText("Cancelar");
		} else if (type == UPDATE) {
			btnPositive.setText("Actualizar");
			btnNegative.setText("Cancelar");
		} else if (type == YES_NO) {
			btnPositive.setText("Si");
			btnNegative.setText("No");
		} else if (type == ALERT) {
			btnPositive.setText("Aceptar");
			btnNegative.setVisibility(View.GONE);
			vSeparatorButtons.setVisibility(View.GONE); 
			btnPositive.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}else if (type == DELETE) {
			btnPositive.setText("Eliminar");
			btnNegative.setText("Cancelar");
		} else if (type == SAVE) {
			btnPositive.setText("Guardar");
			btnNegative.setText("Cancelar");
		}	
		
		btnNegative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		ViewGroup vg = (ViewGroup) getWindow().getDecorView();
		Functions.applyTypeFaceViewGroup(context, vg, App.FONT_REGULAR);
	}

	public DialogConfirmation(Activity context, int type) {
		super(context, style.Theme_DeviceDefault_Dialog_MinWidth);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.context=context;
		dialog = this;
		setContentView(R.layout.dialog_confirmation);

		tvTitleExit = (TextView) findViewById(R.id.tvConfirmationTitle);
		tvDescriptionExit = (TextView) findViewById(R.id.tvConfirmationDescription);
		btnPositive = (Button) findViewById(R.id.btnConfirmationPositive);
		btnNegative = (Button) findViewById(R.id.btnConfirmationCancel);

		tvTitleExit.setText("Titulo");
		tvDescriptionExit.setText("Descripcion");
		if (type == ACEPT_CANCEL) {
			btnPositive.setText("Aceptar");
			btnNegative.setText("Cancelar");
		} else if (type == YES_NO) {
			btnPositive.setText("Si");
			btnNegative.setText("No");
		} else if (type == ALERT) {
			btnPositive.setText("Aceptar");
			btnNegative.setVisibility(View.GONE);
		}else if (type == DELETE) {
			btnPositive.setText("Eliminar");
			btnNegative.setText("Cancelar");
		} else if (type == SAVE) {
			btnPositive.setText("Guardar");
			btnNegative.setText("Cancelar");
		}		

		btnNegative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setTitle(String title) {
		tvTitleExit.setText(title);
	}

	public void setDescription(String description) {
		tvDescriptionExit.setText(description);
	}

	public void setButtonsText(String positiveOption, String negativeOption) {
		btnPositive.setText(positiveOption);
		btnNegative.setText(negativeOption);
	}

	public void setOnClickPositiveButton(android.view.View.OnClickListener listener) {
		btnPositive.setOnClickListener(listener);
	}

	/**
	 * Set the action when the user push the btnNegative, default action call method dismiss().
	 * 
	 * @param listener
	 */
	public void setOnClickNegativeButton(android.view.View.OnClickListener listener) {
		btnNegative.setOnClickListener(listener);
	}

}
