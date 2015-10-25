/*  File Version: 1.0
 *	Copyright, KENJI KAWAIDA, Hajimesoft.com
 *
 *	All rights reserved.
 *
 *	Redistribution and use in source and binary forms, with or without modification, are 
 *	permitted provided that the following conditions are met:
 *
 *	Redistributions of source code must retain the above copyright notice which includes the
 *	name(s) of the copyright holders. It must also retain this list of conditions and the 
 *	following disclaimer. 
 *
 *	Redistributions in binary form must reproduce the above copyright notice, this list 
 *	of conditions and the following disclaimer in the documentation and/or other materials 
 *	provided with the distribution. 
 *
 *	Neither the name of KENJI KAWAIDA, or Hajimesot.com nor the names of its contributors 
 *	may be used to endorse or promote products derived from this software without specific 
 *	prior written permission.
 *
 *	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 *	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 *	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 *	IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 *	INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 *	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 *	WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *	ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 *	OF SUCH DAMAGE. 
 */
package com.cronossf.iknowus.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cronossf.iknowus.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Functions {

	public static boolean isConnectedToWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connectivityManager != null) {
			networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

	public static boolean isConnectedToMobile(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connectivityManager != null) {
			networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

	public static boolean isConnected(Context context) {
		if (isConnectedToWifi(context)) {
			return true;
		} else {
			return isConnectedToMobile(context);
		}
	}

	public static boolean isBluetoothEnabled() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.isEnabled())
				return true;
		}
		return false;
	}

	public static void open_url(Context context, String url) {
		try {
			if (!url.contains("http")) {
				url = "http://" + url;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			context.startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isEnabledGPS(Context context) {
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static String getImei(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static double getValue(String str) {
		double value = 0;
		if (str != null && !str.isEmpty()) {
			str = str.replace(",", ".");
			value = Double.parseDouble(str);
		}
		return value;
	}

	/**
	 * Metodo que retorna si hay conexion a internet.
	 * 
	 * @param context
	 *            es el contexto de donde se hace la llamada al metodo.
	 * @return true o false si hubiera o no conectividad a internet.
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public static String formatDouble(double dbl) {
		String doubleString = "";
		DecimalFormat format = new DecimalFormat("#.00");
		doubleString = format.format(Math.round(dbl * 100) / 100d);
		if (dbl < 1) {
			doubleString = "0" + doubleString;
		}
		return doubleString;
	}

	public static String formatMilSeparator(Long value) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		return formatter.format(value);
	}

	public static String formatMilSeparator(Double value) {
		// DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		// DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		//
		// symbols.setGroupingSeparator(' ');
		// return formatter.format(value);

		DecimalFormat format = new DecimalFormat();
		DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
		customSymbols.setGroupingSeparator('.');
		format.setDecimalFormatSymbols(customSymbols);
		return format.format(value);
	}

	public static boolean isValidString(String content) {
		return content != null && !content.isEmpty() && !content.equals("null");
	}
	

	public static boolean isValidUserName(String cadena) {
		Pattern pat = Pattern.compile("[a-zA-Z0-9_.-]{0,25}");
		Matcher mat = pat.matcher(cadena);
		return mat.matches();
	}

	public static boolean isValidName(String cadena) {
		Pattern pat = Pattern.compile("[a-zA-ZñÑ áéíóú]{0,50}");
		Matcher mat = pat.matcher(cadena);
		return mat.matches();
	}

	// convert from internal Java String format -> UTF-8
	public static String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	public static String trim(String texto) {
		java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
		StringBuilder buff = new StringBuilder();
		while (tokens.hasMoreTokens()) {
			buff.append(" ").append(tokens.nextToken());
		}
		return buff.toString().trim();
	}

	// public static String trim(String text){
	// for(int i=0;i<text.length();i++){
	// if(text.charAt(i)==' '){
	// text = text.substring(i);
	// continue;
	// }
	// }
	//
	// for(int i=text.length()-1;i>=0;i--){
	// if(text.charAt(i)==' '){
	// text = text.substring(0,i);
	// continue;
	// }
	// }
	// return text;
	// }

	public static void applyTypeFace(Activity activity, String pathTypeface) {
		ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
		CustomTypeface.setTypeFace(Typeface.createFromAsset(activity.getAssets(), pathTypeface), vg);
	}

	public static void applyTypeFaceViewGroup(Activity activity, ViewGroup vg, String pathTypeface) {
		CustomTypeface.setTypeFace(Typeface.createFromAsset(activity.getAssets(), pathTypeface), vg);
	}

	public static void applyTypeFace(View v, Context context, String pathTypeface) {
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(), pathTypeface);

		if (v instanceof TextView) {
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

	public static void applyTypeFace(Context context, String pathTypeface, View... views) {
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(), pathTypeface);
		for (View v : views) {
			if (v instanceof TextView) {
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


	public static String getNameFile(String pathImage) {
		String[] parts = pathImage.split("/");
		if (parts.length > 0) {
			return parts[parts.length - 1];
		} else {
			return null;
		}
	}	

	public static String getUrlThumbnailYoutube(String urlYoutube) {
		try {
			if (isValidString(urlYoutube) && (urlYoutube.contains("www.youtube.com/watch?v=") || urlYoutube.contains("youtu.be"))) {
				String[] val = urlYoutube.contains("youtu.be") ? urlYoutube.split("/") : urlYoutube.split("=");
				String codev = urlYoutube.contains("youtu.be") ? val[val.length - 1] : val[1];
				String videoThumbnail = "http://img.youtube.com/vi/" + codev + "/default.jpg";
				return videoThumbnail;
			}
		} catch (Exception e) {
			Log.e(App.TAG, "Error al obtener la miniatura de youtube " + urlYoutube + " " + e.getMessage());
		}
		return null;
	}
	

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validate given email with regular expression.
	 * 
	 * @param email
	 *            email for validation
	 * @return true valid email, otherwise false
	 */
	public static boolean validateEmail(String email) {
		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	/**
	 * Get the current line number.
	 * 
	 * @return int - Current line number.
	 */
	public static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}

	public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public static String getClassName() {
		return Thread.currentThread().getStackTrace()[2].getFileName();
	}

	public static HttpClient getHttpsClient(HttpClient client) {
		try {
			X509TrustManager x509TrustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { x509TrustManager }, null);
			SSLSocketFactory sslSocketFactory = new SocketFactory(sslContext);
			sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager clientConnectionManager = client.getConnectionManager();
			SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
			schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
			return new DefaultHttpClient(clientConnectionManager, client.getParams());
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("resource")
	public static void backup(Activity context) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "//data//" + context.getApplicationContext().getPackageName() + "//databases//database.db";
				String backupDBPath = "history" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()) + ".db";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(backupDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
				Toast.makeText(context.getBaseContext(), "Backup realizado", Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(context.getBaseContext(), "Backup no realizado", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context.getBaseContext(), "Backup no realizado", Toast.LENGTH_LONG).show();

		}
	}

	public static boolean isSDCardPresent() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
}
