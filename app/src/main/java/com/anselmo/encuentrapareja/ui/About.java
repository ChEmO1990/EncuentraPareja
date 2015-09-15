package com.anselmo.encuentrapareja.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.vstechlab.easyfonts.EasyFonts;

public class About extends BaseActivity {
    private TextView textViewDeveloper;
    private TextView textViewAuthor;
    private TextView textViewDesign;

    private TextView titleDeveloper;
    private TextView titleAuthor;
    private TextView titleDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AnalyticsManager.sendScreenView("AboutActivity");

        getSupportActionBar().setTitle(getString(R.string.about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setDrawerIndicatorEnabled(false);
        setCustomIconNavigationDrawer(R.mipmap.ic_up);


        getmDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewDeveloper = (TextView) findViewById(R.id.textDeveloper);
        textViewAuthor = (TextView) findViewById(R.id.textAuthor);
        textViewDesign = (TextView) findViewById(R.id.textDesign);

        titleDeveloper = (TextView) findViewById(R.id.textView1);
        titleAuthor = (TextView) findViewById(R.id.textView2);
        titleDesign = (TextView) findViewById(R.id.textView3);

        //
        textViewDeveloper.setTypeface(EasyFonts.robotoLight(this));
        textViewAuthor.setTypeface(EasyFonts.robotoLight(this));
        textViewDesign.setTypeface(EasyFonts.robotoLight(this));

        titleDeveloper.setTypeface(EasyFonts.robotoLight(this));
        titleAuthor.setTypeface(EasyFonts.robotoLight(this));
        titleDesign.setTypeface(EasyFonts.robotoLight(this));

        textViewDeveloper.setText("Anselmo Hernandez");
        textViewAuthor.setText("Leopoldo Flores");
        textViewDesign.setText("Uzziel Millan");

    }
}
