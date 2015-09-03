package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.anselmo.encuentrapareja.R;
import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by naranya on 9/1/15.
 */
public class SplashActivity extends AppCompatActivity {
    private long splashDelay = 2500; //5 secs
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image = (ImageView) findViewById(R.id.splash_image);

        Glide.with(this)
                .load(R.drawable.splash_image)
                .centerCrop()
                .crossFade()
                .into(image);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(SplashActivity.this, HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }
}
