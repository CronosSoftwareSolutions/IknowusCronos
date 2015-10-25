package com.cronossf.iknowus.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CustomTypeface {
	public static void setTypeFace(Typeface typeFace, ViewGroup parent) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			View v = parent.getChildAt(i);
			if (v instanceof ViewGroup) {
				setTypeFace(typeFace, (ViewGroup) v);
			} else if (v instanceof TextView) {
				TextView tv = (TextView) v;
				tv.setTypeface(typeFace);
				tv.setPaintFlags(tv.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} else if (v instanceof Button) {
				Button btn = (Button) v;
				btn.setTypeface(typeFace);
				btn.setPaintFlags(btn.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} else if (v instanceof EditText) {
				EditText et = (EditText) v;
				et.setTypeface(typeFace);
				et.setPaintFlags(et.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} else if (v instanceof CheckBox) {
				CheckBox cb = (CheckBox) v;
				cb.setTypeface(typeFace);
				cb.setPaintFlags(cb.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} else if (v instanceof RadioButton) {
				RadioButton rb = (RadioButton) v;
				rb.setTypeface(typeFace);
				rb.setPaintFlags(rb.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} else if (v instanceof CheckedTextView) {
				CheckedTextView ctv = (CheckedTextView) v;
				ctv.setTypeface(typeFace);
				ctv.setPaintFlags(ctv.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			} 
		}
	}
}
