package com.anselmo.encuentrapareja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;

public class ShowTipActivity extends AppCompatActivity {
    private String tip;
    private TextView tipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tip);

        tipView = (TextView) findViewById(R.id.showTipText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tip = extras.getString("push_tip");
            tipView.setText(tip);
        }


        AnalyticsManager.sendScreenView("ShowTipActivity");

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        getSupportActionBar().setTitle(getString(R.string.day_tip));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_up);

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToHome();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        redirectToHome();
    }

    private void redirectToHome() {
        Intent i = new Intent( this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finish();
    }
}
