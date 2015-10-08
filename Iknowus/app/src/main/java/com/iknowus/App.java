package com.iknowus;

import android.os.Environment;

import java.io.File;

/**
 * Created by Jorge on 07/10/2015.
 */
public class App {
    public static String getPhotoCache(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Iknowus" + File.separator;
    }
}
