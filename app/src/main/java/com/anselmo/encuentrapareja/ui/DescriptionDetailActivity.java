package com.anselmo.encuentrapareja.ui;

/**
 * Created by naranya on 9/7/15.
 */

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.anselmo.encuentrapareja.analytics.AnalyticsManager;
import com.bumptech.glide.Glide;
import com.vstechlab.easyfonts.EasyFonts;

public class DescriptionDetailActivity extends BaseActivity {
    private TextView labelDescription;
    private CollapsingToolbarLayout collapsingToolbar;

    private static int descriptionArraySetpsResource[] = {
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

        labelDescription = (TextView) findViewById(R.id.labelDescription);
        labelDescription.setTypeface(EasyFonts.robotoLight(this));
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //Get array steps
        titlesArraySteps = getResources().getStringArray(R.array.array_title_steps);

        loadBackdrop();

        switch ( position ) {
            case 0:
                collapsingToolbar.setTitle(titlesArraySteps[0]);
                labelDescription.setText(getString(descriptionArraySetpsResource[0]));
                break;

            case 1:
                collapsingToolbar.setTitle(titlesArraySteps[1]);
                labelDescription.setText(getString(descriptionArraySetpsResource[1]));
                break;

            case 2:
                collapsingToolbar.setTitle(titlesArraySteps[2]);
                labelDescription.setText(getString(descriptionArraySetpsResource[2]));
                break;

            case 3:
                collapsingToolbar.setTitle(titlesArraySteps[3]);
                labelDescription.setText( getString( descriptionArraySetpsResource[3]) );
                break;

            case 4:
                collapsingToolbar.setTitle(titlesArraySteps[4]);
                labelDescription.setText( getString( descriptionArraySetpsResource[4]) );
                break;

            case 5:
                collapsingToolbar.setTitle(titlesArraySteps[5]);
                labelDescription.setText( getString( descriptionArraySetpsResource[5]) );
                break;

            case 6:
                collapsingToolbar.setTitle(titlesArraySteps[6]);
                labelDescription.setText( getString( descriptionArraySetpsResource[6]) );
                break;

            case 7:
                collapsingToolbar.setTitle(titlesArraySteps[7]);
                labelDescription.setText( getString( descriptionArraySetpsResource[7]) );
                break;

            case 8:
                collapsingToolbar.setTitle(titlesArraySteps[8]);
                labelDescription.setText( getString( descriptionArraySetpsResource[8]) );
                break;

            case 9:
                collapsingToolbar.setTitle(titlesArraySteps[9]);
                labelDescription.setText( getString( descriptionArraySetpsResource[9]) );
                break;
        }
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.img_paso1).centerCrop().into(imageView);
    }
}