package com.anselmo.encuentrapareja.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anselmo.encuentrapareja.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import me.grantland.widget.AutofitTextView;

public class DescriptionStepActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private AutofitTextView mTitleView;
    private TextView mDescriptionStep;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexiblespacewithimagescrollview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position_array");
        }

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();

        mImageView = findViewById(R.id.image);
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (AutofitTextView) findViewById(R.id.title);
        mDescriptionStep = (TextView) findViewById(R.id.description_step);
        setTitle(null);

        //Get array steps
        titlesArraySteps = getResources().getStringArray(R.array.array_title_steps);

        switch ( position ) {
            case 0:
                mTitleView.setText( titlesArraySteps[0] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[0]));
                break;

            case 1:
                mTitleView.setText( titlesArraySteps[1] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[1]));
                break;

            case 2:
                mTitleView.setText( titlesArraySteps[2] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[2]));
                break;

            case 3:
                mTitleView.setText( titlesArraySteps[3] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[3]));
                break;

            case 4:
                mTitleView.setText( titlesArraySteps[4] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[4]));
                break;

            case 5:
                mTitleView.setText( titlesArraySteps[5] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[5]));
                break;

            case 6:
                mTitleView.setText( titlesArraySteps[6] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[6]));
                break;

            case 7:
                mTitleView.setText( titlesArraySteps[7] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[7]));
                break;

            case 8:
                mTitleView.setText( titlesArraySteps[8] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[8]));
                break;

            case 9:
                mTitleView.setText( titlesArraySteps[9] );
                mDescriptionStep.setText( getString( descriptionArraySetpsResource[9]));
                break;

        }



        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

                // If you'd like to start from scrollY == 0, don't write like this:
                //mScrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
                mScrollView.scrollTo(0, 1);
                mScrollView.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }



}
