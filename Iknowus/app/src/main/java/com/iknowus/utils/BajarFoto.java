package com.iknowus.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;

import com.iknowus.App;

public class BajarFoto extends AsyncTask<Integer, Void, Integer> {
	String fbID;
	File cacheDir;
	
	public BajarFoto(String fbID){
		this.fbID= fbID;
		this.cacheDir = new File(App.getPhotoCache());
	}		
	
	@Override
	protected void onPreExecute() {}

	@Override
	protected Integer doInBackground(Integer... arg0) {
		try {
	            URL img_value = null;
	            String aux="https://graph.facebook.com/" + fbID + "/picture?type=large";
	            System.out.println(aux);
	            img_value = new URL(aux);
	            //Bitmap bm=BitmapFactory.decodeStream(img_value.openConnection().getInputStream());	           
	            File _filePhoto = new File(cacheDir, fbID+".jpg");
	            System.out.println(_filePhoto.getPath());
	            OutputStream osFilePhoto;
	            InputStream isPhoto = img_value.openConnection().getInputStream();
	        //synchronized (this) {
	            osFilePhoto = new FileOutputStream(_filePhoto);	            
	            byte[] buffer = new byte[1024];
	            int l;
	            while((l=isPhoto.read(buffer))>0){
	            	osFilePhoto.write(buffer,0,l);	          
	            }	            				
				osFilePhoto.close();
	            isPhoto.close();	 
			//}
            return 1;	            	           
		} catch (Exception e) {
			e.printStackTrace();	
			return 0;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {	
		if (result == 1) {		
			System.out.println("Se descargo la foto de: " + fbID + " correctamente.");
		}else{
			System.out.println("Ocurrio un error al desacargar la foto de: "  + fbID );
		}					
	}

}