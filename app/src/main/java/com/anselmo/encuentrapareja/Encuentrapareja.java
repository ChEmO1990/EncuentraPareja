package com.anselmo.encuentrapareja;

import android.app.Application;

import com.anselmo.encuentrapareja.sqlite.DataBaseHelper;

import java.io.IOException;

/**
 * Created by naranya on 9/7/15.
 */
public class Encuentrapareja extends Application {
    private DataBaseHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DataBaseHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
