package com.anselmo.encuentrapareja.ui;

/**
 * Created by naranya on 9/7/15.
 */

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.bumptech.glide.Glide;
import com.vstechlab.easyfonts.EasyFonts;

public class DescriptionDetailActivity extends BaseActivity {
    private TextView labelDescription;
    private CollapsingToolbarLayout collapsingToolbar;

    private static final int IMAGES_ARRAY[] = {
            R.drawable.img_paso01,
            R.drawable.img_paso02,
            R.drawable.img_paso03,
            R.drawable.img_paso04,
            R.drawable.img_paso05,
            R.drawable.img_paso06,
            R.drawable.img_paso07,
            R.drawable.img_paso08,
            R.drawable.img_paso09,
            R.drawable.img_paso10
    };

    private static final int DESCRIPTION_ARRAY_RESOURCES[] = {
            R.string.step_one_description,
            R.string.step_two_description,
            R.string.step_three_description,
            R.string.step_four_description,
            R.string.step_five_description,
            R.string.step_six_description,
            R.string.step_seven_description,
            R.string.step_eight_description,
            R.string.step_nine_description,
            R.string.step_ten_description
    };

    private String titlesArraySteps[];

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position_array");
        }

        //Analytics
        AnalyticsManager.sendScreenView("/home/description/step" + position);
        
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

        labelDescription = (TextView) findViewById(R.id.labelDescription);
        labelDescription.setTypeface(EasyFonts.robotoLight(this));

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //Get array steps
        titlesArraySteps = getResources().getStringArray(R.array.array_title_steps);

        switch ( position ) {
            case 0:
                loadBackdrop( IMAGES_ARRAY[0] );
                collapsingToolbar.setTitle(titlesArraySteps[0]);
                labelDescription.setText( Html.fromHtml( getString(DESCRIPTION_ARRAY_RESOURCES[0])) );
                break;

            case 1:
                loadBackdrop( IMAGES_ARRAY[1] );
                collapsingToolbar.setTitle(titlesArraySteps[1]);
                labelDescription.setText( Html.fromHtml( getString(DESCRIPTION_ARRAY_RESOURCES[1])) );
                break;

            case 2:
                loadBackdrop( IMAGES_ARRAY[2] );
                collapsingToolbar.setTitle(titlesArraySteps[2]);
                labelDescription.setText( Html.fromHtml( getString(DESCRIPTION_ARRAY_RESOURCES[2])) );
                break;

            case 3:
                loadBackdrop( IMAGES_ARRAY[3] );
                collapsingToolbar.setTitle(titlesArraySteps[3]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[3])) );
                break;

            case 4:
                loadBackdrop( IMAGES_ARRAY[4] );
                collapsingToolbar.setTitle(titlesArraySteps[4]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[4])) );
                break;

            case 5:
                loadBackdrop( IMAGES_ARRAY[5] );
                collapsingToolbar.setTitle(titlesArraySteps[5]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[5])) );
                break;

            case 6:
                loadBackdrop( IMAGES_ARRAY[6] );
                collapsingToolbar.setTitle(titlesArraySteps[6]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[6])) );
                break;

            case 7:
                loadBackdrop( IMAGES_ARRAY[7] );
                collapsingToolbar.setTitle(titlesArraySteps[7]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[7])) );
                break;

            case 8:
                loadBackdrop( IMAGES_ARRAY[8] );
                collapsingToolbar.setTitle(titlesArraySteps[8]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[8])) );
                break;

            case 9:
                loadBackdrop( IMAGES_ARRAY[9] );
                collapsingToolbar.setTitle(titlesArraySteps[9]);
                labelDescription.setText( Html.fromHtml(getString(DESCRIPTION_ARRAY_RESOURCES[9])) );
                break;
        }
    }

    private void loadBackdrop( int resource) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(resource).centerCrop().into(imageView);
    }
}