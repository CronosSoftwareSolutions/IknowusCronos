package com.iknowus.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

//import com.facebook.Request;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.model.GraphUser;
import com.google.gson.Gson;
import com.iknowus.App;
import com.iknowus.R;
import com.iknowus.entity.User;
import com.iknowus.utils.BajarFoto;

import org.json.JSONObject;

public class LoginActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ProgressDialog connectionProgressDialog;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient plusClient;
    private CallbackManager callbackManager;

    private boolean mIntentInProgress;
    private boolean flagSession = false;
    private LoginButton loginButton;

    private User appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        if(readUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        loginButton = (LoginButton) findViewById(R.id.login_button);
        connectionProgressDialog = new ProgressDialog(this);
        connectionProgressDialog.setCancelable(false);
        connectionProgressDialog.setMessage("Conectando...");

        loginButton.setReadPermissions("user_friends");
        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String personName = object.getString("name");
                            //String gender = object.getString("gender");
                            //String birthday = object.getString("birthday");
                            new BajarFoto(id).execute();
                            appUser = new User(id, personName, App.getPhotoCache() + id + ".jpg", "",
                                    "", "");
                            Log.i("Facebook", "El usuario de Facebook es : " + appUser.toString());
                            writeUser(appUser);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("Facebook","Login cancelado");
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void gmail(View view) {
        plusClient = new GoogleApiClient.Builder(LoginActivity.this)
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(LoginActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        plusClient.connect();
        connectionProgressDialog.show();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        connectionProgressDialog.dismiss();
        if (Plus.PeopleApi.getCurrentPerson(plusClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(plusClient);
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String birthdate = currentPerson.getBirthday();
            Integer g = currentPerson.getGender();
            String personGooglePlusProfile = currentPerson.getUrl();
            Toast.makeText(this, "Conectado! " + personName + " " + birthdate + " " + g + " " + personGooglePlusProfile + " " + currentPerson.getCurrentLocation() + " " + personPhoto,
                    Toast.LENGTH_LONG).show();
            appUser = new User(currentPerson.getId(), personName, personPhoto, g==0?"Male":"Female",
                    "", currentPerson.getBirthday());
            Log.i("Gmail", "El usuario de Gmail es: " + appUser.toString());
            writeUser(appUser);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
      try{
          connectionProgressDialog.dismiss();
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
//                plusClient.connect();
            }
        }
      }catch(Exception e){e.printStackTrace();}
    }

    public void onConnectionSuspended(int cause) {
//        plusClient.connect();
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!plusClient.isConnecting()) {
//                plusClient.connect();
            }
        }
        if(callbackManager!=null)
            callbackManager.onActivityResult(requestCode, responseCode, intent);
//        if(Session.getActiveSession()!=null)
//            Session.getActiveSession().onActivityResult(this, requestCode, responseCode, intent);
    }

    private User readUser(){
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String s = prefs.getString("user", null);
        return s!=null?new Gson().fromJson(s,User.class):null;
    }

    private void writeUser(User user){
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("user", new Gson().toJson(user));
        ed.commit();
    }

}
